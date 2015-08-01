package com.gerardvh.http;

import com.github.kevinsawicki.http.HttpRequest;
import org.json.CDL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestHandler {
    private static String authString;
    private static String csvURL = "https://www.quandl.com/api/v3/datasets/WIKI/%s.csv";
    private static String jsonURL = "https://www.quandl.com/api/v3/datasets/WIKI/%s.json";
    
    public RequestHandler() {
        // Empty implementation
    }
    
    private static String getRequestURL(String url, String symbol) {
        return String.format(url, symbol);
    }
    public static void setAuthString(String authString) {
        RequestHandler.authString = authString;
    }
    public static JSONObject getOneQuote(String symbol) {
        return new JSONObject(HttpRequest.get(
            getRequestURL(jsonURL, symbol),
            true, // Encode params
            "auth_token", authString,
            "limit", 1) // params
            .body()); // get string value
    }

    private String getHistory(String symbol) {
        return HttpRequest.get(getRequestURL(jsonURL, symbol), true, "auth_token", authString).body();
    }

    public JSONObject getStockHistory(String symbol) throws JSONException {
        return new JSONObject(getHistory(symbol));
    }
}