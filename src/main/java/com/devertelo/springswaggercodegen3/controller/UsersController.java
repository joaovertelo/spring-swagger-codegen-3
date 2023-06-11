package com.devertelo.springswaggercodegen3.controller;

import com.devertelo.springswaggercodegen3.api.UsersApi;
import com.devertelo.springswaggercodegen3.model.UserRequest;
import com.devertelo.springswaggercodegen3.model.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class UsersController implements UsersApi {

    private Map<String, UserResponse> users = new HashMap<>();

    @Override
    public ResponseEntity<UserResponse> createUser(UserRequest user) {
        var newUser = toUserResponse(user);
        users.put(newUser.getId(), newUser);
        return ResponseEntity.ok(newUser);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String id) {
        UserResponse user = users.remove(id);
        return user != null ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> allUsers = this.users.values()
                .stream()
                .toList();
        return ResponseEntity.ok(allUsers);
    }

    @Override
    public ResponseEntity<UserResponse> getUserById(String id) {
        var user = users.get(id);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<UserResponse> updateUser(String id, UserRequest userRequest) {

        var userById = users.get(id);

        if (userById != null) {
            var newUser = toUserResponse(userRequest);
            newUser.setId(userById.getId());
            newUser.setCreatedAt(userById.getCreatedAt());
            users.put(id, newUser);
            return ResponseEntity.ok(newUser);
        }
        return ResponseEntity.notFound().build();
    }

    public UserResponse toUserResponse(UserRequest request) {
        return new UserResponse()
                .id(UUID.randomUUID().toString())
                .createdAt(OffsetDateTime.now())
                .username(request.getUsername())
                .name(request.getName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .status(request.getStatus())
                .password(request.getPassword());
    }
}
