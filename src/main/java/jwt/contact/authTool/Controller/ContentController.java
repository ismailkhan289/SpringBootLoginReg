
/**
 * This file defines the ContentController class, which handles HTTP GET requests
 * for the login, signup, and home pages in a Spring Boot application.
 * 
 * The controller provides the following endpoints:
 * - /login: Redirects authenticated users to the index page, otherwise returns the login view.
 * - /req/signup: Redirects authenticated users to the index page, otherwise returns the signup view.
 * - /index: Returns the index view.
 * 
 * The controller uses Spring Security to check the authentication status of users.
 */
package jwt.contact.authTool.Controller;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContentController {

    @GetMapping("/login")
    public String login() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/index";
        }
        return "login";
    }

    @GetMapping("/req/signup")
    public String signup() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {
            return "redirect:/index";
        }
        return "signup";
    }

    @GetMapping("/index")
    public String home() {
        return "index";
    }
}
