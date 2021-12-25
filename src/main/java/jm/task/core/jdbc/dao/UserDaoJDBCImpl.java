package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement("CREATE TABLE IF NOT EXISTS users" +
                            "(id BIGINT NOT NULL AUTO_INCREMENT, " +
                            "name VARCHAR(50) NOT NULL, " +
                            "lastName VARCHAR(50) NOT NULL, " +
                            "age TINYINT NOT NULL, " +
                            "PRIMARY KEY (id))");
            statement.executeUpdate();
            System.out.println("Таблица создана.");
        } catch (SQLException e) {
            System.out.println("Таблица не создана или уже существует.");
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement preparedStatement = Util.getConnection()
                .prepareStatement("DELETE FROM users WHERE id=?")){
            preparedStatement.setInt(1, 3);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getConnection()
                .prepareStatement("INSERT INTO users VALUES (?,?,?)")) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement preparedStatement = Util.getConnection()
                .prepareStatement("DELETE FROM users WHERE id =?")){
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = null;
        User user = null;
        try (PreparedStatement preparedStatement = Util.getConnection()
                .prepareStatement("SELECT * FROM users")){
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {
        try (PreparedStatement preparedStatement = Util.getConnection()
                .prepareStatement("TRUNCATE TABLE users")){
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}