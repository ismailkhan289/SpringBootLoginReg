package jwt.contact.authTool.Model;

import java.util.Optional; // This is used to import the Optional class

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor; 
// This is a lombok annotation that generates a constructor 
//with 1 parameter for each field in your class.

@Service // This is a service class
@AllArgsConstructor // This is a lombok annotation that generates a constructor 
// with 1 parameter for each field in your class.

public class MyAppUserService implements UserDetailsService { // This class implements 
    //UserDetailsService interface

    @Autowired // This annotation is used to let Spring know that it should inject an 
    //instance of MyAppUserRepository here
    private MyAppUserRepository myAppUserRepository;

    @Override // This annotation is used to let the compiler know that the method is 
    // meant to override a method in a superclass
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException { 
        // This method is used to load user by username

        Optional<MyAppUser> user = myAppUserRepository.findByUsername(username); 
        // This is used to get the user by username
        // Optional is used to handle null values from objects
        if(user.isPresent()){ 
            var userObj = user.get(); // This is used to get the user object
            return User.builder() 
                    .username(userObj.getUsername()) // This is used to get the username
                    .password(userObj.getPassword()) // This is used to get the password
                    .build(); 
        }else{ // This is used to handle the exception
            throw new UsernameNotFoundException(username + " Not Found"); 
            // This is used to throw the exception
    }

}
}