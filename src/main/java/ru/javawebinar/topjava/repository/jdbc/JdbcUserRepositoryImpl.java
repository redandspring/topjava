package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.sql.DataSource;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcUserRepositoryImpl implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepositoryImpl(DataSource dataSource, JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(dataSource)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else {
            namedParameterJdbcTemplate.update(
                    "UPDATE users SET name=:name, email=:email, password=:password, " +
                            "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource);
        }

        Set<Role> roles = user.getRoles();
        if (roles != null){
            final List<Role> rolesList = new ArrayList<>(roles);
            jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId());
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?,?)", new BatchPreparedStatementSetter(){
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException{
                    Role role = rolesList.get(i);
                    ps.setInt(1, user.getId());
                    ps.setString(2, role.toString());
                }

                @Override
                public int getBatchSize(){
                    return rolesList.size();
                }
            });
        }

        return user;
    }

    @Override
    @Transactional
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return getUserWithRoles(users);
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return getUserWithRoles(users);
    }

    private User getUserWithRoles(List<User> users)
    {
        User user = DataAccessUtils.singleResult(users);
        if (user != null){
            List<Role> roles = jdbcTemplate.query("SELECT * FROM user_roles WHERE user_id=?",
                    (rs, n) -> Role.valueOf(rs.getString("role")),
                    user.getId());
            if (roles != null){
                user.setRoles(new HashSet<>(roles));
            }
        }
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> users = jdbcTemplate.query(
                "SELECT * FROM users LEFT JOIN user_roles ON users.id = user_roles.user_id ORDER BY name, email",
                (ResultSetExtractor<List<User>>) rs ->
                {
                    Map<String, User> map = new TreeMap<>();
                    User user;

                    while(rs.next()) {

                        String id = rs.getString("name") + rs.getString("email");
                        user = map.get(id);

                        if ( user == null ) {
                            user = new User();
                            user.setId(rs.getInt("id"));
                            user.setName(rs.getString("name"));
                            user.setEmail(rs.getString("email"));
                            user.setPassword(rs.getString("password"));
                            user.setRegistered(rs.getDate("registered"));
                            user.setEnabled(rs.getBoolean("enabled"));
                            user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                            user.setRoles(new HashSet<>());
                            map.put(id, user);
                        }

                        String roleName = rs.getString("role");
                        if ( roleName != null ) {
                            user.getRoles().add(Role.valueOf(roleName));
                        }
                    }
                    return new ArrayList<>(map.values());
                }

        );

        return users;
    }
}
