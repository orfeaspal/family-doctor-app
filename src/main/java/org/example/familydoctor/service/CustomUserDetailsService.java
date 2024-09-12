package org.example.familydoctor.service;

import org.example.familydoctor.model.Citizen;
import org.example.familydoctor.model.Doctor;
import org.example.familydoctor.model.Role;
import org.example.familydoctor.model.User;
import org.example.familydoctor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRole().getAuthorities()
        );
    }

    public Long loadUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return user.getId();
    }

    public User loadUserEntityByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public Long loadAssociatedEntityIdByUsername(String username) {
        User user = loadUserEntityByUsername(username);
        if (user.getRole() == Role.CITIZEN && user.getCitizen() != null) {
            return user.getCitizen().getId();
        } else if (user.getRole() == Role.DOCTOR && user.getDoctor() != null) {
            return user.getDoctor().getId();
        }
        return null;
    }
}
