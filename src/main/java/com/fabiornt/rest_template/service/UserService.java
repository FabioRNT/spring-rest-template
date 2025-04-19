package com.fabiornt.rest_template.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fabiornt.rest_template.domain.entity.User;
import com.fabiornt.rest_template.exception.EmailAlreadyExistsException;
import com.fabiornt.rest_template.exception.ResourceNotFoundException;
import com.fabiornt.rest_template.repository.UserRepository;

@Service
public class UserService
{
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(User user)
    {
        // Check if email already exists
        userRepository.findByEmail(user.getEmail())
            .ifPresent(existingUser -> {
                throw new EmailAlreadyExistsException(user.getEmail());
            });

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

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

        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setPassword(userDetails.getPassword());
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
    public User patchUser(Long id, User userPatch) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        // Only update fields that are not null
        if (userPatch.getUsername() != null) {
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
            user.setPassword(userPatch.getPassword());
        }

        return userRepository.save(user);
    }
}
