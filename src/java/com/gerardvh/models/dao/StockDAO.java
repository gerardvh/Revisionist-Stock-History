/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gerardvh.models.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import com.gerardvh.models.beans.Stock;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Statement;
import org.json.*;

/**
 *
 * @author gerardvh
 */
public class StockDAO {
   
    public static void createStockDatabase(Connection conn) throws SQLException {
        PreparedStatement prep = conn.prepareStatement(SQL.CREATE_STOCK_TABLE);
        prep.executeUpdate();
    } 
    public static void populateStockDatabase(Connection conn, ArrayList<Stock> stockList) throws SQLException {
        PreparedStatement prep = conn.prepareStatement(SQL.POPULATE_STOCK_SYMBOLS);
        for (Stock stock : stockList) {
            prep.setString(1, stock.getSymbol());
            prep.setString(2, stock.getName());
            prep.executeUpdate();
        }
        
    } 
    public static void populateStockDatabaseFromSQL(Connection conn, File stockSQL) throws SQLException {
        Statement stmt = conn.createStatement();
        String line;
        try (BufferedReader buff = new BufferedReader(new FileReader(stockSQL));) {
            
            while ((line = buff.readLine()) != null) {
                stmt.execute(line);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        
    }
    public static void populateStockDatabaseFromCDL(Connection conn, File stockCDL) throws SQLException {
        String line;
        JSONArray jsonArray = stockJSONFromCDL(stockCDL);
        for (int i = 0; i < jsonArray.length(); i++) {
            addJSONObjectToDatabase(jsonArray.getJSONObject(i), conn);
        }
    }
    public static void addJSONObjectToDatabase(JSONObject jsonObj, Connection conn) throws SQLException {
        PreparedStatement prep = conn.prepareStatement(SQL.POPULATE_STOCK_SYMBOLS);
        prep.setString(1, jsonObj.getString("symbol"));
        prep.setString(2, jsonObj.getString("name"));
        prep.setString(3, jsonObj.getString("exchange"));
        prep.executeUpdate();
    }
    public static JSONArray stockJSONFromCDL(File stockCDL) {
        try (BufferedReader buff = new BufferedReader(new FileReader(stockCDL));) {
            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = buff.readLine()) != null) {
                jsonString.append(line);
                jsonString.append("\n");
            }
            // return our new JSONArray if successful
            return CDL.toJSONArray(jsonString.toString());
        } catch (IOException | JSONException e) {
            System.out.println(e);
        }
        // return null if not successful
        return null;
    } 
    
}
