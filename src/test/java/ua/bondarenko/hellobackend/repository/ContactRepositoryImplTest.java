package ua.bondarenko.hellobackend.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.bondarenko.hellobackend.HelloServletInitializer;
import ua.bondarenko.hellobackend.entity.Contact;

import java.util.Arrays;
import java.util.List;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = HelloServletInitializer.class)
public class ContactRepositoryImplTest {
    public static final Contact CONTACT1 = new Contact(62, "Yadira");
    public static final Contact CONTACT2 = new Contact(126, "Yael");

    @Autowired
    ContactRepository contactRepository;

    @Test
    public void getContacts() throws Exception {
        List<Contact> filteredContacts = contactRepository.getContacts("^[A-X].*$", 0, 250);

        Assert.assertEquals(2, filteredContacts.size());
        Assert.assertEquals(Arrays.asList(CONTACT1, CONTACT2), filteredContacts);
    }
}