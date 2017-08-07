package ua.bondarenko.hellobackend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.bondarenko.hellobackend.entity.Contact;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Repository
@Transactional
public class ContactRepositoryImpl implements ContactRepository {
    private DataSource dataSource;

    @Autowired
    public ContactRepositoryImpl(@Qualifier("dataSource") DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Contact> getContacts(String regex, int offset, int limit) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        List<Contact> contacts = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);

        try {
            connection = dataSource.getConnection();
            connection.setAutoCommit(false);
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(String.format("SELECT * FROM contacts LIMIT %s OFFSET %s", limit, offset));
            String name;
            while (resultSet.next()) {
                name = resultSet.getString(2);
                Matcher matcher = pattern.matcher(name);
                if (!matcher.matches()) {
                    contacts.add(new Contact(resultSet.getLong(1), name));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return contacts;
    }
}
