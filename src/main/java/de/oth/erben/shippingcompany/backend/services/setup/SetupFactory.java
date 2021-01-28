package de.oth.erben.shippingcompany.backend.services.setup;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SetupFactory {

    @Bean
    public EmployeeSetupComponent createEmployeeSetupService(){
        return new EmployeeSetupComponent();
    }

    @Bean
    public VehicleSetupComponent createVehicleService(){
        return new VehicleSetupComponent();
    }

    @Bean @Qualifier("partner")
    public AbstractCustomerSetup createPartnerSetup(){
        return new PartnerAccountSetupComponent();
    }

    @Bean @Qualifier("customer")
    public AbstractCustomerSetup createCustomerSetup(){
        return new CustomerAccountSetup();
    }

}
