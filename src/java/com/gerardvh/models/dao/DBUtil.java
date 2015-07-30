package com.gerardvh.models.dao;

import java.sql.Connection;
import org.json.*;
import com.gerardvh.http.RequestHandler;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBUtil implements Runnable {
    private RequestHandler handler;
    private Connection conn;
    private String symbol;

    public DBUtil() {
        
    }
    public DBUtil(RequestHandler handler, Connection conn, String symbol) {
        this.handler = handler;
        this.conn = conn;
        this.symbol = symbol;
    }
    @Override
    public void run() {
        JSONObject stock_quotes = handler.getStockHistory(symbol);
        try {
            StockDAO.addQuandlResponseToDB(stock_quotes, conn);
        } catch (SQLException ex) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}