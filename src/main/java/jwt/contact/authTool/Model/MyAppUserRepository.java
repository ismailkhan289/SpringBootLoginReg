package jwt.contact.authTool.Model; 

import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository; 
// This is used to import the JpaRepository interface
import org.springframework.stereotype.Repository; 
// This is used to indicate that the class provides 
//the mechanism for storage, retrieval, search, update and delete operation on objects.



@Repository
public interface MyAppUserRepository extends JpaRepository<MyAppUser, Long> { 
    // This is an interface that extends JpaRepository interface
    Optional<MyAppUser> findByUsername(String username); 
    // This is used to get the user by username
}
