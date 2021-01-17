package de.oth.erben.shippingcompany.backend.security;

import de.oth.erben.shippingcompany.backend.security.authorities.AbstractAuthority;
import de.oth.erben.shippingcompany.backend.security.authorities.CutomerAuthority;
import de.oth.erben.shippingcompany.backend.security.authorities.EmployeeAuthority;
import de.oth.erben.shippingcompany.backend.security.roles.Roles;
import de.oth.erben.shippingcompany.backend.services.UserService;
import de.othr.brs31213.sw.othauth.dto.requests.LoginRequest;
import de.othr.brs31213.sw.othauth.dto.responses.UserInformationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class AuthProvider implements AuthenticationProvider {

    @Value("${oth-auth.api.url}")
    private String OTH_AUTH_URL;
    @Value("${oth-auth.api.key}")
    private String API_KEY_OTH_AUTH;

    @Autowired
    private RestTemplate restClient;

    @Autowired
    private UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getCredentials().toString();

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(userName);
        loginRequest.setPassword(password);
        loginRequest.setServiceKey(API_KEY_OTH_AUTH);

        try {

            UserInformationResponse response = restClient.postForObject(OTH_AUTH_URL + "/api/authenticate", loginRequest, UserInformationResponse.class);

            List<String> roles = response.getRoles();

            //filter roles to only contain roles discerning this application
            List<String> externalIdentifier = new ArrayList<>();
            for(Roles role : Roles.values()){
                externalIdentifier.add(role.getExternalIdentifier());
            }

            roles.retainAll(externalIdentifier);

            List<AbstractAuthority> authorities = new ArrayList<>();
            for(String role: roles){
                if(role.equals(Roles.CUSTOMER.getExternalIdentifier())){
                    authorities.add(new CutomerAuthority());
                    //check if internal accounts exists and create one if needed
                    if(!userService.checkIfCustomerExists(response.getUsername())){
                        userService.createCustomerAccount(response);
                    }
                }

                if(role.equals(Roles.EMPLOYEE.getInternalIdentifier())){
                    authorities.add(new EmployeeAuthority());
                    //check if internal account exits and create one if needed
                    if(!userService.checkIfEmployeeExists(response.getUsername())){
                        userService.createEmployeeAccount(response);
                    }
                }
            }

            return new UsernamePasswordAuthenticationToken(userName,password,authorities);

        } catch (HttpClientErrorException e) {
            //printing and passing
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
