package com.company.lukasz.shoppinglist.Model;

public class ShoppingTable {
    private String s_name;
    private String s_date;
    private int s_numOfProducts;
    private int s_realized;

    public ShoppingTable(String s_name, String s_date, int s_numOfProducts, int s_realized) {
        this.s_name = s_name;
        this.s_date = s_date;
        this.s_numOfProducts = s_numOfProducts;
        this.s_realized = s_realized;
    }

    public String getS_name() {
        return s_name;
    }

    public String getS_date() {
        return s_date;
    }

    public int getS_numOfProducts() {
        return s_numOfProducts;
    }

    public int getS_realized() {
        return s_realized;
    }

    public void setS_realized(int s_realized) {
        this.s_realized = s_realized;
    }

    public void setS_numOfProducts(int s_numOfProducts) {
        this.s_numOfProducts = s_numOfProducts;
    }
}
