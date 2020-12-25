package de.oth.erben.shippingcompany.services;

import de.oth.erben.shippingcompany.entities.AbstractOrder;
import de.oth.erben.shippingcompany.entities.Status;
import de.oth.erben.shippingcompany.repositorys.StatusReposirory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class OrderManagementService implements AbstractOrderManagementService{

    @Autowired
    StatusReposirory statusReposirory;

    @Override
    public boolean registerOrder(AbstractOrder newlyCreatedOrder) {
        Status registeredButNotPlanned = new Status();

        newlyCreatedOrder.setStatus(registeredButNotPlanned);
        statusReposirory.save(registeredButNotPlanned);

        return true;
    }
}
