package com.gerardvh.models.dao;

import java.sql.Connection;
import org.json.*;
import com.gerardvh.http.RequestHandler;
import com.gerardvh.jdbc.ConnectionPool;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtil implements Runnable {
    private RequestHandler handler;
    private ConnectionPool pool;
    private String symbol;

    public DBUtil() {
        
    }
    public DBUtil(RequestHandler handler, ConnectionPool pool, String symbol) {
        this.handler = handler;
        this.pool = pool;
        this.symbol = symbol;
    }

    public static void setupSQLTables(ConnectionPool pool) throws SQLException {
        Statement stmt = null;
        try (Connection conn = pool.getConnection();) {
                stmt = conn.createStatement();
                stmt.executeUpdate(SQL.CREATE_STOCK_TABLE);
                stmt.executeUpdate(SQL.CREATE_OWNED_STOCK_TABLE);
                stmt.executeUpdate(SQL.CREATE_USER_TABLE);
                stmt.executeUpdate(SQL.CREATE_PRICE_TABLE);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    @Override
    public void run() {
        JSONObject stock_quotes = handler.getStockHistory(symbol);
        try {
            StockDAO.addQuandlResponseToDB(stock_quotes, pool);
            System.out.println("Running DBUtil");
        } catch (SQLException ex) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}