package com.fabiornt.rest_template.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.fabiornt.rest_template.domain.entity.User;
import com.fabiornt.rest_template.exception.ResourceNotFoundException;
import com.fabiornt.rest_template.http.ApiResponse;
import com.fabiornt.rest_template.http.LinkBuilder;
import com.fabiornt.rest_template.http.ResponseBuilder;
import com.fabiornt.rest_template.domain.model.UserModel;
import com.fabiornt.rest_template.domain.model.UserModelAssembler;
import com.fabiornt.rest_template.service.UserService;
import com.fabiornt.rest_template.util.CsvConverter;

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

    /**
     * Get all users with content negotiation support.
     * Supports both JSON and CSV formats based on the Accept header.
     *
     * @param acceptHeader The Accept header from the request
     * @return Response with users in the requested format
     */
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, "text/csv"})
    public ResponseEntity<?> getAllUsers(@RequestHeader(value = HttpHeaders.ACCEPT, required = false) String acceptHeader) {
        List<User> users = userService.getAllUsers();
        List<UserModel> userModels = users.stream()
                .map(userModelAssembler::toModel)
                .collect(Collectors.toList());

        // Check if the client accepts CSV
        if (acceptHeader != null && acceptHeader.contains("text/csv")) {
            // Convert to CSV and return with appropriate headers
            String csvData = CsvConverter.toCsv(userModels);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("text/csv"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.csv")
                    .body(csvData);
        } else {
            // Default to JSON response
            // Each UserModel already has links from UserModelAssembler
            // For collection-level links, we use the LinkBuilder.forUsers() in the ApiResponseCollection
            return ResponseBuilder.collection(userModels, LinkBuilder.forUsers());
        }
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

    /**
     * Partially update a user with the provided fields
     *
     * @param id The user ID
     * @param userPatch The user object with fields to update
     * @return The updated user
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UserModel>> patchUser(
            @PathVariable Long id,
            @RequestBody User userPatch) {

        User patchedUser = userService.patchUser(id, userPatch);
        UserModel userModel = userModelAssembler.toModel(patchedUser);

        // UserModel already has links from UserModelAssembler
        return ResponseBuilder.success(userModel);
    }
}