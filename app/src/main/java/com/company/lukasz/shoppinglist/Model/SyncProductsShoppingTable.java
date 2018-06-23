package com.company.lukasz.shoppinglist.Model;

public class SyncProductsShoppingTable {

    private String p_name;
    private String p_unit;
    private byte[] p_picture;
    private int p_quantity;
    private int p_status;

    public SyncProductsShoppingTable(String p_name, String p_unit, byte[] p_picture, int p_quantity, int p_status) {
        this.p_name = p_name;
        this.p_unit = p_unit;
        this.p_picture = p_picture;
        this.p_quantity = p_quantity;
        this.p_status = p_status;
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

    public int getP_quantity() {
        return p_quantity;
    }

    public int getP_status() {
        return p_status;
    }

    public void setP_quantity(int p_quantity) {
        this.p_quantity = p_quantity;
    }

    public void setP_status(int p_status) {
        this.p_status = p_status;
    }
}
