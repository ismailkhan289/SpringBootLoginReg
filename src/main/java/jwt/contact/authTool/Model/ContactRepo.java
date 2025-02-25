/**
 * This interface represents the repository for the Contact entity.
 * It extends JpaRepository to provide CRUD operations and additional
 * query methods for the Contact entity.
 * 
 * The ContactRepo interface is annotated with @Repository to indicate
 * that it is a Spring Data repository.
 * 
 * Custom query methods can be defined in this interface, such as the
 * findById method which retrieves a Contact entity by its ID.
 * 
 * @see org.springframework.data.jpa.repository.JpaRepository
 * @see my.contact.contactlist.model.Contact
 */
package jwt.contact.authTool.Model;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
 
@Repository
public interface ContactRepo extends JpaRepository<Contact, String>{
    Optional<Contact> findById(String id);
}
