package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {

    public UserDaoJDBCImpl() {}

    public void createUsersTable() {
        String sqlQuery = "CREATE TABLE IF NOT EXISTS users ( " +
                "id serial PRIMARY KEY, name VARCHAR(64), " +
                "lastName VARCHAR(64), age INT NOT NULL)";
        try (Connection connect = Util.getConnection(); Statement statement = connect.createStatement()) {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void dropUsersTable() {
        String sqlQuery = "DROP TABLE IF EXISTS users ";
        try (Connection connect = Util.getConnection(); Statement statement = connect.createStatement()) {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlQuery = "INSERT INTO users (name, lastName, age) VALUES (?,?,?)";
        try (Connection connect = Util.getConnection(); PreparedStatement preparedStatement = connect.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sqlQuery = "DELETE FROM users WHERE id = ?";
        try (Connection connect = Util.getConnection(); PreparedStatement preparedStatement = connect.prepareStatement(sqlQuery)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        String sqlQuery = "SELECT * FROM users";
        try (Connection connect = Util.getConnection(); Statement statement = connect.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sqlQuery);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));

                usersList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usersList;
    }

    public void cleanUsersTable() {
        String sqlQuery = "DELETE FROM users";
        try (Connection connect = Util.getConnection(); Statement statement = connect.createStatement()) {
            statement.execute(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
