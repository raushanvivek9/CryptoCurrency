package com.example.crypto;

import java.util.ArrayList;

public class Crypto_Model {
    private String name;
    private String symbol;
    private double price;
    private double marketCap;
    private String iconUrl;
    private double percent_change_1h;
    private double percent_change_24h;
    private double percent_change_7d;
    private double percent_change_30d;
    private double percent_change_60d;
    private double percent_change_90d;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Crypto_Model() {
    }

    public Crypto_Model(String name, String symbol, double price, double marketCap) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.marketCap = marketCap;
    }

    public Crypto_Model(String name, String symbol, double price, double marketCap, String iconUrl) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.marketCap = marketCap;
        this.iconUrl = iconUrl;
    }

    public Crypto_Model(String name, String symbol, double price, double marketCap, String iconUrl, double percent_change_1h, double percent_change_24h, double percent_change_7d, double percent_change_30d, double percent_change_60d, double percent_change_90d) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.marketCap = marketCap;
        this.iconUrl = iconUrl;
        this.percent_change_1h = percent_change_1h;
        this.percent_change_24h = percent_change_24h;
        this.percent_change_7d = percent_change_7d;
        this.percent_change_30d = percent_change_30d;
        this.percent_change_60d = percent_change_60d;
        this.percent_change_90d = percent_change_90d;
    }

    public double getPercent_change_1h() {
        return percent_change_1h;
    }

    public void setPercent_change_1h(double percent_change_1h) {
        this.percent_change_1h = percent_change_1h;
    }

    public double getPercent_change_24h() {
        return percent_change_24h;
    }

    public void setPercent_change_24h(double percent_change_24h) {
        this.percent_change_24h = percent_change_24h;
    }

    public double getPercent_change_7d() {
        return percent_change_7d;
    }

    public void setPercent_change_7d(double percent_change_7d) {
        this.percent_change_7d = percent_change_7d;
    }

    public double getPercent_change_30d() {
        return percent_change_30d;
    }

    public void setPercent_change_30d(double percent_change_30d) {
        this.percent_change_30d = percent_change_30d;
    }

    public double getPercent_change_60d() {
        return percent_change_60d;
    }

    public void setPercent_change_60d(double percent_change_60d) {
        this.percent_change_60d = percent_change_60d;
    }

    public double getPercent_change_90d() {
        return percent_change_90d;
    }

    public void setPercent_change_90d(double percent_change_90d) {
        this.percent_change_90d = percent_change_90d;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }
}