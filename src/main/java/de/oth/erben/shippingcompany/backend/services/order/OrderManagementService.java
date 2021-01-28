package de.oth.erben.shippingcompany.backend.services.order;

import de.oth.erben.shippingcompany.backend.data.entities.*;
import de.oth.erben.shippingcompany.backend.data.repositorys.*;
import de.oth.erben.shippingcompany.backend.exceptions.LetterPlanningException;
import de.oth.erben.shippingcompany.backend.exceptions.OrderPlanningException;
import de.oth.erben.shippingcompany.backend.exceptions.OrderRegistrationException;
import de.oth.erben.shippingcompany.backend.services.firstorder.IFirstOrderService;
import de.oth.erben.shippingcompany.backend.services.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Scope("singleton")
@Service
public class OrderManagementService implements IOrderManagementService {

    //repositorys
    @Autowired
    StatusReposirory statusReposirory;
    @Autowired
    AbstractOrderRepository orderRepository;
    @Autowired
    LetterRepository letterRepository;
    @Autowired
    ShipmentRepository shipmentRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    TripRepository tripRepository;
    @Autowired
    IVehicleRepository vehicleRepository;

    //services
    @Autowired
    IFirstOrderService firstOrderService;
    @Autowired
    UserService userService;

    //expects transaction to be created by the mailing- order shiping-service
    @Transactional(Transactional.TxType.REQUIRED)
    @Override
    public AbstractOrder registerOrder(AbstractOrder newlyCreatedOrder, Optional<String> customerKey) throws OrderRegistrationException {
        Status registeredButNotPlanned = new Status();

        newlyCreatedOrder.setStatus(registeredButNotPlanned);

        Long newId = savelyProduceSharedIdentifier();
        newlyCreatedOrder.setTrackingId(newId);

        Optional<Customer> matchingCustomer = userService.getCustomerWithCustomerKey(customerKey);

        if(newlyCreatedOrder instanceof Shipment){
            if(matchingCustomer.isEmpty()){
                throw new IllegalStateException("Can't order a shipment without being logged in!");
            }
        }

        statusReposirory.save(registeredButNotPlanned);

        if(matchingCustomer.isPresent()) {
            matchingCustomer.get().getOrders().add(newlyCreatedOrder);
            newlyCreatedOrder.setCustomer(matchingCustomer.get());
        }

        return newlyCreatedOrder;
    }

    @NotNull
    private Long savelyProduceSharedIdentifier() {
        List<Long> existingIds = StreamSupport.stream(orderRepository.findAll().spliterator(), false).
                map(AbstractOrder::getTrackingId).collect(Collectors.toList());

        //bitwise and operation to always generate positive values - which is just more convenient for the user
        //          newId = 1001....10
        // Long.MAX_VALUE = 0111....11
        //                & 0001....10

        Long newId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;

        if(existingIds.contains(newId)){
            int counter = 0;
            while(counter < 10 &&existingIds.contains(newId)){
                newId = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
                counter++;
            }
            if(existingIds.contains(newId)){
                throw new IllegalStateException("Could not create unique id for the order.");
            }
        }
        return newId;
    }


