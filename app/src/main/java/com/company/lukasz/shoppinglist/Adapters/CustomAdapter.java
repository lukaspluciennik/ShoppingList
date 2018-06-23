package com.company.lukasz.shoppinglist.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.company.lukasz.shoppinglist.Model.ShoppingTable;
import com.company.lukasz.shoppinglist.R;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<ShoppingTable> {

    private LayoutInflater mInflater;
    private ArrayList<ShoppingTable> shoppingList;
    private int mViewResourceId;

    public CustomAdapter(Context context, int textViewResourceId, ArrayList<ShoppingTable> shoppingList){
        super(context, textViewResourceId, shoppingList);
        this.shoppingList = shoppingList;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId=textViewResourceId;

    }

    public View getView(int position, View convertView, ViewGroup parents){
        convertView=mInflater.inflate(mViewResourceId,null);

        ShoppingTable shoppingTable = shoppingList.get(position);

        if(shoppingTable != null){
            TextView s_name = convertView.findViewById(R.id.textViewName);
            TextView s_date = convertView.findViewById(R.id.textViewDate);
            TextView s_numOfProducts = convertView.findViewById(R.id.textViewNumOfProducts);
            TextView s_realized = convertView.findViewById(R.id.textViewListRealized);

            if(s_name != null){
                s_name.setText(shoppingTable.getS_name());
            }

            if(s_date != null){
                s_date.setText(shoppingTable.getS_date());
            }

            if(s_numOfProducts != null){
                s_numOfProducts.setText("Produkty: " + String.valueOf(shoppingTable.getS_numOfProducts()));
            }

            if(s_realized != null){
                if(shoppingTable.getS_realized() == 0){
                    s_realized.setText("W trakcie");
                    s_realized.setTextColor(ContextCompat.getColor(getContext(), R.color.colorAccent));
                } else {
                    s_realized.setText("Zrealizowana");
                    s_realized.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                }

            }

        }

        return convertView;

    }

}
