package com.gerardvh.models.dao;

import java.sql.Connection;

public class UserDAO {
    public static boolean createUserInDatabase(String username, String password, Connection conn) {
        boolean success = false;
        // create a user

        return success;
    }
    public static boolean loginUserFromDatabase(String username, String password, Connection conn) {
        // Verify user exists and password correct, if both yes, 
        // forward to index with user in request
        // if user does not exist, dispatch back to login page and ask to create account
        boolean success = false;


        return success;
    }
}