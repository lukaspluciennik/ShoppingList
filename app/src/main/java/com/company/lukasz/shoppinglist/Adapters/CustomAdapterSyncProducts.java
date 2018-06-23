package com.company.lukasz.shoppinglist.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.lukasz.shoppinglist.DBHelper.DatabaseHelper;
import com.company.lukasz.shoppinglist.Model.SyncProductsShoppingTable;
import com.company.lukasz.shoppinglist.R;

import java.util.ArrayList;

public class CustomAdapterSyncProducts extends ArrayAdapter<SyncProductsShoppingTable> {

    private LayoutInflater layoutInflater;
    private ArrayList<SyncProductsShoppingTable> synclist;
    private int mViewResourceId;
    DatabaseHelper myDB;
    private final String s_name;


    public CustomAdapterSyncProducts(Context context, int textViewResourceId, ArrayList<SyncProductsShoppingTable> synclist, String s_name) {
        super(context, textViewResourceId, synclist);
        this.synclist = synclist;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
        this.s_name=s_name;

    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parents) {

        convertView = layoutInflater.inflate(mViewResourceId, null);
        myDB = new DatabaseHelper(getContext());

        final SyncProductsShoppingTable syncProductsShoppingTable = synclist.get(position);

        if (syncProductsShoppingTable != null) {
            Button btnPlusProduct = convertView.findViewById(R.id.buttonProductQuantityPlus);
            Button btnMinusProduct = convertView.findViewById(R.id.buttonProductQuantityMinus);
            TextView p_name = convertView.findViewById(R.id.textViewSyncProductName);
            TextView p_unit = convertView.findViewById(R.id.textViewSyncProductUnit);
            ImageView imageView = convertView.findViewById(R.id.imageViewSyncProductImage);
            final TextView p_quantity = convertView.findViewById(R.id.textViewProductQuantity);
            TextView p_status = convertView.findViewById(R.id.textViewProductStatus);

            if (p_name != null) {
                p_name.setText(syncProductsShoppingTable.getP_name());
            }

            if (p_unit != null) {
                p_unit.setText(syncProductsShoppingTable.getP_unit());
            }

            if (imageView != null) {
                byte[] productImage = syncProductsShoppingTable.getP_picture();
                Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
                bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                imageView.setImageBitmap(bitmap);
            }

            if (p_quantity != null) {
                p_quantity.setText("Ilość: " + String.valueOf(syncProductsShoppingTable.getP_quantity()));
            }

            if (p_status != null) {
                if (syncProductsShoppingTable.getP_status() == 1) {
                    p_status.setText("Kupiony");
                    p_status.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                } else {
                    p_status.setText("Niekupiony");
                    p_status.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                }

            }


            btnPlusProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    String p_name = syncProductsShoppingTable.getP_name();
                    Cursor data = myDB.getProductIdOnName(p_name);
                    int itemID = 1;
                    while (data.moveToNext()) {
                        itemID = data.getInt(0);
                    }

                    Cursor getNameId = myDB.getListId(s_name);
                    int s_id = 1;
                    while (getNameId.moveToNext()) {
                        s_id = getNameId.getInt(0);
                    }

                    if (itemID > -1 && s_id > -1) {

                        String unit = syncProductsShoppingTable.getP_unit();
                        if(unit.equals("Gram")){
                            myDB.updatePlusGramQuantitySyncProductsListOnShoppingId(s_id,itemID);
                            syncProductsShoppingTable.setP_quantity(syncProductsShoppingTable.getP_quantity()+100);
                        } else{
                            myDB.updatePlusQuantitySyncProductsListOnShoppingId(s_id,itemID);
                            syncProductsShoppingTable.setP_quantity(syncProductsShoppingTable.getP_quantity()+1);
                        }
                        p_quantity.setText("Ilość: " + String.valueOf(syncProductsShoppingTable.getP_quantity()));
                    }


                }
            });

            btnMinusProduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(syncProductsShoppingTable.getP_quantity() > 1) {

                        String p_name = syncProductsShoppingTable.getP_name();
                        Cursor data = myDB.getProductIdOnName(p_name);
                        int itemID = 1;
                        while (data.moveToNext()) {
                            itemID = data.getInt(0);
                        }

                        Cursor getNameId = myDB.getListId(s_name);
                        int s_id = 1;
                        while (getNameId.moveToNext()) {
                            s_id = getNameId.getInt(0);
                        }

                        if (itemID > -1 && s_id > -1) {

                            String unit =syncProductsShoppingTable.getP_unit();
                            if(unit.equals("Gram")){
                                myDB.updateMinusGramQuantitySyncProductsListOnShoppingId(s_id, itemID);
                                syncProductsShoppingTable.setP_quantity(syncProductsShoppingTable.getP_quantity() - 100);
                            } else {
                                myDB.updateMinusQuantitySyncProductsListOnShoppingId(s_id, itemID);
                                syncProductsShoppingTable.setP_quantity(syncProductsShoppingTable.getP_quantity() - 1);
                            }

                            p_quantity.setText("Ilość: " + String.valueOf(syncProductsShoppingTable.getP_quantity()));
                        }
                    }
                }
            });


        }
        return convertView;

    }
}
