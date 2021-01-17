package de.oth.erben.shippingcompany.backend.services.order;

import de.oth.erben.shippingcompany.backend.data.entities.*;
import de.oth.erben.shippingcompany.backend.data.entities.dto.OrderLetterDTO;
import de.oth.erben.shippingcompany.backend.data.entities.dto.OrderShipmentDTO;
import de.oth.erben.shippingcompany.backend.data.entities.dto.ShipmentOrderdDTO;
import de.oth.erben.shippingcompany.backend.data.repositorys.LetterRepository;
import de.oth.erben.shippingcompany.backend.data.repositorys.ShipmentRepository;
import de.oth.erben.shippingcompany.backend.services.AbstractUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MailingService implements AbstractMailingService {

    private LetterRepository letterRepository;
    private ShipmentRepository shipmentRepository;
    private AbstractOrderManagementService orderManagementService;
    private AbstractUserService userService;

    @Autowired
    public MailingService(LetterRepository letterRepository,ShipmentRepository shipmentRepository,AbstractOrderManagementService orderManagementService,AbstractUserService userService){
        this.letterRepository=letterRepository;
        this.shipmentRepository=shipmentRepository;
        this.orderManagementService=orderManagementService;
        this.userService=userService;
    }

    @Override
    public long orderLetter(OrderLetterDTO orderDetails) {
        Letter newlyCreatedLetter = new Letter();
        newlyCreatedLetter.setContent(orderDetails.getContentLetter());
        newlyCreatedLetter.setReceivingAddress(orderDetails.getReceivingAddress());
        newlyCreatedLetter.setPrice(0.80);

        String customerIdentifier = SecurityContextHolder.getContext().getAuthentication().getName();

        if(customerIdentifier==null){
            if(orderDetails.getCustomerKey()!=null && !orderDetails.getCustomerKey().equals("")){
                Optional<Customer> customer = userService.getCustomerFromPartnerKey(orderDetails.getCustomerKey());
                if(customer.isPresent()){
                    customerIdentifier = customer.get().getUserName();
                }
            }
        }

        orderManagementService.registerOrder(newlyCreatedLetter, Optional.ofNullable(customerIdentifier));
        letterRepository.save(newlyCreatedLetter);

        return  newlyCreatedLetter.getTrackingId();
    }

    @Override
    public ShipmentOrderdDTO orderShipment(OrderShipmentDTO orderDetails){
        Shipment newlyCreatedShipment = new Shipment();

        if(orderDetails.getPickupTime()==null&&orderDetails.getPickUpTimeString()!=null){
            try {
                Date pickUpTime = new SimpleDateFormat("yyyy-MM-dd").parse(orderDetails.getPickUpTimeString());
                newlyCreatedShipment.setPickUpTime(pickUpTime);
            } catch (ParseException e) {
                e.printStackTrace();
                throw new IllegalStateException("Given Pickup date could not be read.",e);
            }
        }else if (orderDetails.getPickupTime()==null&&orderDetails.getPickUpTimeString()==null){
            throw new IllegalStateException("No Pickup date given.");
        }else{
            newlyCreatedShipment.setPickUpTime(orderDetails.getPickupTime());
        }

        newlyCreatedShipment.setReceivingAddress(orderDetails.getReceivingAddress());
        newlyCreatedShipment.setAmountBundle(orderDetails.getAmountBundles());
        newlyCreatedShipment.setPickUpAddress(orderDetails.getStartingAddress());
        //assume that delivery will always be 2 hours after pickup
        newlyCreatedShipment.setDropOffTime(addTimeNeededForDelivery(newlyCreatedShipment.getPickUpTime(),2));

        newlyCreatedShipment.setPrice(30.00);

        String customerIdentifier = SecurityContextHolder.getContext().getAuthentication().getName();

        /*if(customerIdentifier==null){
            if(orderDetails.getCustomerKey()!=null && !orderDetails.getCustomerKey().equals("")){
                Optional<Customer> customer = userService.getCustomerFromPartnerKey(orderDetails.getCustomerKey());
                if(customer.isPresent()){
                    customerIdentifier = customer.get().getUserName();
                }else{
                    throw new IllegalStateException("A User must be a registered Customer to order a shipment -case 1");
                }
            }else{
                throw new IllegalStateException("A User must be a registered Customer to order a shipment -case 2");
            }
        }*/

        orderManagementService.registerOrder(newlyCreatedShipment, Optional.ofNullable(customerIdentifier));
        shipmentRepository.save(newlyCreatedShipment);

        ShipmentOrderdDTO shipmentOrderdDTO= new ShipmentOrderdDTO();

        shipmentOrderdDTO.setPickupTime(newlyCreatedShipment.getPickUpTime());
        shipmentOrderdDTO.setTrackingId(newlyCreatedShipment.getTrackingId());
        shipmentOrderdDTO.setDropOffTime(newlyCreatedShipment.getDropOffTime());

        return shipmentOrderdDTO;
    }

    private  Date addTimeNeededForDelivery(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }


    //@Scheduled(cron = "* */10 * * * *")
    public void planLetters(){
        List<AbstractOrder> registeredLetters = ((List<Letter>) letterRepository.findAll()).stream().
                                filter(letter -> letter.getStatus().getDescription().equals(StatusDescription.REGISTERED))
                                .collect(Collectors.toList());
        orderManagementService.planOrder(registeredLetters, Letter.class);
    }
}
