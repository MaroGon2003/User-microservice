package com.powerup.user_microservice.infrastructure.out.jpa.adapter;

import com.powerup.user_microservice.infrastructure.out.jpa.entity.UserEntity;
import com.powerup.user_microservice.infrastructure.out.jpa.repository.IUserRepository;
import com.powerup.user_microservice.infrastructure.utils.InfrastructureConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository
                .findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(InfrastructureConstants.USER_NOT_FOUND + email));

        return new UserDetailsImpl(userEntity);
    }
}
