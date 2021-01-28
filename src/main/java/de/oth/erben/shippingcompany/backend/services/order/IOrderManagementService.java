package de.oth.erben.shippingcompany.backend.services.order;

import de.oth.erben.shippingcompany.backend.data.entities.AbstractOrder;
import de.oth.erben.shippingcompany.backend.data.entities.Letter;
import de.oth.erben.shippingcompany.backend.data.entities.Shipment;
import de.oth.erben.shippingcompany.backend.data.entities.Trip;
import de.oth.erben.shippingcompany.backend.exceptions.LetterPlanningException;
import de.oth.erben.shippingcompany.backend.exceptions.OrderPlanningException;
import de.oth.erben.shippingcompany.backend.exceptions.OrderRegistrationException;

import java.util.List;
import java.util.Optional;

public interface IOrderManagementService {
   /**
    *
    * @param newlyCreatedOrder - order created by the mailing- or shipping-service
    * @param customerKey - needed to dertemine the corresponding customer - if no key is given
    *                    the customer is determined by the securityContext
    * @return order- whihc now has a status and if possible a customer assigned
    */
   AbstractOrder registerOrder(AbstractOrder newlyCreatedOrder, Optional<String> customerKey) throws OrderRegistrationException;

   /**
    *
    * @param userName - username of the customer
    * @return list of orders belonging to that customer
    */
   List<AbstractOrder> findOrdersByCustomerUserName(String userName);

   /**
    *
    * @param userName -username of an employee
    * @return list of trips assigned to that employee
    */
   List<Trip> findTripsByEmployeeUserName(String userName);

   /**
    *
    * @param trackingId -id of the order
    * @return shipment or letter for the given id
    */
   Optional<AbstractOrder> findByTrackingId(Long trackingId);

   /**
    *
    * @param tripId -id of a trip
    * @return all orders being delivered by that trip
    */
   Optional<Trip> findByTripId(Long tripId);

   /**
    * for now a chronjob - as it only simulates the behaviour
    *
    * puts all letters which have the status registered into the status printing
    */
   void printRegisteredLetter();

   /**
    * for now a chronjob - as it only simulates the behaviour
    *
    * plans a trip for all printed letter - all letters printed since the last call of this method get assigned to the same trip
    * ---> if called every 24 hours it would create the desired once a day delivery of letters
    */
   void planPrintedLetter() throws LetterPlanningException;

   /**
    * for now a chronjob - as it only simulates the behaviour
    *
    * plans a trip for all registered shipments - all registered shipments since the last call of this method get assigned to their own trip
    */
   void planRegisteredShipments() throws OrderPlanningException;

   /**
    * needed for security purposes - i.e. not to show information about a shipment to anyone but its owner
    *
    * @param userName - username of the customer
    * @param trackingId - id of the order
    * @return boolean
    */
   boolean checkIfOrderBelongsToCustomer(String userName,Long trackingId);

   /**
    *
    * @param oldLetter - letter currently in the db
    * @param newLetter - transient letter object -> containing the editing information given
    * @return boolean
    */
   boolean editOrderLetter(Letter oldLetter, Letter newLetter);

   /**
    *
    * @param oldShipment - shipment currently in the db
    * @param newShipment - transient shipment object -> containing the editing information given
    * @return boolean
    */
   boolean editOrderShipment(Shipment oldShipment, Shipment newShipment);

   /**
    * cancels a order - can only be performed while the order is not being delivered
    * @param order -order to cancel
    */
   void cancelOrder(AbstractOrder order);

   /**
    * delivers a trip and puts all orders corresponding to said trip into the status -> delivered
    *
    * @param tripToDeliver -trip to deliver
    */
   void deliverTrip(Trip tripToDeliver);
}
