#Shipping-Company
##By: Jakob Erben - 3148826

##Login
###Customer : 
username: shippingSteven - password: GnomeLachen3
###Employees : 
username: shippingSoren - password: HammerPflanze7  
username: shippingRasmus - password: HaferDieb14

##Scheduled Tasks
These tasks move the orders along by updating their statuses and  
or planning trips.
---
  
executed every: 5min  
time between printing and planning of the letters is 2,5 min

---
printRegisteredLetters : changes a letters sttaus from registered to printing
planPrintedLetters: plans a trip for all letter printed since the last call of this function

planRegisteredShipments : creates a corresponding trip for a registered shipment

##TripCreation

A trip is assigned to an employee with the least amount of assigned undelivered trips.  
A Shipment with more than with more than 200 Bundles requires a truck which needs  
2 employees to operate, therefore 2 employees are assigned. If one marks the trip as completed it  
is completed for both of them.

The only 2 employees currently working for the company are the 2 employees which login-information 
can bew found above.

##Editing and canceling orders

An order can be edited or canceled while not being delivered, in delivery or canceled.  


Sign in --> My Orders --> Details

The corresponding options will be present if editing or canceling is possible.

