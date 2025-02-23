package jwt.contact.authTool.Controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // This is a controller class
public class ContentController { // This is a class named ContentController

    @GetMapping("/login") 
    // This is a method that maps HTTP GET requests onto specific handler methods
    public String login() { // This is a method named login
        
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // ✅ Check if user is already authenticated
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) { 
            // ✅ Check if user is already authenticated
            return "redirect:/index"; // ✅ Redirect to index page
        }

        return "login"; // ✅ Show login page if user is not logged in
    }

    @GetMapping("/req/signup") 
    // This is a method that maps HTTP GET requests onto specific handler methods
    public String signup() { 
        return "signup";
    }
    
    @GetMapping("/index") 
    // This is a method that maps HTTP GET 
     //requests onto specific handler methods
    public String home() { 
        return "index";
    } 

}
