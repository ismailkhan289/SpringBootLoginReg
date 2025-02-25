/**
 * Represents a contact entity in the contact list project.
 * This entity is mapped to the "contacts" table in the database.
 * 
 * Attributes:
 * - id: Unique identifier for the contact, generated using UUID.
 * - name: Name of the contact.
 * - email: Email address of the contact.
 * - titles: Titles associated with the contact.
 * - phone: Phone number of the contact.
 * - address: Address of the contact.
 * - status: Status of the contact.
 * - photoUrl: URL to the contact's photo.
 * - createdAt: Date when the contact was created.
 * - updatedAt: Date and time when the contact was last updated.
 * 
 * The entity uses JPA annotations for ORM mapping and Lombok annotations for boilerplate code reduction.
 * The createdAt and updatedAt fields are automatically set during the creation and update of the entity.
 * 
 * Annotations used:
 * - @Entity: Specifies that the class is an entity.
 * - @Table: Specifies the table name in the database.
 * - @Id: Specifies the primary key of the entity.
 * - @UuidGenerator: Generates a UUID for the id field.
 * - @Column: Specifies the column details for the fields.
 * - @PrePersist: Method annotated to execute before the entity is persisted.
 * - @PreUpdate: Method annotated to execute before the entity is updated.
 * - @JsonInclude: Includes non-null fields in JSON serialization.
 * - @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor: Lombok annotations for 
 *   generating boilerplate code.
 */
package jwt.contact.authTool.Model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.hibernate.annotations.UuidGenerator;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude
@Table(name="contacts")

public class Contact {
    @Id
    @UuidGenerator
    @Column(name = "id", unique = true,updatable = false )//this is the primary key of the entity
    private String id;
    private String name;
    private String email;
    private String titles;
    private String phone;
    private String address;
    private String status;
    private String photoUrl;

    @Column(name = "created_at") private LocalDate createdAt;
    @Column(name = "updated_at") private LocalDateTime updatedAt;

    //this method is called before the entity is persisted
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDateTime.now();

    }
    
    //this method is called before the entity is updated
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

}
