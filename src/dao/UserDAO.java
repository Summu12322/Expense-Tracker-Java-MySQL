package dao;

import util.DBConnection;

import java.sql.*;

public class UserDAO {

    public boolean userExists(
            String username) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT * FROM users WHERE username=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(
                    1,
                    username);

            ResultSet rs =
                    ps.executeQuery();

            return rs.next();

        } catch(Exception e) {

            e.printStackTrace();
        }

        return false;
    }

    public void registerUser(
            String username,
            String password) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "INSERT INTO users(username,password) VALUES(?,?)";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(
                    1,
                    username);

            ps.setString(
                    2,
                    password);

            ps.executeUpdate();

        } catch(Exception e) {

            e.printStackTrace();
        }
    }

    public int loginUser(
            String username,
            String password) {

        try {

            Connection con =
                    DBConnection.getConnection();

            String query =
                    "SELECT * FROM users " +
                            "WHERE username=? AND password=?";

            PreparedStatement ps =
                    con.prepareStatement(query);

            ps.setString(
                    1,
                    username);

            ps.setString(
                    2,
                    password);

            ResultSet rs =
                    ps.executeQuery();

            if(rs.next()) {

                return rs.getInt(
                        "id");
            }

        } catch(Exception e) {

            e.printStackTrace();
        }

        return -1;
    }

}