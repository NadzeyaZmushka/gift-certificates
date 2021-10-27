package com.epam.esm.mapper;

import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapper implements EntityMapper<User> {

    private static final String USER_ID = "id";
    private static final String USER_NAME = "name";
    private static final String USER_SURNAME = "surname";

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong(USER_ID));
        user.setName(rs.getString(USER_NAME));
        user.setSurname(rs.getString(USER_SURNAME));

        return user;
    }
}
