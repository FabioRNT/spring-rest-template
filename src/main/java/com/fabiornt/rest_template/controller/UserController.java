package com.fabiornt.rest_template.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fabiornt.rest_template.domain.entity.User;
import com.fabiornt.rest_template.exception.ResourceNotFoundException;
import com.fabiornt.rest_template.http.ApiResponse;
import com.fabiornt.rest_template.http.ApiResponseCollection;
import com.fabiornt.rest_template.http.LinkBuilder;
import com.fabiornt.rest_template.http.ResponseBuilder;
import com.fabiornt.rest_template.domain.model.UserModel;
import com.fabiornt.rest_template.domain.model.UserModelAssembler;
import com.fabiornt.rest_template.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserModelAssembler userModelAssembler;

    @Autowired
    public UserController(UserService userService, UserModelAssembler userModelAssembler) {
        this.userService = userService;
        this.userModelAssembler = userModelAssembler;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserModel>> createUser(@Valid @RequestBody User user) {
        User createdUser = userService.createUser(user);
        UserModel userModel = userModelAssembler.toModel(createdUser);

        // UserModel already has links from UserModelAssembler
        return ResponseBuilder.created(userModel);
    }

    @GetMapping
    public ResponseEntity<ApiResponseCollection<UserModel>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserModel> userModels = users.stream()
                .map(userModelAssembler::toModel)
                .collect(Collectors.toList());

        // Each UserModel already has links from UserModelAssembler
        // For collection-level links, we use the LinkBuilder.forUsers() in the ApiResponseCollection
        return ResponseBuilder.collection(userModels, LinkBuilder.forUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserModel>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        UserModel userModel = userModelAssembler.toModel(user);
        // UserModel already has links from UserModelAssembler
        return ResponseBuilder.success(userModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserModel>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User userDetails) {

        User updatedUser = userService.updateUser(id, userDetails);
        UserModel userModel = userModelAssembler.toModel(updatedUser);

        // UserModel already has links from UserModelAssembler
        return ResponseBuilder.success(userModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseBuilder.noContent();
    }
}