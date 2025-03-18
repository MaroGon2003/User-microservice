package com.powerup.user_microservice.infrastructure.out.jpa.adapter;

import com.powerup.user_microservice.infrastructure.out.jpa.entity.UserRolesEntity;
import com.powerup.user_microservice.infrastructure.out.jpa.repository.IUserRolesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRolesRepository userRolesRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserRolesEntity userRolesEntity = userRolesRepository
                .findByUserEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return new UserDetailsImpl(userRolesEntity);
    }
}
