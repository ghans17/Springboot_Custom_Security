package com.argusoft.authmodule.custom;

import com.argusoft.authmodule.MenuManagement.exceptions.AccessDeniedException;
import com.argusoft.authmodule.MenuManagement.exceptions.InvalidTokenException;
import com.argusoft.authmodule.MenuManagement.services.AuthorizationService;
import com.argusoft.authmodule.entities.*;
import com.argusoft.authmodule.services.TokenService;
import com.argusoft.authmodule.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;
import java.util.Optional;

@Aspect
@Component
@Order(1) // Ensure this aspect executes at the highest priority
public class AccessValidationAspect {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorizationService authorizationService;

    /**
     * This method is executed before any method annotated with @ValidateAccess.
     * It performs authentication and authorization checks based on the token, user roles,
     * menu access, and specific actions.
     */
    @Before("@annotation(validateAccess)")
    public void validateAccess(JoinPoint joinPoint, ValidateAccess validateAccess) throws Throwable {
        // 1. Extract the token from the HTTP request header
        String token = getTokenFromRequest();

        // 2. Authenticate the token
        if (token == null || !tokenService.validateAccessToken(token)) {
            throw new InvalidTokenException("Invalid or missing access token.");
        }

        // 3. Extend token expiration to ensure the user's session remains active
        Optional<Token> optionalToken = tokenService.findByAccessTokenHash(token);
        if (optionalToken.isEmpty()) {
            throw new InvalidTokenException("Token not found or has expired.");
        }
        Token existingToken = optionalToken.get();
        existingToken.setExpiresAt(LocalDateTime.now().plusHours(1)); // Extend expiration time
        tokenService.save(existingToken);

        // 4. Extract username and App-ID for further validation
        String username = extractUsername(token);
        String appId = validateAccess.appId();

        // 5. Validate if the user has access to the specified App-ID
        if (!authorizationService.hasAccessToAppId(username, appId)) {
            throw new AccessDeniedException("Access denied to the specified App-ID.");
        }

        // 6. Check if the user has access to the requested menu (API endpoint)
        String endpointUrl = getRequestUrl();
        if (!authorizationService.hasMenuAccess(username, appId, endpointUrl)) {
            throw new AccessDeniedException("Access denied to the requested menu.");
        }

        // 7. Validate if the user is authorized to perform the requested action on the menu
        if (!authorizationService.hasActionAccess(username, appId, endpointUrl)) {
            throw new AccessDeniedException("Permission denied for the requested action.");
        }
    }

    /**
     * Extract the username from the token.
     *
     * @param token The hashed access token
     * @return The username associated with the token
     */
    private String extractUsername(String token) {
        Optional<Token> tokenOptional = tokenService.findByAccessTokenHash(token);
        return tokenOptional.map(tokenEntity -> tokenEntity.getUser().getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid token or token has expired."));
    }

    /**
     * Extract the access token from the HTTP request header.
     *
     * @return The token, or null if it is not present
     */
    private String getTokenFromRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String token = request.getHeader("Authorization");
            return token != null ? token.replace("Bearer ", "") : null;
        }
        return null;
    }

    /**
     * Retrieve the requested URL from the HTTP request.
     *
     * @return The URI of the current request
     */
    private String getRequestUrl() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            return request.getRequestURI();
        }
        return null;
    }
}
