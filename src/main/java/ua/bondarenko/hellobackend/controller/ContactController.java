package ua.bondarenko.hellobackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ua.bondarenko.hellobackend.entity.Contact;
import ua.bondarenko.hellobackend.repository.ContactRepository;

import java.util.List;

@RestController
public class ContactController {
    private ContactRepository contactRepository;

    @Autowired
    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/hello/contacts")
    public ResponseEntity<?> allContacts(@RequestParam("nameFilter") String nameFilter,
                                         @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
                                         @RequestParam(value = "limit", required = false, defaultValue = "20") int limit) {
        if (StringUtils.isEmpty(nameFilter)) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        List<Contact> filteredContacts = contactRepository.getContacts(nameFilter, offset, limit);
        return new ResponseEntity<List<Contact>>(filteredContacts, HttpStatus.OK);
    }
}
