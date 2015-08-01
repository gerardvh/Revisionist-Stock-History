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
import com.gerardvh.models.beans.User;
import com.gerardvh.models.dao.StockDAO;
import com.gerardvh.models.dao.UserDAO;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpSession;
import org.json.JSONObject;

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
            String pageToForwardTo = "/JH7_gvanhalsema/";
            HttpSession session = request.getSession(true);

            if (session.getAttribute("user") == null || request.getAttribute("login_error") != null) {
                pageToForwardTo = "/login.jsp";
            }
            
            // neet to get the user and add it to request
            // request.setAttribute("user", dum.getUser());

            // Testing HttpRequester
//            DBUtil dbUtil = new DBUtil(new RequestHandler(), connection, "YHOO");
//            Thread t = new Thread(dbUtil);
//            t.start();
            
//            ArrayList<Stock> stockList = 
//                    (ArrayList<Stock>)request.getAttribute("watched_stock_list");
//            if (stockList == null) {
////                Nothing in our request yet. Send to Select page.
//                request.setAttribute("stockSymbols", StockDAO.getAllStocks(connectionPool));
//                RequestDispatcher dispatcher = 
//                    getServletContext().getRequestDispatcher("/chooseStocks.jsp");
//                dispatcher.forward(request, response);
//            } 
            
            // request.setAttribute("stock_quotes", stock_quotes);
            String action = request.getParameter("action");
            if (action != null) {
                User user;
                String err = null;
                boolean success;
                String name = request.getParameter("username");
                String pass = request.getParameter("password");
                switch (action) {
                    case "Choose Stocks":
                        // Set up user state with the chosen stocks
                        // Get the user so we can work with it.
                        user = (User)session.getAttribute("user");

                        ArrayList<String> stockSymbols = new ArrayList<>();
                        for (int i = 1; i <= 5; i++) {
                            String param = String.format("stock%d", i);
                            String symbol = (String)request.getParameter(param);
                            stockSymbols.add(symbol);
                            System.out.println(symbol);
                        }
                        
                        // // Add stocks to price database on a different thread
                        // for (String stockSymbol : stockSymbols) {
                        //     if (stockSymbol != null) {
                        //         Thread t = new Thread(new DBUtil(new RequestHandler(), connectionPool, stockSymbol));
                        //         t.start();
                        //     }
                        // }
                        if (user != null) {
                            user.setStockListFromSymbols(stockSymbols);
                            user.updateStockListFromWeb();
                            UserDAO.saveUserState(user, connectionPool);
                            pageToForwardTo = "/revisionist-stock-history.jsp";
                        } else {
                            pageToForwardTo = "/login.jsp";
                        }
                        
                        break;
                    case "Login":
                        // Attempt to login
                        user = UserDAO.loginUserFromDatabase(name, pass, connectionPool);
                        // request.setAttribute("error_message", err);
                        session.setAttribute("user", user);
                        if (user == null) {
                            request.setAttribute("login_error", "Username or Password is incorrect.");
                            pageToForwardTo = "/login.jsp";
                            break;
                        } else if (user.hasState()) {
                            // set user and redirect
                            user.updateFromUserState();
                            // err = "User has a state" + user.getUserState().toString(2);
                            request.setAttribute("error_message", err);
                            pageToForwardTo = "/revisionist-stock-history.jsp";
//                            JSONArray stockArray = new JSONArray(user.getUserState().getJSONArray("stocks"));
                        } else {
                            // Set error message
                            request.setAttribute("stockSymbols", StockDAO.getAllStocks(connectionPool));
                            pageToForwardTo = "/chooseStocks.jsp";
//                            err = "User has no state";
                        }
                        break;
                    case "Create Account":
                        // Create a new user
                        boolean userAlreadyExists = UserDAO.userExists(name, connectionPool);
                        if (!userAlreadyExists) {
                            // Set user and redirect
                            UserDAO.createUserInDatabase(name, pass, connectionPool);
                            session.setAttribute("user", UserDAO.loginUserFromDatabase(name, pass, connectionPool));
                            request.setAttribute("stockSymbols", StockDAO.getAllStocks(connectionPool));
                            pageToForwardTo = "/chooseStocks.jsp";
                        } else {
                            // Set error message
                            // TODO: fix this
                            request.setAttribute("login_error", "Unable to create new user.");
                            pageToForwardTo = "/login.jsp";
                            break;
                        }
                        break;
                    case "Refresh State":
                        User refreshUser = (User) session.getAttribute("user");
                        if (refreshUser != null) {
                            UserDAO.clearUserState(refreshUser, connectionPool);
                        }
                        request.setAttribute("stockSymbols", StockDAO.getAllStocks(connectionPool));
                        pageToForwardTo = "/chooseStocks.jsp";
                        break;
                    default:
                        System.out.println("Don't know what to do with: " + action);
                        break; 
                } 
            }
                    // Finished updating, send to jsp
            RequestDispatcher dispatcher = 
                getServletContext().getRequestDispatcher(pageToForwardTo);
            dispatcher.forward(request, response);
            
        } catch (SQLException e) {
            System.err.println("Exception in 'processRequest()': " + e);
        } 
        finally {
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
        // System.out.println("Here I am at post!");
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



