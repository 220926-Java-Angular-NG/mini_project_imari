package com.revature.repos;

import com.revature.models.User;
import com.revature.utils.CRUDDaoInterface;
import com.revature.utils.ConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepo implements CRUDDaoInterface<User> {
    public Connection connection;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepo.class);

    public UserRepo() {
        try {
            connection = ConnectionManager.getConnection();
            System.out.println(connection.getSchema());

        } catch (SQLException sqlException) {
            LOGGER.error(sqlException.getMessage());
        }
    }

    @Override
    public int create(User user) {
        try {
            String sql = "INSERT INTO users (id, first_name, last_name, email, pass_word, birth_date, zodiac, mood) " +
                    "VALUES (default,?,?,?,?,?,?,?) ";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            preparedStatement.setString(1, user.getFirstname());
            preparedStatement.setString(2, user.getLastname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setDate(5, Date.valueOf(user.getBirthdate()));
            preparedStatement.setString(6, user.getZodiac());
            preparedStatement.setString(7, user.getMood());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            resultSet.next();

            return resultSet.getInt("id");

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return 0;
    }

    @Override
    public List<User> getAll() {
        List<User> users = new ArrayList<User>();

        try {
            String sql = "SELECT * FROM users";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("id"));
                user.setFirstname(resultSet.getString("first_name"));
                user.setLastname(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("pass_word"));
                user.setBirthdate(resultSet.getDate("birth_date").toString());
                user.setZodiac(resultSet.getString("zodiac"));
                user.setMood(resultSet.getString("mood"));

                users.add(user);
            }

            return users;

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return null;
    }

    @Override
    public User getById(int id) {
        try {
            String sql = "SELECT * FROM users WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            User user = new User();

            while (resultSet.next()) {
                user.setId(resultSet.getInt("id"));
                user.setFirstname(resultSet.getString("first_name"));
                user.setLastname(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("pass_word"));
                user.setBirthdate(resultSet.getDate("birth_date").toString());
                user.setZodiac(resultSet.getString("zodiac"));
                user.setMood(resultSet.getString("mood"));
            }

            return user;

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return null;
    }

    @Override
    public User update(User user) {
        try {
            String sql = "UPDATE users SET mood = ? WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getMood());
            preparedStatement.setInt(2, user.getId());

            preparedStatement.executeUpdate();

            ResultSet resultSet = preparedStatement.getGeneratedKeys();

            while (resultSet.next()) {
                user.setMood(resultSet.getString("mood"));
            }

            return user;

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return null;
    }

    @Override
    public boolean delete(User user) {
        try {
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, user.getId());

            preparedStatement.execute();
            return true;

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return false;
    }

    @Override
    public boolean deleteById(int id) {
        try {
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1, id);

            preparedStatement.execute();
            return true;

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }

        return false;
    }

    public User loginUser(User user) {
        try {
            String sql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.getEmail());

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next() && resultSet.getString("pass_word").equals(user.getPassword())) {
                return new User(resultSet.getInt("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("email"),
                        resultSet.getString("pass_word"),
                        resultSet.getDate("birth_date").toString(),
                        resultSet.getString("zodiac"),
                        resultSet.getString("mood"));
            }

        } catch (Exception exception) {
            System.out.println("This is the userDAO: " + exception.getMessage());
        }

        return null;
    }
}
