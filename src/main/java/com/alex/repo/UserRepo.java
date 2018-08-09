package com.alex.repo;

import com.alex.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public class UserRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User getUserByName(String username) {
        StringBuilder sql = new StringBuilder("select * from user where name=?");
        List<User> userList = jdbcTemplate.query(sql.toString(), new Object[]{username}, new BeanPropertyRowMapper<>(User.class));
        if (!CollectionUtils.isEmpty(userList)) {
            return userList.get(0);
        }
        return null;
    }

}
