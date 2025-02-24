/**
 * This controller handles user registration requests.
 * It provides an endpoint for creating new users.
 * 
 * Dependencies:
 * - MyAppUserRepository: Repository for managing user data.
 * - PasswordEncoder: Utility for encoding user passwords.
 * 
 * Endpoint:
 * - POST /req/signup: Creates a new user with the provided details.
 * 
 * Example request body:
 * {
 *   "username": "exampleUser",
 *   "password": "examplePassword",
 *   "email": "user@example.com"
 * }
 * 
 * Example response:
 * {
 *   "id": 1,
 *   "username": "exampleUser",
 *   "email": "user@example.com"
 * }
 */
package jwt.contact.authTool.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jwt.contact.authTool.Model.MyAppUser;
import jwt.contact.authTool.Model.MyAppUserRepository;

@RestController

public class RegistrationController {

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(value = "/req/signup", consumes = "application/json")
    public MyAppUser createUser(@RequestBody MyAppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return myAppUserRepository.save(user);
    }

}
