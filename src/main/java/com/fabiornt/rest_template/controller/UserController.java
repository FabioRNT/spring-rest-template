package com.fabiornt.rest_template.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
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
import com.fabiornt.rest_template.http.ResponseBuilder;
import com.fabiornt.rest_template.model.UserModel;
import com.fabiornt.rest_template.model.UserModelAssembler;
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

        Link selfLink = linkTo(methodOn(UserController.class).getUserById(createdUser.getId())).withSelfRel();
        Link usersLink = linkTo(methodOn(UserController.class).getAllUsers()).withRel("users");

        return ResponseBuilder.created(userModel, selfLink, usersLink);
    }

    @GetMapping
    public ResponseEntity<ApiResponseCollection<UserModel>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        List<UserModel> userModels = users.stream()
                .map(userModelAssembler::toModel)
                .collect(Collectors.toList());

        Link selfLink = linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel();

        return ResponseBuilder.collection(userModels, selfLink);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserModel>> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        UserModel userModel = userModelAssembler.toModel(user);
        Link selfLink = linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel();
        Link usersLink = linkTo(methodOn(UserController.class).getAllUsers()).withRel("users");

        return ResponseBuilder.success(userModel, selfLink, usersLink);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserModel>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody User userDetails) {

        User updatedUser = userService.updateUser(id, userDetails);
        UserModel userModel = userModelAssembler.toModel(updatedUser);

        Link selfLink = linkTo(methodOn(UserController.class).getUserById(id)).withSelfRel();
        Link usersLink = linkTo(methodOn(UserController.class).getAllUsers()).withRel("users");

        return ResponseBuilder.success(userModel, selfLink, usersLink);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseBuilder.noContent();
    }
}