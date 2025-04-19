package com.fabiornt.rest_template.domain.model;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.fabiornt.rest_template.domain.entity.User;
import com.fabiornt.rest_template.http.LinkBuilder;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, UserModel> {

    @Override
    public UserModel toModel(User user) {
        UserModel userModel = UserModel.fromEntity(user);

        // Add links using the centralized LinkBuilder
        for (Link link : LinkBuilder.forUser(user.getId())) {
            userModel.add(link);
        }

        return userModel;
    }
}
