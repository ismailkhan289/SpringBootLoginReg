package jwt.contact.authTool.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // This is a controller class
public class ContentController { // This is a class named ContentController
    @GetMapping("/login") 
    // This is a method that maps HTTP GET requests onto specific handler methods
    public String login() { // This is a method named login
        return "login";
    }

    @GetMapping("/req/signup") 
    // This is a method that maps HTTP GET requests onto specific handler methods
    public String signup() { 
        return "signup";
    }    

}
