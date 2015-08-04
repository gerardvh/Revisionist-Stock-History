package com.gerardvh.models.dao;

import java.util.ArrayList;

public class SQL {
    static public final String CREATE_USER_TABLE = 
        "CREATE TABLE IF NOT EXISTS user (" +
            "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(30) NOT NULL, " +
            "password VARCHAR(30) NOT NULL, " +
            "user_state TEXT, " +
            "date DATE)";
    static public final String CREATE_STOCK_TABLE = 
        "CREATE TABLE IF NOT EXISTS stock (" +
            "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "symbol VARCHAR(15) NOT NULL, " +
            "name VARCHAR(100) NOT NULL, " +
            "exchange VARCHAR(8))";
    static public final String CREATE_OWNED_STOCK_TABLE = 
        "CREATE TABLE IF NOT EXISTS owned_stocks(" +
            "id INT PRIMARY KEY AUTO_INCREMENT, " +
            "user_id INT NOT NULL, " +
            "stock_id INT NOT NULL, " +
            "num_shares INT)";
    static public final String CREATE_PRICE_TABLE = 
        "CREATE TABLE IF NOT EXISTS stock_price (" +
            "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "user_id INT NOT NULL, " +
            "stock_id INT NOT NULL, " +
            "date DATE NOT NULL, " +
            "close_price DOUBLE NOT NULL, " +
            "adj_close DOUBLE)";

    static public ArrayList<String> getTableSQL() {
        ArrayList<String> sqlStrings = new ArrayList<>();
        sqlStrings.add(CREATE_USER_TABLE);
        sqlStrings.add(CREATE_STOCK_TABLE);
        sqlStrings.add(CREATE_OWNED_STOCK_TABLE);
        sqlStrings.add(CREATE_PRICE_TABLE);
        return sqlStrings;

    }

    static public final String CREATE_NEW_USER = 
        "INSERT INTO user " +
            "(name, password) " +
            "VALUES(?,?)";

    static public final String CHECK_USER_LOGIN = 
        "SELECT EXISTS(" +
            "SELECT 1 FROM user " +
            "WHERE name=? AND password=?)";
    static public final String CHECK_USER_EXISTS = 
        "SELECT EXISTS(" +
            "SELECT 1 FROM user " +
            "WHERE name=?)";

    static public final String GET_USER =
        "SELECT id,name,user_state FROM user " +
            "WHERE name=? AND password=?";
    static public final String SAVE_USER_STATE = 
        "UPDATE user " +
            "SET user_state=? " +
            "WHERE id=?";
    static public final String CLEAR_USER_STATE = 
        "UPDATE user " +
            "SET user_state=NULL " +
            "WHERE id=?";

    static public final String GET_DATE_FOR_USER_ID = 
        "SELECT date FROM user " +
            "WHERE id=?";
    static public final String GET_STOCK_LIST_BY_USER_ID = 
        "SELECT stock.id, symbol,stock.name, num_shares, user_id " +
            "FROM stock INNER JOIN owned_stocks " +
            "ON stock.id=owned_stocks.stock_id WHERE user_id=?";
    
    static public final String POPULATE_PRICE =
        "INSERT INTO stock_price " +
            "(stock_id, date, close, adj_close) " +
            "VALUES(?,?,?,?)";

    static public final String GET_PRICE_BY_DATE_AND_ID = 
        "SELECT close " +
            "FROM stock_price " +
            "WHERE date=? AND stock_id=?";
    
    static public final String POPULATE_STOCK_SYMBOLS = 
        "INSERT INTO stock" +
            "(symbol,name,exchange) " +
            "VALUES (?, ?, ?)";
    

}