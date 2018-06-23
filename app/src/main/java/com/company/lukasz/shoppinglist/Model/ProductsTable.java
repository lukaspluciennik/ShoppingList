package com.company.lukasz.shoppinglist.Model;

public class ProductsTable {

    private String p_name;
    private String p_unit;
    private byte[] p_picture;

    public ProductsTable(String p_name, String p_unit, byte[] p_picture) {
        this.p_name = p_name;
        this.p_unit = p_unit;
        this.p_picture = p_picture;
    }

    public String getP_name() {
        return p_name;
    }

    public String getP_unit() {
        return p_unit;
    }

    public byte[] getP_picture() {
        return p_picture;
    }
}
