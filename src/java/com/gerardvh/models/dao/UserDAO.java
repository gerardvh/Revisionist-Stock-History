package com.gerardvh.models.dao;

import com.gerardvh.http.RequestHandler;
import com.gerardvh.jdbc.ConnectionPool;
import com.gerardvh.models.beans.Stock;
import com.gerardvh.models.beans.User;
import com.github.kevinsawicki.http.HttpRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import javax.servlet.http.HttpServletRequest;
import org.json.JSONObject;

public class UserDAO {
    public static boolean createUserInDatabase(String username, String password, ConnectionPool pool) throws SQLException {
        try (Connection conn = pool.getConnection();
            PreparedStatement prep = conn.prepareStatement(SQL.CREATE_NEW_USER);) {
            prep.setString(1, username);
            prep.setString(2, password);
            return prep.executeUpdate() == 1 ? true : false;
        }
    }
    public static User loginUserFromDatabase(String username, String password, ConnectionPool pool) throws SQLException {
        // Verify user exists and password correct, if both yes, 
        // forward to index with user in request
        // if user does not exist, dispatch back to login page and ask to create account
        try (Connection conn = pool.getConnection();
            PreparedStatement prep = conn.prepareStatement(SQL.GET_USER);) {
            prep.setString(1, username);
            prep.setString(2, password);
            ResultSet rs = prep.executeQuery();
            if (rs.next()) {// User exists and password correct
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String state = rs.getString("user_state");
                User user = new User(id, name);
                if (state != null) {
                    JSONObject userState = new JSONObject(state);
                    user.setUserState(userState);
                } 
                return user; 
            } else {  // User does not currently exist or password wrong
                return null;
            }

        }
    }
    public static boolean userExists(String username, ConnectionPool pool) throws SQLException {
        try (Connection conn = pool.getConnection();
            PreparedStatement prep = conn.prepareStatement(SQL.CHECK_USER_EXISTS);) {
            prep.setString(1, username);
            ResultSet rs = prep.executeQuery();
            return rs.getInt(1) == 1 ? true : false;
        }
    }
    public static boolean saveUserState(User user, ConnectionPool pool) throws SQLException {
        try (Connection conn = pool.getConnection();
            PreparedStatement prep = conn.prepareStatement(SQL.SAVE_USER_STATE)) {
            String state = new JSONObject(user).toString();
            prep.setString(1, state);
            prep.setInt(2, user.getId());
            return prep.executeUpdate() == 1 ? true : false;
        }
    }
    public static boolean clearUserState(User user, ConnectionPool pool) throws SQLException {
        try (Connection conn = pool.getConnection();
            PreparedStatement prep = conn.prepareStatement(SQL.CLEAR_USER_STATE);) {
            prep.setInt(1, user.getId());
            return prep.executeUpdate() == 1 ? true : false;
        }
    }
//    private static User getUserFromDatabase(String username, String password, Connection conn) throws SQLException {
//        try (PreparedStatement prep = conn.prepareStatement(SQL.GET_USER);) {
//            prep.setString(1, username);
//            prep.setString(2, password);
//            ResultSet rs = prep.executeQuery();
//            if (rs.next()) {
//                int userID = rs.getInt("id");
//                Date userDate = rs.getDate("date");
//                // need a routine to get the combined info for the user from the DB
//            }
//        }
//    }
    private static ArrayList<Stock> getUserStockList(int id, ConnectionPool pool) throws SQLException {
        ArrayList<Stock> stockList = new ArrayList<>();
        // SQL statement for stock_id's and num_shares for the user_id
        // For each stock_id get the price for the user's date
        Date user_date = getDateByUserID(id, pool); 
        try (Connection conn = pool.getConnection();
            PreparedStatement prep = conn.prepareStatement(SQL.GET_STOCK_LIST_BY_USER_ID);) {
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()) {
                Stock stock = new Stock();
                int stock_id = rs.getInt("stock_id");
                stock.setId(stock_id);
                stock.setSymbol(rs.getString("symbol"));
                stock.setName(rs.getString("name"));
                stock.setNumShares(rs.getInt("num_shares"));
                stock.setPrice(StockDAO.getPriceByDateAndID(user_date, stock_id, pool));
                stockList.add(stock);
            }
        }
        return stockList;
    }
    private static Date getDateByUserID(int id, ConnectionPool pool) throws SQLException {
        try (Connection conn = pool.getConnection();
            PreparedStatement prep = conn.prepareStatement(SQL.GET_DATE_FOR_USER_ID);) {
            prep.setInt(1, id);
            ResultSet rs = prep.executeQuery();
            rs.next();
            return rs.getDate("date");
        }
    }

    public static User getUserFromRequest(HttpServletRequest request) {
        User user = (User)request.getAttribute("user");
        if (user == null) {
            
        }
        return user;
    }

}