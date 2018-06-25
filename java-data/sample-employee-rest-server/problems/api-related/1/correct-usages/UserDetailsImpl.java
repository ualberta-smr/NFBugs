package com.derrikcurran.sample.employeerestserver.security;

import com.derrikcurran.sample.employeerestserver.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private long id;
    
    long getId() {
        return id;
    }
}
