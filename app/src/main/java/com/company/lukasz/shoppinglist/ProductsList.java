package com.company.lukasz.shoppinglist;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.company.lukasz.shoppinglist.Adapters.CustomAdapterProducts;
import com.company.lukasz.shoppinglist.DBHelper.DatabaseHelper;
import com.company.lukasz.shoppinglist.Model.ProductsTable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ProductsList extends AppCompatActivity {

    Button btnAddNewProductActivity;
    DatabaseHelper myDB;
    ArrayList<ProductsTable> productsList;
    ListView listView;
    ProductsTable productsTable;
    CustomAdapterProducts adapterProducts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);


        btnAddNewProductActivity = findViewById(R.id.buttonAddNewProductActivity);
        myDB = new DatabaseHelper(this);
        productsList = new ArrayList<>();
        Cursor data = myDB.getListOfProducts();
        int numRows = data.getCount();

        final String s_name;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            s_name = extras.getString("S_NAME");
        } else {
            s_name = "Błąd przekazania zmiennej s_name";
        }

        if (numRows <= 0) {

            ImageView mleko = new ImageView(this);
            mleko.setImageResource(R.mipmap.ic_mlekolayer);
            ImageView ser = new ImageView(this);
            ser.setImageResource(R.mipmap.ic_serlayer);
            ImageView szynka = new ImageView(this);
            szynka.setImageResource(R.mipmap.ic_szynkalayer);
            ImageView salami = new ImageView(this);
            salami.setImageResource(R.mipmap.ic_salamialayer);
            ImageView kielbasa = new ImageView(this);
            kielbasa.setImageResource(R.mipmap.ic_kielbasalayer);
            ImageView boczek = new ImageView(this);
            boczek.setImageResource(R.mipmap.ic_boczeklayer);
            ImageView chleb = new ImageView(this);
            chleb.setImageResource(R.mipmap.ic_chleblayer);
            ImageView ziemniaki = new ImageView(this);
            ziemniaki.setImageResource(R.mipmap.ic_ziemniakilayer);
            ImageView cebula = new ImageView(this);
            cebula.setImageResource(R.mipmap.ic_cebulalayer);
            ImageView jablko = new ImageView(this);
            jablko.setImageResource(R.mipmap.ic_jablkolayer);
            ImageView truskawki = new ImageView(this);
            truskawki.setImageResource(R.mipmap.ic_truskawkilayer);
            ImageView herbata = new ImageView(this);
            herbata.setImageResource(R.mipmap.ic_herbatalayer);
            ImageView sok = new ImageView(this);
            sok.setImageResource(R.mipmap.ic_soklayer);


            // myDB.addProductToDB("Przykładowy produkt", "Jednostka", imageViewToByte(testImage));
            // productsTable = new ProductsTable("Przykładowy produkt", "Jednostka", imageViewToByte(testImage));
            myDB.addProductToDB("Mleko", "Karton", imageViewToByte(mleko));
            productsTable = new ProductsTable("Mleko", "Karton", imageViewToByte(mleko));
            productsList.add(productsTable);
            myDB.addProductToDB("Ser żółty", "Gram", imageViewToByte(ser));
            productsTable = new ProductsTable("Ser żółty", "Gram", imageViewToByte(ser));
            productsList.add(productsTable);
            myDB.addProductToDB("Szynka", "Gram", imageViewToByte(szynka));
            productsTable = new ProductsTable("Szynka", "Gram", imageViewToByte(szynka));
            productsList.add(productsTable);
            myDB.addProductToDB("Salami", "Gram", imageViewToByte(salami));
            productsTable = new ProductsTable("Salami", "Gram", imageViewToByte(salami));
            productsList.add(productsTable);
            myDB.addProductToDB("Kiełbasa", "Gram", imageViewToByte(kielbasa));
            productsTable = new ProductsTable("Kiełbasa", "Gram", imageViewToByte(kielbasa));
            productsList.add(productsTable);
            myDB.addProductToDB("Boczek", "Gram", imageViewToByte(boczek));
            productsTable = new ProductsTable("Boczek", "Opakowanie", imageViewToByte(boczek));
            productsList.add(productsTable);
            myDB.addProductToDB("Chleb", "Sztuka", imageViewToByte(chleb));
            productsTable = new ProductsTable("Chleb", "Sztuka", imageViewToByte(chleb));
            productsList.add(productsTable);
            myDB.addProductToDB("Ziemniaki", "Kilogram", imageViewToByte(ziemniaki));
            productsTable = new ProductsTable("Ziemniaki", "Kilogram", imageViewToByte(ziemniaki));
            productsList.add(productsTable);
            myDB.addProductToDB("Cebula", "Sztuka", imageViewToByte(cebula));
            productsTable = new ProductsTable("Cebula", "Sztuka", imageViewToByte(cebula));
            productsList.add(productsTable);
            myDB.addProductToDB("Jabłko", "Sztuka", imageViewToByte(jablko));
            productsTable = new ProductsTable("Jabłko", "Sztuka", imageViewToByte(jablko));
            productsList.add(productsTable);
            myDB.addProductToDB("Truskawki", "Gram", imageViewToByte(truskawki));
            productsTable = new ProductsTable("Truskawki", "Gram", imageViewToByte(truskawki));
            productsList.add(productsTable);
            myDB.addProductToDB("Herbata", "Opakowanie", imageViewToByte(herbata));
            productsTable = new ProductsTable("Herbata", "Opakowanie", imageViewToByte(herbata));
            productsList.add(productsTable);
            myDB.addProductToDB("Sok", "Karton", imageViewToByte(sok));
            productsTable = new ProductsTable("Sok", "Karton", imageViewToByte(sok));
            productsList.add(productsTable);

            adapterProducts = new CustomAdapterProducts(this, R.layout.adapter_view_layout_products, productsList);
            listView = findViewById(R.id.productsListView);
            listView.setAdapter(adapterProducts);
        } else {
            while (data.moveToNext()) {
                productsTable = new ProductsTable(data.getString(1), data.getString(2), data.getBlob(3));
                productsList.add(productsTable);

            }
            adapterProducts = new CustomAdapterProducts(this, R.layout.adapter_view_layout_products, productsList);
            listView = findViewById(R.id.productsListView);
            listView.setAdapter(adapterProducts);

        }

        btnAddNewProductActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductsList.this, AddNewProduct.class);
                startActivity(intent);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String p_name = productsList.get(position).getP_name();
                Cursor data = myDB.getListId(s_name);
                int itemID = 1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }

                Cursor data2 = myDB.getProductIdOnName(p_name);
                int productID = 1;
                while (data2.moveToNext()) {
                    productID = data2.getInt(0);
                }

                if (productID > -1 && itemID > -1) {
                    addProduct(itemID, productID, 0, 0);
                    myDB.updateNumOfProductsOnListId(itemID);
                }
            }
        });


        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String p_name = productsList.get(position).getP_name();
                Cursor data = myDB.getProductIdOnName(p_name);
                int itemID = 1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }

                if (itemID > -1) {
                    AlertDialog alertDialog = askOption(itemID, position);
                    alertDialog.show();
                }

                return true;
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

    public void addProduct(int s_id, int p_id, int quantity, int success) {
        boolean insertData = myDB.addToSyncProductsShopping(s_id, p_id, quantity, success);

        if (insertData) {
            Toast.makeText(ProductsList.this, "Dodano produkt do listy", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(ProductsList.this, "Coś poszło nie tak", Toast.LENGTH_SHORT).show();
        }

    }

    private AlertDialog askOption(final int id, final int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Usuwanie")
                .setMessage("Czy chcesz usunąć?")

                .setPositiveButton("Usuń", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {


                        myDB.deleteFromProductsTableOnId(id);
                        adapterProducts.remove(productsList.get(position));
                        adapterProducts.notifyDataSetChanged();
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
        productsList = new ArrayList<>();
        Cursor data = myDB.getListOfProducts();
        while (data.moveToNext()) {
            productsTable = new ProductsTable(data.getString(1), data.getString(2), data.getBlob(3));
            productsList.add(productsTable);

        }
        adapterProducts = new CustomAdapterProducts(this, R.layout.adapter_view_layout_products, productsList);
        listView = findViewById(R.id.productsListView);
        listView.setAdapter(adapterProducts);

    }
}
