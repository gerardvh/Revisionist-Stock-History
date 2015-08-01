package com.gerardvh.models.dao;

import com.gerardvh.models.beans.Stock;
import com.gerardvh.models.beans.User;
import java.util.ArrayList;
import java.sql.Date;


public class DummyData {
    private User user = null;
    private ArrayList<Stock> dummyStockList = null;

    public DummyData() {
        setupStockList();
        setupUser();
    }

    private void setupStockList() {
        dummyStockList = new ArrayList<>();
        dummyStockList.add(new Stock("AAPL", "Apple Inc.", getRandomInt(), getRandomDouble(), 1));
        dummyStockList.add(new Stock("GOOG", "Google Inc.", getRandomInt(), getRandomDouble(), 2));
        dummyStockList.add(new Stock("YHOO", "Yahoo! Inc.", getRandomInt(), getRandomDouble(), 3));
        dummyStockList.add(new Stock("CDW", "CDW Corporation", getRandomInt(), getRandomDouble(), 4));
        dummyStockList.add(new Stock("AKAM", "Akamai Technologies, Inc.", getRandomInt(), getRandomDouble(), 5));
    }
    private int getRandomInt() {
        int rand = (int)(Math.random() * 100);
        return rand;
    }
    private double getRandomDouble() {
        return Math.random() * 100;
    }
    private void setupUser() {
        user = new User("Testing", dummyStockList, new Date(new java.util.Date().getTime()), -1);
    }
    public ArrayList<Stock> getDummyStockList() {
        return dummyStockList;
    }
    public User getUser() {
        return user;
    }

}