package org.example.familydoctor.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.familydoctor.model.User;
import org.example.familydoctor.service.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    public CustomAuthenticationFilter(String loginUrl, AuthenticationManager authenticationManager, CustomUserDetailsService userDetailsService) {
        this.setFilterProcessesUrl(loginUrl);
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // Extract login details from the request
            Map<String, String> credentials = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            String username = credentials.get("username");
            String password = credentials.get("password");

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(username, password);

            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read login credentials", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // Retrieve user details
        String username = authResult.getName();
        Long userId = userDetailsService.loadUserIdByUsername(username);

        // Fetch user role
        User user = userDetailsService.loadUserEntityByUsername(username);
        String role = user.getRole().name();

        // Fetch associated entity ID (Citizen or Doctor ID)
        Long associatedEntityId = userDetailsService.loadAssociatedEntityIdByUsername(username);

        // Prepare response data
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("username", username);
        responseData.put("userId", userId);
        responseData.put("role", role);
        responseData.put("entityId", associatedEntityId);

        // Optionally add JWT token if you are using it
        // String token = JWT.create()... // Create JWT token
        // responseData.put("token", token);

        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(), responseData);
    }
}
