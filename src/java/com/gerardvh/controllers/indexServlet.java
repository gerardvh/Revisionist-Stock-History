/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gerardvh.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gerardvh.jdbc.ConnectionPool;
import com.gerardvh.models.dao.DummyData;
import com.gerardvh.http.RequestHandler;
import com.gerardvh.models.beans.Stock;
import com.gerardvh.models.dao.DBUtil;
import com.gerardvh.models.dao.StockDAO;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;

/**
 *
 * @author gerardvh
 */
public class indexServlet extends HttpServlet {
    DummyData dum = new DummyData();
    
    public void init() {
        // ConnectionPool connectionPool = (ConnectionPool) getServletContext().getAttribute("connectionPool");
        // Connection connection = null;
        // try {
        //     connection = connectionPool.getConnection();
        //     String base_stock_data = getServletContext().getRealPath("/WEB-INF/base_stock_data.txt");
        //     StockDAO.populateStockDatabaseFromCDL(connection, new File(base_stock_data));
        //     // StockController.populateStockDatabase(connection, dum.getDummyStockList());
        // } catch (SQLException e) {
        //     System.err.println("Exception in 'processRequest()': " + e);
        // } finally {
        //     if (connection != null) {
        //         connectionPool.free(connection);
        //     }
        // }
        RequestHandler.setAuthString(getServletContext().getInitParameter("auth_token"));
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ConnectionPool connectionPool = (ConnectionPool) getServletContext().getAttribute("connectionPool");
        Connection connection = null;
        
        try {
            connection = connectionPool.getConnection();
            // neet to get the user and add it to request
            // request.setAttribute("user", dum.getUser());

            // Testing HttpRequester
//            DBUtil dbUtil = new DBUtil(new RequestHandler(), connection, "YHOO");
//            Thread t = new Thread(dbUtil);
//            t.start();
            
            ArrayList<Stock> stockList = 
                    (ArrayList<Stock>)request.getAttribute("watched_stock_list");
            if (stockList == null) {
//                Nothing in our request yet. Send to Select page.
                request.setAttribute("stockSymbols", StockDAO.getAllStocks(connectionPool));
                RequestDispatcher dispatcher = 
                    getServletContext().getRequestDispatcher("/chooseStocks.jsp");
                dispatcher.forward(request, response);
            } 
            
            // request.setAttribute("stock_quotes", stock_quotes);

            // Finished updating, send to jsp
//            RequestDispatcher dispatcher = 
//                getServletContext().getRequestDispatcher("/testing_page.jsp");
//            dispatcher.forward(request, response);
        } catch (SQLException e) {
            System.err.println("Exception in 'processRequest()': " + e);
        } finally {
            if (connection != null) {
                connectionPool.free(connection);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Here I am at post!");
        ConnectionPool connectionPool = 
            (ConnectionPool) getServletContext().getAttribute("connectionPool");
        ArrayList<String> stockSymbols = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            String param = String.format("stock%d", i);
            stockSymbols.add(param);
        }
        for (String stockSymbol : stockSymbols) {
            Thread t = new Thread(new DBUtil(new RequestHandler(), connectionPool, stockSymbol));
            t.start();
            System.out.println("Running thread.");
        }

        processRequest(request, response);
    }
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}



