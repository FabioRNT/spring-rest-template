package com.fabiornt.rest_template.model;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.fabiornt.rest_template.controller.UserController;
import com.fabiornt.rest_template.domain.entity.User;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, UserModel> {

    @Override
    public UserModel toModel(User user) {
        UserModel userModel = UserModel.fromEntity(user);
        
        // Add self link
        userModel.add(linkTo(methodOn(UserController.class).getUserById(user.getId())).withSelfRel());
        
        // Add collection link
        userModel.add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("users"));
        
        return userModel;
    }
}
