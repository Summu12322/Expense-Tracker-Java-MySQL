package service;

import dao.UserDAO;
import util.PasswordUtil;

public class UserService {

    UserDAO userDAO =
            new UserDAO();

    public boolean register(
            String username,
            String password) {

        if(userDAO.userExists(
                username)) {

            return false;
        }

        String hashedPassword =
                PasswordUtil.hashPassword(
                        password);

        userDAO.registerUser(
                username,
                hashedPassword);

        return true;
    }

    public int login(
            String username,
            String password) {

        String hashedPassword =
                PasswordUtil.hashPassword(
                        password);

        return userDAO.loginUser(
                username,
                hashedPassword);
    }

}