package ua.bondarenko.hellobackend.repository;

import ua.bondarenko.hellobackend.entity.Contact;

import java.util.List;

public interface ContactRepository {
    List<Contact> getContacts(String regex, int offset, int limit);
}
