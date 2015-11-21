package com.quizify.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Ajeet Kumar Meena on 15-11-2015.
 */
public class DatabaseController {
    String url;
    Connection connection;

    DatabaseController(String url, String root, String password) throws ClassNotFoundException, SQLException {
        this.url = url;
        Class.forName("com.mysql.jdbc.Driver");
        this.connection = DriverManager.getConnection(url, root, password);
    }

    int insertNewUser(Quizify.User user) throws SQLException {
        if( isFacebookIdExist(user.getFacebookId()) ) {
            return getUserId(user.getFacebookId());
        } else {
            String statement = "INSERT INTO mydb.users(facebook_id,user_name,gcm_id,email) VALUES(?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(statement, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getFacebookId());
            preparedStatement.setString(2, user.getUserName());
            preparedStatement.setString(3, user.getGcmId());
            preparedStatement.setString(4, user.getEmail());
            int rowsInserted = preparedStatement.executeUpdate();
            if( rowsInserted == 0 ) {
                throw new SQLException("No Rows Created");
            } else {
                try ( ResultSet generatedKeys = preparedStatement.getGeneratedKeys() ) {
                    if( generatedKeys.next() ) {
                        user.setUserId(generatedKeys.getInt(1));
                        return user.getUserId();
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
            }
        }
    }

    boolean isFacebookIdExist(String facebookId) throws SQLException {
        String statement = "SELECT count(*) from mydb.users where facebook_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, facebookId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if( resultSet.next() ) {
            int count = resultSet.getInt(1);
            if( count > 0 )
                return true;
            else
                return false;
        } else {
            return false;
        }
    }

    int getUserId(String facebookId) throws SQLException {
        String statement = "SELECT * from mydb.users where facebook_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setString(1, facebookId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if( resultSet.next() ) {
            return resultSet.getInt(1);
        } else {
            return -1;
        }
    }

    Quizify.User getUserById(int userId) throws SQLException {
        String statement = "SELECT * from mydb.users where user_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(statement);
        preparedStatement.setInt(1, userId);
        ResultSet resultSet = preparedStatement.executeQuery();
//        if( resultSet.next() ) {
//            resultSet.ge
//        }
        return new Quizify.User("check","check","check","check");
    }
}
