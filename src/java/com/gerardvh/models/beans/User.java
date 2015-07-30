package com.gerardvh.models.beans;

import com.gerardvh.models.beans.Stock;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;

public class User {

    private String name = "";
    private String password = "";
    private ArrayList<Stock> stockList = new ArrayList<>();
    private Date date = new Date();
    private int id = -1;
    private double cash = 0;
    private double netWorth = 0;

    public User() {
        // Empty implementation
    }

    public User(String name, String password, ArrayList<Stock> stockList, Date date, int id) {
        this.name = name;
        this.password = password;
        this.stockList = stockList;
        this.date = date;
        this.id = id;
    }
    public User(String name, ArrayList<Stock> stockList, Date date, int id) {
        // Convenience init without password
        this(name, "", stockList, date, id);
    }

    public double getNetWorth() {
        netWorth = 0;
        for (Stock stock : stockList) {
            netWorth += stock.getValue();
        }
        netWorth += this.cash;
        return netWorth;
    }

    public String getNetWorthFormatted() {
        return NumberFormat.getCurrencyInstance().format(getNetWorth());
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the stockList
     */
    public ArrayList<Stock> getStockList() {
        return stockList;
    }

    /**
     * @param stockList the stockList to set
     */
    public void setStockList(ArrayList<Stock> stockList) {
        this.stockList = stockList;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    public String getDateString() {
        return date.toString();
    }

}
