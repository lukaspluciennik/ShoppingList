package com.company.lukasz.shoppinglist.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shopping.db";
    //tabela shoppingTable
    private static final String TABLE_NAME = "shopping_table";
    private static final String COL1 = "S_ID";
    private static final String COL2 = "S_NAME";
    private static final String COL3 = "S_DATE";
    private static final String COL4 = "S_NUMOFPRODUCTS";
    private static final String COL5 = "S_REALIZED";
    //tabela productsTable
    private static final String TABLE_NAME_PRODUCTS = "products_table";
    private static final String COL1_PRODUCTS = "P_ID";
    private static final String COL2_PRODUCTS = "P_NAME";
    private static final String COL3_PRODUCTS = "P_UNIT";
    private static final String COL4_PRODUCTS = "P_PICTURE";
    //tabela syncProductsShoppingTable
    private static final String TABLE_NAME_SYNC = "sync_table";
    private static final String COL1_SYNC = "SYNC_ID";
    private static final String COL2_SYNC = "S_ID";
    private static final String COL3_SYNC = "P_ID";
    private static final String COL4_SYNC = "P_QUANTITY";
    private static final String COL5_SYNC = "P_STATUS";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createShoppingTable = "CREATE TABLE " + TABLE_NAME + " (S_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "S_NAME VARCHAR, S_DATE VARCHAR, S_NUMOFPRODUCTS INTEGER, S_REALIZED INTEGER)";
        String createProductsTable = "CREATE TABLE " + TABLE_NAME_PRODUCTS + " (P_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "P_NAME VARCHAR, P_UNIT VARCHAR, P_PICTURE BLOB)";
        String createSyncProductsTable = "CREATE TABLE " + TABLE_NAME_SYNC + " (SYNC_ID INTEGER PRIMARY KEY AUTOINCREMENT, " + "S_ID INTEGER, P_ID INTEGER, P_QUANTITY INTEGER, P_STATUS INTEGER)";
        db.execSQL(createShoppingTable);
        db.execSQL(createProductsTable);
        db.execSQL(createSyncProductsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SYNC);
    }

    //dodawanie do ShoppingTable
    public boolean addData (String s_name, String s_date, int s_numOfProducts, int s_realized){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, s_name);
        contentValues.put(COL3, s_date);
        contentValues.put(COL4, s_numOfProducts);
        contentValues.put(COL5, s_realized);


        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    //dodawanie do ProductsTable
    public boolean addProductToDB(String p_name, String p_unit, byte[] p_picture) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_PRODUCTS, p_name);
        contentValues.put(COL3_PRODUCTS, p_unit);
        contentValues.put(COL4_PRODUCTS, p_picture);


        long result = db.insert(TABLE_NAME_PRODUCTS, null, contentValues);

        return result != -1;
    }

    //dodawanie do SyncProductsShopping
    public boolean addToSyncProductsShopping(int s_id, int p_id, int p_quantity, int p_status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2_SYNC, s_id);
        contentValues.put(COL3_SYNC, p_id);
        contentValues.put(COL4_SYNC, p_quantity);
        contentValues.put(COL5_SYNC, p_status);

        long result = db.insert(TABLE_NAME_SYNC, null, contentValues);

        return result != -1;
    }


    //Pobieranie całej zawartości tabeli shoppingTable
    public Cursor getListContent(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    //Pobieranie id tabeli shopping table na podstawie name
    public Cursor getListId(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //Pobieranie tabeli shopping table na podstawie id
    public Cursor getShoppingTableOnId(int s_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL1 + " = '" + s_id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    //usuwanie rekordu z tabeli shoppingTable za pomoca name
    public void deleteFromShoppingList(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " +
                COL2 + " = '" + name + "'";
        db.execSQL(query);
    }

    //usuwanie rekordu z tabeli shoppingTable za pomoca id
    public void deleteFromShoppingListOnId(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " +
                COL1 + " = '" + id + "'";
        db.execSQL(query);
    }

    //Zmiana statusu listy na zrealizowaną w ShoppingID
    public void updateListStatusOnRealized(int s_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL5 + " = '" + 1 + "'" + " WHERE " +
                COL1 + " = '" + s_id + "'";
        db.execSQL(query);
    }

    //Zwiekszanie liczby produktów w konkretnym rekordzie tabeli shoppingTable na postawie ShoppingID
    public void updateNumOfProductsOnListId(int s_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 + " = " + COL4 + " + '" + 1 + "'" + " WHERE " +
                COL1 + " = '" + s_id + "'";
        db.execSQL(query);
    }

    //Zmniejszanie liczby produktów w konkretnym rekordzie tabeli shoppingTable na postawie ShoppingID
    public void updateDecreaseNumOfProductsOnListId(int s_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 + " = " + COL4 + " - '" + 1 + "'" + " WHERE " +
                COL1 + " = '" + s_id + "'";
        db.execSQL(query);
    }

    //Pobieranie całej zawartości tabeli productsTable
    public Cursor getListOfProducts() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME_PRODUCTS, null);
        return data;
    }

    //Pobieranie id tabeli products table na podstawie name
    public Cursor getProductIdOnName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1_PRODUCTS + " FROM " + TABLE_NAME_PRODUCTS +
                " WHERE " + COL2_PRODUCTS + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //Pobieranie tabeli products table na podstawie id
    public Cursor getProductsTableOnId(int p_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1_PRODUCTS + " FROM " + TABLE_NAME_PRODUCTS +
                " WHERE " + COL1_PRODUCTS + " = '" + p_id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //usuwanie rekorku z tabeli productsTable za pomoca name
    public void deleteFromProductsTableOnName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME_PRODUCTS + " WHERE " +
                COL2_PRODUCTS + " = '" + name + "'";
        db.execSQL(query);
    }


    //usuwanie rekordu z tabeli productsTable za pomoca id
    public void deleteFromProductsTableOnId(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME_PRODUCTS + " WHERE " +
                COL1_PRODUCTS + " = '" + id + "'";
        db.execSQL(query);
    }

    //Pobieranie tabeli SyncProductsShopping na podstawie Shopping ID
    public Cursor getSyncProductsListOnShoppingId(int s_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL2_PRODUCTS + ", " + COL3_PRODUCTS + ", " + COL4_PRODUCTS + ", " + COL4_SYNC + ", " + COL5_SYNC + " FROM " + TABLE_NAME_PRODUCTS +
                " JOIN " + TABLE_NAME_SYNC + " ON " + TABLE_NAME_PRODUCTS + "." + COL1_PRODUCTS + "=" + TABLE_NAME_SYNC + "." + COL3_SYNC + " WHERE " + COL2_SYNC + " = '" + s_id + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //Usuwanie z tabeli SyncProductsShopping na podstawie ShoppingID i ProductId
    public void deleteFromSyncProductsListOnShoppingId(int s_id, int p_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME_SYNC + " WHERE " +
                COL2_SYNC + " = '" + s_id + "'" + " AND " + COL3_SYNC + " = '" + p_id + "'";
        db.execSQL(query);
    }

    //Zmiana statusu produktu na kupiony w tabeli SyncProductsShopping na podstawie ShoppingID i ProductId
    public void updateStatusSyncProductsListOnShoppingId(int s_id, int p_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME_SYNC + " SET " + COL5_SYNC + " = '" + 1 + "'" + " WHERE " +
                COL2_SYNC + " = '" + s_id + "'" + " AND " + COL3_SYNC + " = '" + p_id + "'";
        db.execSQL(query);
    }

    //Zwiekszenie ilosci produktu do kupienia w tabeli SyncProductsShopping na podstawie ShoppingID i ProductId
    public void updatePlusQuantitySyncProductsListOnShoppingId(int s_id, int p_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME_SYNC + " SET " + COL4_SYNC + " = " + COL4_SYNC + " + '" + 1 + "'" + " WHERE " +
                COL2_SYNC + " = '" + s_id + "'" + " AND " + COL3_SYNC + " = '" + p_id + "'";
        db.execSQL(query);
    }

    //Zmniejszenie ilosci produktu do kupienia w tabeli SyncProductsShopping na podstawie ShoppingID i ProductId
    public void updateMinusQuantitySyncProductsListOnShoppingId(int s_id, int p_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME_SYNC + " SET " + COL4_SYNC + " = " + COL4_SYNC + " - '" + 1 + "'" + " WHERE " +
                COL2_SYNC + " = '" + s_id + "'" + " AND " + COL3_SYNC + " = '" + p_id + "'";
        db.execSQL(query);
    }
    //Zwiekszenie ilosci gram produktu do kupienia w tabeli SyncProductsShopping na podstawie ShoppingID i ProductId
    public void updatePlusGramQuantitySyncProductsListOnShoppingId(int s_id, int p_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME_SYNC + " SET " + COL4_SYNC + " = " + COL4_SYNC + " + '" + 100 + "'" + " WHERE " +
                COL2_SYNC + " = '" + s_id + "'" + " AND " + COL3_SYNC + " = '" + p_id + "'";
        db.execSQL(query);
    }

    //Zmniejszenie ilosci gram produktu do kupienia w tabeli SyncProductsShopping na podstawie ShoppingID i ProductId
    public void updateMinusGramQuantitySyncProductsListOnShoppingId(int s_id, int p_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME_SYNC + " SET " + COL4_SYNC + " = " + COL4_SYNC + " - '" + 100 + "'" + " WHERE " +
                COL2_SYNC + " = '" + s_id + "'" + " AND " + COL3_SYNC + " = '" + p_id + "'";
        db.execSQL(query);
    }
}
