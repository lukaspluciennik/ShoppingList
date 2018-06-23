package com.company.lukasz.shoppinglist.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.company.lukasz.shoppinglist.Model.ProductsTable;
import com.company.lukasz.shoppinglist.R;

import java.util.ArrayList;

public class CustomAdapterProducts extends ArrayAdapter<ProductsTable> {

    private LayoutInflater layoutInflater;
    private ArrayList<ProductsTable> productsList;
    private int mViewResourceId;

    public CustomAdapterProducts(@NonNull Context context, int textViewResourceId, ArrayList<ProductsTable> productsList) {
        super(context, textViewResourceId, productsList);
        this.productsList = productsList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parents) {
        convertView = layoutInflater.inflate(mViewResourceId, null);

        ProductsTable productsTable = productsList.get(position);

        if (productsTable != null) {
            TextView p_name = convertView.findViewById(R.id.textViewProductName);
            TextView p_unit = convertView.findViewById(R.id.textViewProductUnit);
            ImageView imageView = convertView.findViewById(R.id.imageViewProductImage);

            if (p_name != null) {
                p_name.setText(productsTable.getP_name());
            }

            if (p_unit != null) {
                p_unit.setText(productsTable.getP_unit());
            }

            if (imageView != null) {
                byte[] productImage = productsTable.getP_picture();
                Bitmap bitmap = BitmapFactory.decodeByteArray(productImage, 0, productImage.length);
                bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true);
                imageView.setImageBitmap(bitmap);
            }

        }
        return convertView;
    }
}
