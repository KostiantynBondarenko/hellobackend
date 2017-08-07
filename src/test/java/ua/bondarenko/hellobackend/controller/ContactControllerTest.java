package ua.bondarenko.hellobackend.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.bondarenko.hellobackend.entity.Contact;
import ua.bondarenko.hellobackend.repository.ContactRepository;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ContactControllerTest {
    private static long ID = 1L;
    private static String NAME = "Alex";
    private static String REGEX = "^A.*$";
    private MockMvc mockMvc;
    private ContactRepository contactRepository;

    @Before
    public void setUp() throws Exception {
        contactRepository = mock(ContactRepository.class);
        ContactController contactController = new ContactController(contactRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(contactController).build();

        List<Contact> contacts = Collections.singletonList(new Contact(ID, NAME));
        when(contactRepository.getContacts(REGEX, 0, 20)).thenReturn(contacts);
    }

    @Test
    public void testController() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                get("/hello/contacts").param("nameFilter", REGEX)
        );

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(ID))
                .andExpect(jsonPath("$[0].name").value(NAME));

        verify(contactRepository).getContacts(REGEX, 0, 20);
    }

    @Test
    public void testControllerEmpty() throws Exception {
        ResultActions resultActions = mockMvc.perform(
                get("/hello/contacts").param("nameFilter", "")
        );

        resultActions.andExpect(status().is(400));

    }
}