package com.gerardvh.models.dao;

public class SQL {
    static public final String CREATE_USER_TABLE = 
        "CREATE TABLE IF NOT EXISTS user (" +
            "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(30) NOT NULL, " +
            "password VARCHAR(30) NOT NULL, " +
            "date DATE)";
    static public final String CREATE_STOCK_TABLE = 
        "CREATE TABLE IF NOT EXISTS stock (" +
            "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "symbol VARCHAR(15) NOT NULL, " +
            "name VARCHAR(50) NOT NULL, " +
            "exchange VARCHAR(8))";
    static public final String CREATE_OWNED_STOCK_TABLE = 
        "CREATE TABLE IF NOT EXISTS owned_stocks(" +
            "id INT PRIMARY KEY AUTO_INCREMENT, " +
            "user_id INT NOT NULL, " +
            "stock_id INT NOT NULL, " +
            "num_shares INT, " +
            "FOREIGN KEY (user_id) REFERENCES user(id), " +
            "FOREIGN KEY (stock_id) REFERENCES stock(id))";
    static public final String CREATE_PRICE_TABLE = 
        "CREATE TABLE IF NOT EXISTS stock_price (" +
            "id INTEGER PRIMARY KEY AUTO_INCREMENT, " +
            "stock_id INT NOT NULL, " +
            "date DATE NOT NULL, " +
            "close_price DOUBLE NOT NULL, " +
            "adj_close DOUBLE," +
            "FOREIGN KEY (stock_id) REFERENCES stock(id), " +
            "FOREIGN KEY (user_id) REFERENCES user(id))";

    static public final String INSERT_USER = 
        "INSERT INTO user " +
            "(name, password) " +
            "VALUES(?,?)";

    static public final String CHECK_USER_EXISTS = 
        "SELECT EXISTS(" +
            "SELECT 1 FROM user " +
            "WHERE name=? AND password=?)";

    static public final String GET_USER_ID =
        "SELECT id FROM user " +
            "WHERE name=? AND password=?"; 

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