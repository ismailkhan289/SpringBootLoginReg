
/**
 * This is the ContactController class, which is a REST controller for managing contacts.
 * It provides endpoints for creating, retrieving, updating, and deleting contacts, as well as uploading and retrieving contact photos.
 * 
 * Base URL: /contacts
 * 
 * Endpoints:
 * - POST /contacts: Create a new contact.
 * - GET /contacts: Retrieve a paginated list of contacts.
 * - GET /contacts/getAll: Retrieve the total number of contacts.
 * - GET /contacts/{id}: Retrieve a specific contact by ID.
 * - DELETE /contacts/{id}: Delete a specific contact by ID.
 * - PUT /contacts/photo: Upload a photo for a specific contact.
 * - GET /contacts/image/{filename}: Retrieve a contact photo by filename.
 * 
 * Dependencies:
 * - ContactService: Service layer for managing contacts.
 * 
 * Annotations:
 * - @RestController: Indicates that this class is a REST controller.
 * - @RequestMapping("/contacts"): Sets the base URL for the controller.
 * - @RequiredArgsConstructor: Generates a constructor with required arguments (final fields).
 * 
 * Exception Handling:
 * - Throws RuntimeException with a custom message if an error occurs while getting the total number of contacts.
 * 
 * Note:
 * - The createContact method returns a ResponseEntity with the created contact and sets the location header to "/contacts/userID".
 * - The getPhoto method reads the photo file from the file system and returns it as a byte array.
 */
package jwt.contact.authTool.Controller;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;


import org.springframework.http.MediaType;
import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import jwt.contact.authTool.Uploads.ContactImage;
import jwt.contact.authTool.Model.Contact;
import jwt.contact.authTool.Model.ContactService;


@RestController //this is a rest controller
@RequestMapping("/contacts") //this is the base url for the controller
@RequiredArgsConstructor


public class ContactController {

    
    //first declare the service injection in the controller
    private final ContactService contactService;

    @PostMapping(consumes = "application/json")
    public  ResponseEntity<Contact> createContact(@RequestBody Contact contact){
        return ResponseEntity
                .created(URI.create("/contacts/userID"))
                .body(contactService.createContact(contact));
    }
    
    @GetMapping
    public ResponseEntity<Page<Contact>> getContacts
            (@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size){
            return ResponseEntity.ok().body(contactService.getAllContacts(page, size));
             }
             
    @GetMapping("/getAll")
    public long getAll(){
        try {
        return contactService.getAll();

        } catch (Exception e) {
            throw new RuntimeException("Error Getting total number of contacts");
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable(value = "id") String id){
        return ResponseEntity.ok().body(contactService.getContact(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable String id){
         contactService.deleteContact(id);
         return ResponseEntity.noContent().build();
    }
    @PutMapping("/photo")
    public ResponseEntity<String> uploadPhoto(
            @RequestParam("id") String id, 
            @RequestParam("file")MultipartFile file){
        return ResponseEntity.ok().body(contactService.uploadPhoto(id, file));
    }

    @GetMapping(path = "/image/{filename}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public byte[] getPhoto(@PathVariable("filename") String filename) throws IOException{
        return Files.readAllBytes(Paths.get(ContactImage.PHOTO_DIRECTORY +filename));
    }
}
