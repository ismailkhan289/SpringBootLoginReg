/**
 * This file defines the MyAppUserRepository interface, which is part of the 
 * jwt.contact.authTool.Model package. This repository interface is used for 
 * performing CRUD operations and custom queries on the MyAppUser entity.
 * 
 * It extends a Spring Data repository interface, providing a set of methods 
 * to interact with the database without the need for boilerplate code.
 * 
 * The repository pattern is used to encapsulate the logic required to access 
 * data sources, providing a more object-oriented view of the persistence layer.
 */
package jwt.contact.authTool.Model; 

import java.util.Optional; 

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository; 
// This is used to indicate that the class provides 
//the mechanism for storage, retrieval, search, update and delete operation on objects.



@Repository
public interface MyAppUserRepository extends JpaRepository<MyAppUser, Long> { 
    // This is an interface that extends JpaRepository interface
    Optional<MyAppUser> findByUsername(String username); 
    // This is used to get the user by username
}
