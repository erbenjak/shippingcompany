package de.oth.erben.shippingcompany.backend.services.REST;

public interface ISignUrlLoader {
    /**
     * loads the url needed for customer sign up from the metadata api of the authentication-provider
     *
     * @return sign-up-url for redirection
     */
    public String loadSignInUrl();
}
