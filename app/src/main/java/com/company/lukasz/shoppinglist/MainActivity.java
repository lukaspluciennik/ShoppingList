package com.company.lukasz.shoppinglist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.company.lukasz.shoppinglist.DBHelper.DatabaseHelper;

public class MainActivity extends AppCompatActivity {


    DatabaseHelper myDB;
    Button btnAdd, btnView;
    EditText editText;
    EditText editTextDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editText = findViewById(R.id.editText);
        editTextDate = findViewById(R.id.editTextDate);
        btnAdd = findViewById(R.id.btnAddList);
        btnView = findViewById(R.id.btnViewAllLists);
        myDB = new DatabaseHelper(this);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ViewListContents.class);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEntry = editText.getText().toString();
                String newEntryDate = editTextDate.getText().toString();
                if (editText.length() != 0 && editTextDate.length() != 0) {
                    AddData(newEntry, newEntryDate, 0, 0);
                    editText.setText("");
                    editTextDate.setText("");
                } else {
                    Toast.makeText(MainActivity.this, "Nie wpisałeś wszystkich danych", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void AddData(String newEntry, String date, int numOfProducts, int realized){
        boolean insertData = myDB.addData(newEntry, date, numOfProducts, realized);

        if(insertData){
            Toast.makeText(MainActivity.this, "Dodano nową listę", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "Coś poszło nie tak", Toast.LENGTH_SHORT).show();
        }

    }

}




