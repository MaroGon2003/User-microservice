package com.powerup.user_microservice.infrastructure.out.roleinterceptor;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.powerup.user_microservice.domain.spi.IRoleInterceptorPort;
import com.powerup.user_microservice.infrastructure.configuration.security.jwt.JwtToken;
import com.powerup.user_microservice.infrastructure.utils.InfrastructureConstants;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RoleInterceptorAdapter  implements IRoleInterceptorPort {

    private final HttpServletRequest request;
    private final JwtToken jwtToken;

    @Override
    public String getRoleName() {
        String token = request.getHeader(InfrastructureConstants.AUTHORIZATION_HEADER);
        if (token != null && token.startsWith(InfrastructureConstants.BEARER_PREFIX)) {
            token = token.substring(InfrastructureConstants.BEARER_PREFIX.length());
            DecodedJWT decodedJWT = jwtToken.validateToken(token);
            return jwtToken.getSpecificClaim(decodedJWT, InfrastructureConstants.ROLE_CLAIM).asString();
        } else {
            throw new IllegalArgumentException(InfrastructureConstants.JWT_MISSING_OR_INVALID);
        }
    }

    @Override
    public boolean isAdmin() {
        String role = getRoleName();
        return InfrastructureConstants.ROLE_ADMIN.equals(role);
    }

    @Override
    public boolean isSeller() {
        String role = getRoleName();
        return InfrastructureConstants.ROLE_SELLER.equals(role);
    }
}
