package com.company.lukasz.shoppinglist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.company.lukasz.shoppinglist.Adapters.CustomAdapterSyncProducts;
import com.company.lukasz.shoppinglist.DBHelper.DatabaseHelper;
import com.company.lukasz.shoppinglist.Model.SyncProductsShoppingTable;

import java.util.ArrayList;

public class Specific_List_Activity extends AppCompatActivity {


    TextView textViewListName;
    TextView textViewListDate;
    Button btnAddProduct;
    ArrayList<SyncProductsShoppingTable> syncList;
    ListView listView;
    SyncProductsShoppingTable syncProductsShoppingTable;
    DatabaseHelper myDB;
    CustomAdapterSyncProducts adapterSyncProducts;
    String test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_list);

        textViewListName = findViewById(R.id.textViewListName);
        textViewListDate = findViewById(R.id.textViewListDate);
        btnAddProduct = findViewById(R.id.buttonAddNewProduct);

        String s_name = "";
        String s_date = "";
        final String name;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            s_name = extras.getString("S_NAME");
            s_date = extras.getString("S_DATE");
            name = s_name;
            test = s_name;

        } else {
            name = "Błąd przekazania zmiennej name";
        }
        textViewListName.setText(s_name);
        textViewListDate.setText(s_date);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Specific_List_Activity.this, ProductsList.class);
                intent.putExtra("S_NAME", name);
                startActivity(intent);
            }
        });

        myDB = new DatabaseHelper(this);
        syncList = new ArrayList<>();
        Cursor data = myDB.getListId(s_name);
        int itemID = 1;
        while (data.moveToNext()) {
            itemID = data.getInt(0);
        }
        if (itemID > -1) {

            Cursor getList = myDB.getSyncProductsListOnShoppingId(itemID);
            int numRows = getList.getCount();

//            if(numRows<=0){

//                myDB.addToSyncProductsShopping(itemID, 1,1,0);
//                syncProductsShoppingTable = new SyncProductsShoppingTable("Przykładowy produkt", "Kilogram", null,10,0);
//                syncList.add(syncProductsShoppingTable);
//
//                adapterSyncProducts = new CustomAdapterSyncProducts(this, R.layout.adapter_view_layout_sync_products, syncList);
//                listView = findViewById(R.id.listViewSyncProducts);
//                listView.setAdapter(adapterSyncProducts);
//            }
//
//            else{
            while (getList.moveToNext()) {
                syncProductsShoppingTable = new SyncProductsShoppingTable(getList.getString(0), getList.getString(1), getList.getBlob(2), getList.getInt(3), getList.getInt(4));
                syncList.add(syncProductsShoppingTable);
            }
            adapterSyncProducts = new CustomAdapterSyncProducts(this, R.layout.adapter_view_layout_sync_products, syncList, test);
            listView = findViewById(R.id.listViewSyncProducts);
            listView.setAdapter(adapterSyncProducts);


        }


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String p_name = syncList.get(position).getP_name();
                Cursor data = myDB.getProductIdOnName(p_name);
                int itemID = 1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }

                Cursor getNameId = myDB.getListId(name);
                int s_id = 1;
                while (getNameId.moveToNext()) {
                    s_id = getNameId.getInt(0);
                }

                if (itemID > -1 && s_id > -1) {
                    AlertDialog alertDialog = askOption(itemID, s_id, position);
                    alertDialog.show();
                }

                return true;
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String p_name = syncList.get(position).getP_name();
                Cursor data = myDB.getProductIdOnName(p_name);
                int itemID = 1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }

                Cursor getNameId = myDB.getListId(name);
                int s_id = 1;
                while (getNameId.moveToNext()) {
                    s_id = getNameId.getInt(0);
                }

                if (itemID > -1 && s_id > -1) {
                    myDB.updateStatusSyncProductsListOnShoppingId(s_id, itemID);
                    syncList.get(position).setP_status(1);
                    adapterSyncProducts.notifyDataSetChanged();
                }
            }
        });

    }

    private AlertDialog askOption(final int p_id, final int s_id, final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Usuwanie")
                .setMessage("Czy chcesz usunąć?")

                .setPositiveButton("Usuń", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {


                        myDB.deleteFromSyncProductsListOnShoppingId(s_id, p_id);
                        myDB.updateDecreaseNumOfProductsOnListId(s_id);
                        adapterSyncProducts.remove(syncList.get(position));
                        adapterSyncProducts.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                })

                .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();


        return alertDialog;

    }

    @Override
    protected void onResume() {
        super.onResume();


        myDB = new DatabaseHelper(this);
        syncList = new ArrayList<>();
        Cursor data = myDB.getListId(test);
        int itemID = 1;
        while (data.moveToNext()) {
            itemID = data.getInt(0);
        }
        if (itemID > -1) {

            Cursor getList = myDB.getSyncProductsListOnShoppingId(itemID);
            int numRows = getList.getCount();

            if (numRows <= 0) {

            } else {
                while (getList.moveToNext()) {
                    syncProductsShoppingTable = new SyncProductsShoppingTable(getList.getString(0), getList.getString(1), getList.getBlob(2), getList.getInt(3), getList.getInt(4));
                    syncList.add(syncProductsShoppingTable);
                }
                adapterSyncProducts = new CustomAdapterSyncProducts(this, R.layout.adapter_view_layout_sync_products, syncList, test);
                listView = findViewById(R.id.listViewSyncProducts);
                listView.setAdapter(adapterSyncProducts);

            }
        }
    }
}
