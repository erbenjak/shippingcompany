package de.oth.erben.shippingcompany.backend.services.REST;

import de.othr.brs31213.sw.othauth.dto.responses.MetadataResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Scope("singleton")
@Service
public class SignInUrlLoader implements ISignUrlLoader{
    @Value("${oth-auth.api.url}")
    private String OTH_AUTH_URL;

    @Autowired
    private RestTemplate restClient;

    @Override
    public String loadSignInUrl() {
        try{
            MetadataResponse metadataResponse = restClient.getForObject(OTH_AUTH_URL+"/api/metadata",MetadataResponse.class);
            return metadataResponse.getRegistrationUrl();
        }catch(Exception e){
            System.out.println("Sign-in Url could not be loaded!");
            //since the signin-url could not be loaded the user will be stuck on the homescreen for now
            return "";
        }
    }
}
