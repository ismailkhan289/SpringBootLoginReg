package jwt.contact.authTool.Model;
import jakarta.persistence.Entity; 
import jakarta.persistence.Id; 
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.*;

@Getter 
@Setter
@NoArgsConstructor 
@AllArgsConstructor 
@Entity
public class MyAppUser {
    @Id 
    @GeneratedValue(strategy = GenerationType.AUTO) 
    private Long id;   
    private String username;
    private String email;
    private String password;
}
