package com.fabiornt.rest_template.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fabiornt.rest_template.domain.entity.User;
import com.fabiornt.rest_template.exception.EmailAlreadyExistsException;
import com.fabiornt.rest_template.exception.ResourceNotFoundException;
import com.fabiornt.rest_template.exception.UsernameAlreadyExistsException;
import com.fabiornt.rest_template.repository.UserRepository;
import com.fabiornt.rest_template.security.model.Role;

@Service
public class UserService
{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User createUser(User user)
    {
        // Check if email already exists
        userRepository.findByEmail(user.getEmail())
            .ifPresent(existingUser -> {
                throw new EmailAlreadyExistsException(user.getEmail());
            });

        // Check if username already exists
        userRepository.findByUsername(user.getUsername())
            .ifPresent(existingUser -> {
                throw new UsernameAlreadyExistsException(user.getUsername());
            });

        // Encode password
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Add default role
        user.addRole(Role.ROLE_USER);

        return userRepository.save(user);
    }

    /**
     * Get all users without pagination
     *
     * @return List of all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get users with pagination
     *
     * @param pageable Pagination information
     * @return Page of users
     */
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        // Check if email is being changed and if it already exists
        if (!user.getEmail().equals(userDetails.getEmail())) {
            userRepository.findByEmail(userDetails.getEmail())
                .ifPresent(existingUser -> {
                    throw new EmailAlreadyExistsException(userDetails.getEmail());
                });
        }

        // Check if username is being changed and if it already exists
        if (!user.getUsername().equals(userDetails.getUsername())) {
            userRepository.findByUsername(userDetails.getUsername())
                .ifPresent(existingUser -> {
                    throw new UsernameAlreadyExistsException(userDetails.getUsername());
                });
        }

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());

        // Encode password if provided
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        // Preserve roles if not provided
        if (userDetails.getRoles() != null && !userDetails.getRoles().isEmpty()) {
            user.setRoles(userDetails.getRoles());
        }

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        // Check if user exists before deletion
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        userRepository.delete(user);
    }

    /**
     * Partially update a user with the provided fields
     *
     * @param id The user ID
     * @param userPatch The user object with fields to update
     * @return The updated user
     */
    @Transactional
    public User patchUser(Long id, User userPatch) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        // Only update fields that are not null
        if (userPatch.getUsername() != null) {
            // Check if username is being changed and if it already exists
            if (!user.getUsername().equals(userPatch.getUsername())) {
                userRepository.findByUsername(userPatch.getUsername())
                    .ifPresent(existingUser -> {
                        throw new UsernameAlreadyExistsException(userPatch.getUsername());
                    });
            }
            user.setUsername(userPatch.getUsername());
        }

        if (userPatch.getEmail() != null) {
            // Check if email is being changed and if it already exists
            if (!user.getEmail().equals(userPatch.getEmail())) {
                userRepository.findByEmail(userPatch.getEmail())
                    .ifPresent(existingUser -> {
                        throw new EmailAlreadyExistsException(userPatch.getEmail());
                    });
            }
            user.setEmail(userPatch.getEmail());
        }

        if (userPatch.getPassword() != null) {
            // Encode the password
            user.setPassword(passwordEncoder.encode(userPatch.getPassword()));
        }

        // Update roles if provided
        if (userPatch.getRoles() != null && !userPatch.getRoles().isEmpty()) {
            user.setRoles(userPatch.getRoles());
        }

        return userRepository.save(user);
    }
}
