package com.company.lukasz.shoppinglist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.company.lukasz.shoppinglist.Adapters.CustomAdapter;
import com.company.lukasz.shoppinglist.DBHelper.DatabaseHelper;
import com.company.lukasz.shoppinglist.Model.ShoppingTable;

import java.util.ArrayList;

public class ViewListContents extends AppCompatActivity {

    DatabaseHelper myDB;
    ArrayList<ShoppingTable> shoppingList;
    ListView listView;
    ShoppingTable shoppingTable;
    CustomAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewcontents_layout);



        myDB = new DatabaseHelper(this);

        shoppingList = new ArrayList<>();
        Cursor data = myDB.getListContent();
        int numRows = data.getCount();
        if (numRows <= 0) {
            myDB.addData("Przykładowa lista", "26.06.2018", 0, 0);
            shoppingTable = new ShoppingTable("Przykładowa lista", "26.06.2018", 0, 0);
            shoppingList.add(shoppingTable);
            adapter = new CustomAdapter(this, R.layout.adapter_view_layout, shoppingList);
            listView = findViewById(R.id.shoppingListView);
            listView.setAdapter(adapter);
        }else{
            while (data.moveToNext()){
                shoppingTable = new ShoppingTable(data.getString(1), data.getString(2), data.getInt(3), data.getInt(4));
                shoppingList.add(shoppingTable);
            }
            adapter = new CustomAdapter(this, R.layout.adapter_view_layout, shoppingList);
            listView = findViewById(R.id.shoppingListView);
            listView.setAdapter(adapter);

        }


        Button button = findViewById(R.id.buttonAddNewList);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewListContents.this, MainActivity.class);
                startActivity(intent);
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                String name = shoppingList.get(i).getS_name();
                Cursor data = myDB.getListId(name);
                int itemID = 1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                    Log.d("Help", "" + data.getInt(0));
                }
                if (itemID > -1) {
                    AlertDialog alertDialog = askOption(itemID, i);
                    alertDialog.show();


                }
                return true;
            }
        });


        // Klikniecie pozycji na liscie
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = shoppingList.get(i).getS_name();
                String data = shoppingList.get(i).getS_date();
                Log.d("Help", "You clicked " + name);
                Intent intent = new Intent(ViewListContents.this, Specific_List_Activity.class);
                intent.putExtra("S_NAME", name);
                intent.putExtra("S_DATE", data);
                startActivity(intent);
            }
        });


    }

    private AlertDialog askOption(final int id, final int i) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Opcje")
                .setMessage("Co chcesz zrobić?")

                .setPositiveButton("Usuń", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {


                        myDB.deleteFromShoppingListOnId(id);
                        adapter.remove(shoppingList.get(i));
                        adapter.notifyDataSetChanged();
                        dialog.dismiss();

                    }
                })

                .setNeutralButton("Zrealizowana", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        myDB.updateListStatusOnRealized(id);
                        shoppingList.get(i).setS_realized(1);
                        adapter.notifyDataSetChanged();
                        dialogInterface.dismiss();

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

        shoppingList = new ArrayList<>();
        Cursor data = myDB.getListContent();
        int numRows = data.getCount();
        if (numRows <= 0) {
            myDB.addData("Przykładowa lista", "26.06.2018", 0, 0);
            shoppingTable = new ShoppingTable("Przykładowa lista", "26.06.2018", 0, 0);
            shoppingList.add(shoppingTable);
            adapter = new CustomAdapter(this, R.layout.adapter_view_layout, shoppingList);
            listView = findViewById(R.id.shoppingListView);
            listView.setAdapter(adapter);
        } else {
            while (data.moveToNext()) {
                shoppingTable = new ShoppingTable(data.getString(1), data.getString(2), data.getInt(3), data.getInt(4));
                shoppingList.add(shoppingTable);
            }
            adapter = new CustomAdapter(this, R.layout.adapter_view_layout, shoppingList);
            listView = findViewById(R.id.shoppingListView);
            listView.setAdapter(adapter);

        }
    }
}
