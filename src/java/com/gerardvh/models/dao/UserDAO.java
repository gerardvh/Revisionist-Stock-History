package com.gerardvh.models.dao;

import com.gerardvh.jdbc.ConnectionPool;
import com.gerardvh.models.beans.Stock;
import com.gerardvh.models.beans.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

public class UserDAO {
    public static boolean createUserInDatabase(String username, String password, Connection conn) {
        boolean success = false;
        // create a user

        return success;
    }
//    public static User loginUserFromDatabase(String username, String password, Connection conn) throws SQLException {
//        // Verify user exists and password correct, if both yes, 
//        // forward to index with user in request
//        // if user does not exist, dispatch back to login page and ask to create account
//        try (PreparedStatement prep = conn.prepareStatement(SQL.CHECK_USER_EXISTS);) {
//            prep.setString(1, username);
//            prep.setString(2, password);
//            ResultSet rs = prep.executeQuery();
//            rs.next();
//            if (rs.getInt(1) == 0) { // User does not currently exist or password wrong
//                return null;
//            } else { //User exists and password correct
//                return getUserFromDatabase(username, password, conn);
//            }
//
//        }
//    }
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
}