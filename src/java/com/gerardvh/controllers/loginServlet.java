/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gerardvh.controllers;

import com.gerardvh.models.dao.UserDAO;
import com.gerardvh.jdbc.ConnectionPool;
import com.gerardvh.models.beans.User;
import com.gerardvh.models.dao.StockDAO;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author gerardvh
 */
public class loginServlet extends HttpServlet {

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
        RequestDispatcher dispatcher = 
                getServletContext().getRequestDispatcher("/login.jsp");
        dispatcher.forward(request, response);
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
        ConnectionPool connectionPool = (ConnectionPool) getServletContext().getAttribute("connectionPool");
        try (Connection conn = connectionPool.getConnection();){
            String pageToForwardTo = "/JH7_gvanhalsema/"; // Optimistic forwarding
            // Look for user logging in and attempt to log in user or create user
            String action = request.getParameter("action");
            if (action != null) {
                String name = request.getParameter("username");
                String pass = request.getParameter("password");
                boolean success = true;
                String err = null;
                User user = null;
                
                switch (action) {
                    case "Login":
                        // Attempt to login
                        user = UserDAO.loginUserFromDatabase(name, pass, connectionPool);
                        // request.setAttribute("error_message", err);
                        request.setAttribute("user", user);
                        if (user == null) {
                            request.setAttribute("login_error", "Username or Password is incorrect.");
                            pageToForwardTo = "/login.jsp";
                            break;
                        }
                        if (user.hasState()) {
                            // set user and redirect
                            err = "User has a state" + user.getUserState().toString(2);
                            pageToForwardTo = "/testing_page.jsp";
//                            JSONArray stockArray = new JSONArray(user.getUserState().getJSONArray("stocks"));
                        } else {
                            // Set error message
                            request.setAttribute("stockSymbols", StockDAO.getAllStocks(connectionPool));
                            pageToForwardTo = "/testing_page.jsp";
//                            err = "User has no state";
                        }
                        break;
                    case "Create Account":
                        // Create a new user
                        success = UserDAO.createUserInDatabase(name, pass, connectionPool);
                        if (success) {
                            // Set user and redirect
                            
                        } else {
                            // Set error message
                            err = ""; 
                        }
                        break;
                    default:
                        System.out.println("Don't know what to do with: " + action);
                }
                
            } 
        RequestDispatcher dispatcher = 
                getServletContext().getRequestDispatcher(pageToForwardTo);
        dispatcher.forward(request, response);
        } catch (SQLException e) {
            System.err.println(e);
        }
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
