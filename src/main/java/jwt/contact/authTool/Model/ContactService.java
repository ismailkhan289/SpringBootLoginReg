
/**
 * Service class for managing contacts.
 * 
 * This service provides methods to perform CRUD operations on contacts,
 * as well as uploading and managing contact photos.
 * 
 * Dependencies:
 * - ContactRepo: Repository for accessing contact data.
 * 
 * Methods:
 * - getAll(): Returns the total count of contacts.
 * - getAllContacts(int page, int size): Retrieves a paginated list of contacts.
 * - getContact(String id): Retrieves a contact by its ID.
 * - createContact(Contact contact): Creates a new contact.
 * - deleteContact(String id): Deletes a contact by its ID.
 * - uploadPhoto(String id, MultipartFile file): Uploads a photo for a contact and updates the contact's photo URL.
 * 
 * Private Fields:
 * - fileExtention: Function to extract the file extension from a filename.
 * - photoFunction: BiFunction to handle the photo upload process and return the photo URL.
 * 
 * Annotations:
 * - @Service: Indicates that this class is a Spring service.
 * - @Slf4j: Provides logging capabilities.
 * - @Transactional(rollbackOn = Exception.class): Ensures that methods are transactional and roll back on exceptions.
 * - @RequiredArgsConstructor: Generates a constructor with required arguments (final fields).
 * - @Getter and @Setter: Lombok annotations to generate getter and setter methods.
 */
package jwt.contact.authTool.Model;
import org.springframework.data.domain.Page;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import jwt.contact.authTool.Uploads.ContactImage;
import jwt.contact.authTool.Model.Contact;
import jwt.contact.authTool.Model.ContactRepo;

@Getter
@Setter
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor

public class ContactService {
    private final ContactRepo contactRepo;

    public Long getAll() {
        return contactRepo.count();
    }

    public Page<Contact> getAllContacts(int page, int size) {
        return contactRepo.findAll(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt")));
    }

    public Contact getContact(String id) {
        return contactRepo.findById(id).orElseThrow(() -> new RuntimeException("Contact not found"));
    }

    public Contact createContact(Contact contact) {
        return contactRepo.save(contact);
    }

    public void deleteContact(String id) {
        if (contactRepo.existsById(id)) {
            contactRepo.deleteById(id);
        } else {
            throw new RuntimeException("contact not found " + id);
        }
    }

    public String uploadPhoto(String id, MultipartFile file) {
        log.info("Saving Picture of Contact Person: {}", id);
        Contact contact = getContact(id);
        String photoUrl = photoFunction.apply(id, file);
        contact.setPhotoUrl(photoUrl);
        contactRepo.save(contact);
        return photoUrl;
    }

    // Function to extract the file extension from a filename
    private final Function<String, String> fileExtention = filename -> Optional.of(filename)
            .filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse(".png");

    // BiFunction to handle the photo upload process and return the photo URL
    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) -> {
        String filename = id + fileExtention.apply(image.getOriginalFilename());
        try {
            Path fileStorageLocation = Paths.get(ContactImage.PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectories(fileStorageLocation);
            }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/contacts/image/" + filename).toUriString();
        } catch (Exception e) {
            throw new RuntimeException("Unable to save Image");
        }
    };
}
