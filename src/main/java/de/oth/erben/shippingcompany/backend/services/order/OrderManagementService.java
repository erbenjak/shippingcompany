package de.oth.erben.shippingcompany.backend.services.order;

import de.oth.erben.shippingcompany.backend.data.entities.*;
import de.oth.erben.shippingcompany.backend.data.repositorys.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.persistence.criteria.Order;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class OrderManagementService implements AbstractOrderManagementService {

    @Autowired
    StatusReposirory statusReposirory;

    @Autowired
    AbstractOrderRepository orderRepository;

    @Autowired
    LetterRepository letterRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    TripRepository tripRepository;

    @Override
    public boolean registerOrder(AbstractOrder newlyCreatedOrder, Optional<String> customerIdentifier) {
        Status registeredButNotPlanned = new Status();

        newlyCreatedOrder.setStatus(registeredButNotPlanned);

        Long newId = savelyProduceSharedIdentifier();
        newlyCreatedOrder.setTrackingId(newId);

        statusReposirory.save(registeredButNotPlanned);

        if(newlyCreatedOrder instanceof Shipment){
            Shipment newlyCreatedShipment = (Shipment) newlyCreatedOrder;

            //if(newlyCreatedShipment.getAmountBundle() > )

            Date pickUpDateWeekday =  precheckIfPickupDateIsAWeekday(newlyCreatedShipment.getPickUpTime());
            //Date possiblePickUpDate = checkIfPickupDateIsPossible();

            //planShipment(newlyCreatedShipment);
        }

        if(customerIdentifier.isPresent()){
            Optional<Customer> customer = Optional.ofNullable(null);
            try {
                customer = customerRepository.findByUserName(customerIdentifier.get());
            }catch(Exception e){
                throw new IllegalStateException("A registered account must have a corresponding customer-account");
            }

            if(!customer.isPresent()){
                throw new IllegalStateException("A registered account must have a corresponding customer-account");
            }else{
                customer.get().getOrders().add(newlyCreatedOrder);
                newlyCreatedOrder.setCustomer(customer.get());
            }
        }

        return true;
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

    /**
     *Gets triggered by crone-jobs inside the letter-Service and the delivery-Service.
     * **/
    @Override
    public void planOrder(List<AbstractOrder> abstractOrders,Class typeOfOrder) {
        if(typeOfOrder.equals(Letter.class)){
            planLetter(abstractOrders);
        }else if(typeOfOrder.equals(Order.class)){
            planShipment(abstractOrders);
        }else{
            throw new IllegalArgumentException("Unknown AbstractOrder Type. Order must either be a letter or a delivery!");
        }
    }

    @Override
    public List<AbstractOrder> findOrdersByCustomerUserName(String userName) {
        return orderRepository.findByCustomerUserName(userName);
    }

    @Override
    public Optional<AbstractOrder> findByTrackingId(Long trackingId) {
        return orderRepository.findByTrackingId(trackingId);
    }

    private Date precheckIfPickupDateIsAWeekday(Date dateForPrecheking){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateForPrecheking);

        if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            calendar.add(Calendar.DAY_OF_WEEK,2);
        }else if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            calendar.add(Calendar.DAY_OF_WEEK,2);
        }

        return calendar.getTime();
    }

    private void planLetter(List<AbstractOrder> abstractOrders) {

    }

    private void planShipment(List<AbstractOrder> abstractOrders) {

    }
}