    @Transactional(Transactional.TxType.SUPPORTS)
    @Override
    public List<AbstractOrder> findOrdersByCustomerUserName(String userName) {
        return orderRepository.findByCustomerUserName(userName);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    @Override
    public List<Trip> findTripsByEmployeeUserName(String userName) {
        Optional<Employee> e = employeeRepository.findByUserName(userName);

        if(!e.isPresent()){
            return new ArrayList();
        }

        return e.get().getDeliveries();
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    @Override
    public Optional<AbstractOrder> findByTrackingId(Long trackingId) {
        return orderRepository.findByTrackingId(trackingId);
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    @Override
    public Optional<Trip> findByTripId(Long tripId) {
        return tripRepository.findById(tripId);
    }


    @Override
    @Scheduled(fixedDelay = 300000,initialDelay = 10000)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void printRegisteredLetter() {
        List<Letter> registeredLetters = letterRepository.findByStatusDescription(StatusDescription.REGISTERED.getDescriptionString());
        for(Letter letter: registeredLetters){
            Status status = letter.getStatus();
            status.update(StatusDescription.PRINTING);
            statusReposirory.save(status);
        }
    }


    /**
     * To create a letter-delivery the following things are needed
     *
     * - an employee
     * - a  vehicle
     * - one or more letter
     * (multiple letters will be put into one trip)
     * **/
    @Override
    @Scheduled(fixedDelay = 300000,initialDelay = 160000)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void planPrintedLetter() throws LetterPlanningException {

        List<AbstractOrder> printedLetter = letterRepository.findByStatusDescription(StatusDescription.PRINTING.getDescriptionString()).stream().map(letter -> (AbstractOrder)letter).collect(Collectors.toList());

        if(printedLetter.isEmpty()){
            return;
        }

        //employees are being sorted by the least amount of undelivered trips first
        List<Employee> allEmployees = StreamSupport.stream(employeeRepository.findAll().spliterator(),false).sorted().collect(Collectors.toList());

        Vehicle chosenVehicle = findBicycleLeastUsed();

        if(allEmployees.isEmpty()) {
            System.out.println("INFO : Can't plan the letter delivery without any employees to assign the delivery to.");
            return;
        }

        //employees will be sorted by the the amount of deliveries they still have to complete
        //the less deliveries the "smaller" the employee
        Employee employeeChosenForDelivery = allEmployees.get(0);

        Date deliveryTime = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(deliveryTime);
        c.add(Calendar.DATE, 1);
        deliveryTime = c.getTime();

        Trip newTrip = new Trip(deliveryTime,chosenVehicle,printedLetter);
        tripRepository.save(newTrip);

        employeeChosenForDelivery.getDeliveries().add(newTrip);
        employeeRepository.save(employeeChosenForDelivery);

        for(AbstractOrder letter:printedLetter){
            letter.setCorrespondingTrip(newTrip);
            Status status = letter.getStatus();
            status.update(StatusDescription.IN_DELIVERY);
            statusReposirory.save(status);
            letterRepository.save((Letter)letter);
        }
    }

    /**
     * To create a shipment-delivery the following things are needed
     *
     * - an employee
     * - a  vehicle
     * - a  shipment
     * **/
    @Override
    @Scheduled(fixedDelay = 300000,initialDelay = 10000)
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void planRegisteredShipments() throws OrderPlanningException {

        List<AbstractOrder> registeredShipments = shipmentRepository.findByStatusDescription(StatusDescription.REGISTERED.getDescriptionString()).stream().map(shipment -> (AbstractOrder)shipment).collect(Collectors.toList());

        for(AbstractOrder order:registeredShipments){
            List<Employee> allEmployees = StreamSupport.stream(employeeRepository.findAll().spliterator(),false).sorted().collect(Collectors.toList());

            if(allEmployees.isEmpty()) {
                throw new OrderPlanningException("ERROR : Can't plan the letter delivery without any employees to assign the delivery to.");
            }

            Vehicle chosenVehicle = ((Shipment)order).getAmountBundle() < 200 ? findSmallTruckLeastUsed() : findBigTruckLeastUsed();

            int amountPeopleRequired = chosenVehicle.getAmntPersonsRequired();
            List<Employee> employees = new ArrayList<>();

            if(amountPeopleRequired>allEmployees.size()){
                throw new OrderPlanningException("To view employees to deliver a order of this in one go");
            }

            for(int i=0;i<amountPeopleRequired;i++){
                employees.add(allEmployees.get(i));
            }

            Date deliveryTime = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(deliveryTime);
            c.add(Calendar.DATE, 1);
            deliveryTime = c.getTime();

            ArrayList<AbstractOrder> currentOrders = new ArrayList<>();
            currentOrders.add(order);

            Trip newTrip = new Trip(deliveryTime,chosenVehicle,currentOrders);
            tripRepository.save(newTrip);

            for(Employee employeeChosenForDelivery:employees) {
                employeeChosenForDelivery.getDeliveries().add(newTrip);
                employeeRepository.save(employeeChosenForDelivery);
            }

            order.setCorrespondingTrip(newTrip);
            Status status = order.getStatus();
            status.update(StatusDescription.IN_DELIVERY);
            statusReposirory.save(status);
            shipmentRepository.save((Shipment)order);
        }
    }

    @Override
    @Transactional(Transactional.TxType.SUPPORTS)
    public boolean checkIfOrderBelongsToCustomer(String userName, Long trackingId) {
        Optional<Customer> customer= customerRepository.findByUserName(userName);

        if(!customer.isPresent()){
            return false;
        }

        for(AbstractOrder order : customer.get().getOrders()){
            if(order.getTrackingId().equals(trackingId)){
                return true;
            }
        }

        return false;
    }

    @Transactional(Transactional.TxType.SUPPORTS)
    @Override
    public boolean editOrderLetter(Letter oldLetter, Letter newLetter) {
        oldLetter.setContent(newLetter.getContent());
        oldLetter.setReceivingAddress(newLetter.getReceivingAddress());

        // a order being edited can not have a corresponding trip - because then its already being delivered
        oldLetter.setCorrespondingTrip(null);

        oldLetter.getStatus().update(StatusDescription.REGISTERED);
        letterRepository.save(oldLetter);
        return true;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public boolean editOrderShipment(Shipment oldShipment, Shipment newShipment) {
        oldShipment.setCorrespondingTrip(null);
        oldShipment.getStatus().update(StatusDescription.REGISTERED);
        oldShipment.setReceivingAddress(newShipment.getReceivingAddress());
        oldShipment.setAmountBundle(newShipment.getAmountBundle());
        oldShipment.setPickUpAddress(newShipment.getPickUpAddress());
        oldShipment.setDropOffTime(newShipment.getDropOffTime());
        //in case the customer entered no date on editing
        if(newShipment.getPickUpTime()!=null){
            oldShipment.setPickUpTime(newShipment.getPickUpTime());
        }
        shipmentRepository.save(oldShipment);

        return true;
    }

    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void cancelOrder(AbstractOrder order) {
        if(order.getCorrespondingTrip()!=null){
            throw new IllegalStateException("A order in delivery can't be canceled");
        }
        order.getStatus().update(StatusDescription.CANCELED);
        orderRepository.save(order);
    }


    @Override
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void deliverTrip(Trip tripToDeliver) {
        if(tripToDeliver == null){
            throw new IllegalStateException("Can't deliver a trip without specifying which one.");
        }


        List<AbstractOrder> orders = tripToDeliver.getOrders().stream().collect(Collectors.toList());

        if(orders.isEmpty()){
            throw new IllegalStateException("Can't deliver a trip,which does not contain a any orders");
        }

        //Setting all orders inside Trip to status == delivered
        for(AbstractOrder order : orders){
            order.getStatus().update(StatusDescription.DELIVERED);
            statusReposirory.save(order.getStatus());
            orderRepository.save(order);
        }

        tripToDeliver.setCompleted(true);

        tripRepository.save(tripToDeliver);

        return;
    }

    private Vehicle findBicycleLeastUsed(){
        //a vehicle with less than 100 kilo maxWeight is a bicycles
        return getVehicleByMaxWeight(0.0,100.00);
    }

    private Vehicle findSmallTruckLeastUsed(){
        //a vehicle with more than 100 and less than 10000 kilo maxWeight is a small truck
        return getVehicleByMaxWeight(100,10000.00);
    }

    private Vehicle findBigTruckLeastUsed(){
        //a vehicle with more than 100 and less than 10000 kilo maxWeight is a small truck
        return getVehicleByMaxWeight(10000.00,Double.MAX_VALUE);
    }

    @Nullable
    private Vehicle getVehicleByMaxWeight(double minWeight, double maxWeight) {
        List<Vehicle> vehicles = StreamSupport.stream(vehicleRepository.findAll().spliterator(),false).collect(Collectors.toList());
        Vehicle bicycle = null;

        for(Vehicle vehicle: vehicles){

            if(vehicle.getMaxWeight()>minWeight&&vehicle.getMaxWeight()<=maxWeight){
                if(bicycle == null){
                    bicycle = vehicle;
                }else{
                    if(tripRepository.findByDeliveryVehicle(vehicle).size()<tripRepository.findByDeliveryVehicle(bicycle).size()){
                        bicycle = vehicle;
                    }
                }
            }
        }
        return bicycle;
    }
}
