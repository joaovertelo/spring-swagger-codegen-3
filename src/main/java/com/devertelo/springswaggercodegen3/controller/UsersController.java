package com.devertelo.springswaggercodegen3.controller;

import com.devertelo.springswaggercodegen3.api.UsersApi;
import com.devertelo.springswaggercodegen3.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
public class UsersController implements UsersApi {

    private Map<String, User> users = new HashMap<>();

    @Override
    public ResponseEntity<User> createUser(User user) {
        user.setId(UUID.randomUUID().toString());
        user.setCreatedAt(OffsetDateTime.now());
        users.put(user.getId(), user);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<Void> deleteUser(String id) {
        User user = users.remove(id);
        return user != null ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> userList = users.values()
                .stream()
                .toList();
        return ResponseEntity.ok(userList);
    }

    @Override
    public ResponseEntity<User> getUserById(String id) {
        var user = users.get(id);
        return ResponseEntity.ok(user);
    }

    @Override
    public ResponseEntity<User> updateUser(String id, User user) {
        if (users.containsKey(id)) {
            user.setId(id);
            users.put(id, user);
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.notFound().build();
    }
}
