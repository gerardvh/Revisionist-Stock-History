package com.gerardvh.models.beans;

import java.text.NumberFormat;

public class Stock {
    private String symbol = "";
    private String name = "";
    private int numShares = 0;
    private double price = 0.0;
    private double value = 0.0;
    private int id = -1;

    public Stock() {
        // Empty implementation
    }

    public Stock(String symbol, String name, int numShares, double price, int id) {
        this.symbol = symbol;
        this.name = name;
        this.numShares = numShares;
        this.price = price;
        this.id = id;
    }
    public Stock(String symbol, String name) {
        this.symbol = symbol;
        this.name = name;
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
     * @return the symbol
     */
    public String getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(String symbol) {
        this.symbol = symbol;
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
     * @return the numShares
     */
    public int getNumShares() {
        return numShares;
    }

    /**
     * @param numShares the numShares to set
     */
    public void setNumShares(int numShares) {
        this.numShares = numShares;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    public String getPriceFormatted() {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(2);
        return nf.format(price);
    }

    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the value
     */
    public double getValue() {
        return price * numShares;
    }

    public String getValueFormatted() {
        return NumberFormat.getCurrencyInstance().format(getValue());
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }
}