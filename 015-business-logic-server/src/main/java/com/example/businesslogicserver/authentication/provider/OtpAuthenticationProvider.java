package com.example.businesslogicserver.authentication.provider;

import com.example.businesslogicserver.authentication.OtpAuthentication;
import com.example.businesslogicserver.authentication.proxy.AuthenticationServerProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class OtpAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private AuthenticationServerProxy proxy;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String code=authentication.getCredentials().toString();

        boolean result=proxy.sendOtp(username,code);

        if(result){
            return new OtpAuthentication(username,code);
        }
        else{
            throw new BadCredentialsException("Bad credential exception !");
        }

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OtpAuthentication.class.isAssignableFrom(authentication);
    }
}
