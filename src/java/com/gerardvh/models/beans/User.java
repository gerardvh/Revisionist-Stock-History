package com.gerardvh.models.beans;

import com.gerardvh.http.RequestHandler;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.sql.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User {

    private String name = "";
    private String password = "";
    private ArrayList<Stock> stockList = new ArrayList<>();
    private Date date = new Date(new java.util.Date().getTime());
    private int id = -1;
    private double cash = 0;
    private double netWorth = 0;
    private JSONObject userState = null;

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
    
    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public double getNetWorth() {
        netWorth = 0;
        for (Stock stock : stockList) {
            netWorth += stock.getValue();
        }
        netWorth += this.getCash();
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
    private String getPassword() {
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

    /**
     * @return the cash
     */
    public double getCash() {
        return cash;
    }

    /**
     * @param cash the cash to set
     */
    public void setCash(double cash) {
        this.cash = cash;
    }

    /**
     * @return the userState
     */
    public JSONObject getUserState() {
        return userState;
    }

    /**
     * @param userState the userState to set
     */
    public void setUserState(JSONObject userState) {
        this.userState = userState;
    }

    public boolean hasState() {
        return this.userState != null;
    }

    public void setStockListFromSymbols(ArrayList<String> stockSymbols) {
        ArrayList<Stock> stocks = new ArrayList<>();
        for (String stockSymbol : stockSymbols) {
            Stock stock = new Stock();
            stock.setSymbol(stockSymbol);
            stocks.add(stock);
        }
        this.stockList = stocks;
    }

    public void updateFromUserState() {
        this.name = userState.getString("name");
//        this.date = new Date(userState.getString("date"));
        this.id = userState.getInt("id");
        ArrayList<Stock> tempList = new ArrayList<>();
        JSONArray stateStockList = userState.getJSONArray("stockList");
        for (Object stock : stateStockList) {
            tempList.add(new Stock((JSONObject)stock));
        }
        this.stockList = tempList;

    }

    public void updateStockListFromWeb() {
        for (Stock stock : stockList) {
            JSONObject quandlObject = RequestHandler.getOneQuote(stock.getSymbol());
            System.out.println(quandlObject);
            try {
                stock.setPrice(quandlObject.getJSONObject("dataset")
                                            .getJSONArray("data")
                                            .getJSONArray(0) // first item
                                            .getDouble(4)); // close price)
                stock.setName(quandlObject.getJSONObject("dataset")
                                            .getString("name"));
            } catch (JSONException e) {
                System.out.println("EXCEPTION: " + quandlObject);
            }
        }
    }

}
