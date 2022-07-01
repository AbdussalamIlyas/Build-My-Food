package de.htwberlin.webtech.authentication.controllers;

import de.htwberlin.webtech.api.recipe.Recipe;
import de.htwberlin.webtech.authentication.models.ERole;
import de.htwberlin.webtech.authentication.models.Role;
import de.htwberlin.webtech.authentication.models.User;
import de.htwberlin.webtech.authentication.repository.RoleRepository;
import de.htwberlin.webtech.authentication.repository.UserRepository;
import de.htwberlin.webtech.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    private final Integer ROLE_MODERATOR_ID = 2;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users = new ArrayList<>(userRepository.findAll());
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/user/{id}/verified")
    public ResponseEntity<User> verifiedUser(@PathVariable Long id) {

        User user = userRepository.findById(id).get();
        Role role = roleRepository.findById(ROLE_MODERATOR_ID)
                .orElseThrow(() -> new ResourceNotFoundException("Not found Role Moderator"));

        user.getRoles().add(role);

        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    @DeleteMapping("/user/{id}/unverified")
    public ResponseEntity<User> removeVerified(@PathVariable Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found User with id = " + id));

        user.removeRole(ROLE_MODERATOR_ID);
        userRepository.save(user);

        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
