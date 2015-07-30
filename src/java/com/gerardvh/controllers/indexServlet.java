/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gerardvh.controllers;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.gerardvh.jdbc.ConnectionPool;
import com.gerardvh.models.dao.DummyData;
import com.gerardvh.models.dao.StockDAO;
import java.io.File;

/**
 *
 * @author gerardvh
 */
public class indexServlet extends HttpServlet {
    DummyData dum = new DummyData();
    
    public void init() {
        ConnectionPool connectionPool = (ConnectionPool) getServletContext().getAttribute("connectionPool");
        Connection connection = null;
        try {
            connection = connectionPool.getConnection();
            String base_stock_data = getServletContext().getRealPath("/WEB-INF/base_stock_data.txt");
            StockDAO.populateStockDatabaseFromCDL(connection, new File(base_stock_data));
            // StockController.populateStockDatabase(connection, dum.getDummyStockList());
        } catch (SQLException e) {
            System.err.println("Exception in 'processRequest()': " + e);
        } finally {
            if (connection != null) {
                connectionPool.free(connection);
            }
        }
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
            request.setAttribute("user", dum.getUser());

            // Finished updating, send to jsp
            RequestDispatcher dispatcher = 
                getServletContext().getRequestDispatcher("/revisionist-stock-history.jsp");
            dispatcher.forward(request, response);
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



