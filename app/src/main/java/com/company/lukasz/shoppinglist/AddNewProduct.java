package com.company.lukasz.shoppinglist;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.company.lukasz.shoppinglist.DBHelper.DatabaseHelper;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class AddNewProduct extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 1888;
    Button btnAddNewProductToTable;
    Button btnDoPicture;
    Button btnAddImage;
    EditText editTextNewProductName;
    EditText editTextAddUnit;
    ImageView imageView;
    DatabaseHelper myDB;

    final int REQUEST_CODE_GALLERY = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_product);

        btnAddNewProductToTable = findViewById(R.id.btnAddNewProduct);
        btnDoPicture = findViewById(R.id.btnAddPicture);
        btnAddImage = findViewById(R.id.buttonAddImage);
        editTextNewProductName = findViewById(R.id.editTextNewProductName);
        editTextAddUnit = findViewById(R.id.editTextAddUnit);
        imageView = findViewById(R.id.imageViewAddProduct);
        myDB = new DatabaseHelper(this);

        btnAddNewProductToTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dodawanie do Tabeli nowego produktu
                String newProduct = editTextNewProductName.getText().toString();
                String newUnit = editTextAddUnit.getText().toString();
                if (editTextNewProductName.length() != 0 && editTextAddUnit.length() != 0) {
                    try {
                        AddNewProduct(newProduct, newUnit, imageViewToByte(imageView));

                        Toast.makeText(AddNewProduct.this, "Produkt dodany", Toast.LENGTH_SHORT);
                        editTextNewProductName.setText("");
                        editTextAddUnit.setText("");
                        imageView.setImageResource(R.mipmap.ic_zakupylayer);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(AddNewProduct.this,"Nie załączyłeś zdjęcia produktu", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(AddNewProduct.this, "Nie wpisałeś wszystkich danych.", Toast.LENGTH_SHORT).show();
                }


            }
        });


        btnDoPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddNewProduct.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        CAMERA_REQUEST
                );


            }
        });


        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        AddNewProduct.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });


    }

    private byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == REQUEST_CODE_GALLERY) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(AddNewProduct.this, "Brak uprawnień do galerii zdjęć", Toast.LENGTH_SHORT).show();
            }
            return;
        } else if (requestCode == CAMERA_REQUEST){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Uprawnienia do aparatu przyznane", Toast.LENGTH_SHORT).show();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else{
                Toast.makeText(this, "Brak uprawnień do aparatu", Toast.LENGTH_LONG).show();
            }
            return;
        }


        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null) {
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        } else if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK){
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                imageView.setImageBitmap(photo);
        }



        super.onActivityResult(requestCode, resultCode, data);
    }

    public void AddNewProduct(String newProduct, String newUnit, byte[] p_picture) {
        boolean insertData = myDB.addProductToDB(newProduct, newUnit, p_picture);

        if (insertData) {
            Toast.makeText(AddNewProduct.this, "Dodano nowy produkt", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(AddNewProduct.this, "Coś poszło nie tak", Toast.LENGTH_SHORT).show();
        }
    }
}
