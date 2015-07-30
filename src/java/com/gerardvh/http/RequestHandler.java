package com.gerardvh.http;

import com.github.kevinsawicki.http.HttpRequest;
import org.json.JSONException;
import org.json.JSONObject;

public class RequestHandler {
    private static String authString;
    
    public RequestHandler() {
        // Empty implementation
    }
    
    private String getRequestURL(String symbol) {
        return String.format("https://www.quandl.com/api/v1/datasets/WIKI/%s.json", symbol);
    }
    public static void setAuthString(String authString) {
        RequestHandler.authString = authString;
    }

    private String getHistory(String symbol) {
        return HttpRequest.get(getRequestURL(symbol), true,
            "auth_token", authString).body();
    }

    public JSONObject getStockHistory(String symbol) throws JSONException {
        return new JSONObject(getHistory(symbol));
    }
}