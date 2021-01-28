package de.oth.erben.shippingcompany.backend.services.order;

import de.oth.erben.shippingcompany.backend.data.entities.Address;
import de.oth.erben.shippingcompany.backend.data.entities.Shipment;
import de.oth.erben.shippingcompany.backend.data.entities.dto.OrderShipmentDTO;
import de.oth.erben.shippingcompany.backend.data.entities.dto.ShipmentOrderdDTO;
import de.oth.erben.shippingcompany.backend.data.repositorys.ShipmentRepository;
import de.oth.erben.shippingcompany.backend.exceptions.OrderRegistrationException;
import de.oth.erben.shippingcompany.backend.services.firstorder.IFirstOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Scope("singleton")
@Service
public class ShipmentService implements IShipmentService{

    @Autowired
    IOrderManagementService orderManagementService;

    @Autowired
    ShipmentRepository shipmentRepository;

    @Autowired
    IFirstOrderService firstOrderBonusService;

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    @Override
    public ShipmentOrderdDTO orderShipment(OrderShipmentDTO orderDetails){
        Shipment newlyCreatedShipment = new Shipment();

        newlyCreatedShipment = setDate(orderDetails, newlyCreatedShipment);
        newlyCreatedShipment = setReceivingAddress(orderDetails, newlyCreatedShipment);
        newlyCreatedShipment.setAmountBundle(orderDetails.getAmountBundles());
        newlyCreatedShipment.setPickUpAddress(orderDetails.getStartingAddress());
        //assume that delivery will always be 2 hours after pickup
        newlyCreatedShipment.setDropOffTime(addTimeNeededForDelivery(newlyCreatedShipment.getPickUpTime(),2));
        newlyCreatedShipment.setPrice(30.00);

        //registering sets the status as well as the customer
        try {
            newlyCreatedShipment = (Shipment) orderManagementService.registerOrder(newlyCreatedShipment, Optional.ofNullable(orderDetails.getCustomerKey()));
        }catch(OrderRegistrationException e){
            System.out.println("Could not register Shipment");
            return new ShipmentOrderdDTO();
        }

        shipmentRepository.save(newlyCreatedShipment);

        //check if shipment is first shipment
        List<Shipment> shipments = orderManagementService.findOrdersByCustomerUserName(newlyCreatedShipment.getCustomer().getUserName()).
                //filter to only leave shipments remaining
                stream().filter(order -> order instanceof Shipment).map(order -> (Shipment)order).collect(Collectors.toList());

        if(shipments.size()==1){
            firstOrderBonusService.orderBonusAndSendLetterConfirmation(newlyCreatedShipment.getCustomer(),newlyCreatedShipment.getPickUpAddress());
        }

        //create return
        ShipmentOrderdDTO shipmentOrderdDTO= new ShipmentOrderdDTO();
        shipmentOrderdDTO.setPickupTime(newlyCreatedShipment.getPickUpTime());
        shipmentOrderdDTO.setTrackingId(newlyCreatedShipment.getTrackingId());
        shipmentOrderdDTO.setDropOffTime(newlyCreatedShipment.getDropOffTime());

        return shipmentOrderdDTO;
    }

    private Shipment setReceivingAddress(OrderShipmentDTO orderDetails, Shipment newlyCreatedShipment) {
        if(orderDetails.getReceivingAddress()==null){
          orderDetails.setReceivingAddress(new Address());
        }

        if(orderDetails.getReceivingAddress().checkIfAddressIsEmpty()){
            //only a short term fix - since one partner project does not save an address like previously agreed upon
            Address fixedAddress = new Address();
            fixedAddress.setReceiver(orderDetails.getReceivingAddressString());
            newlyCreatedShipment.setReceivingAddress(fixedAddress);
        }else{
            newlyCreatedShipment.setReceivingAddress(orderDetails.getReceivingAddress());
        }

        return newlyCreatedShipment;
    }

    private Shipment setDate(OrderShipmentDTO orderDetails, Shipment newlyCreatedShipment) {
        if(orderDetails.getPickupTime()==null&& orderDetails.getPickUpTimeString()!=null){
            try {
                Date pickUpTime = new SimpleDateFormat("yyyy-MM-dd").parse(orderDetails.getPickUpTimeString());
                newlyCreatedShipment.setPickUpTime(pickUpTime);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new IllegalStateException("Given Pickup date could not be read.",e);
            }
        }else if (orderDetails.getPickupTime()==null&& orderDetails.getPickUpTimeString()==null){
            throw new IllegalStateException("No Pickup date given.");
        }else{
            newlyCreatedShipment.setPickUpTime(orderDetails.getPickupTime());
        }
        return newlyCreatedShipment;
    }

    private Date addTimeNeededForDelivery(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}
