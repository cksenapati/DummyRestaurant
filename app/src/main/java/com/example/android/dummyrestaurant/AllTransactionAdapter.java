package com.example.android.dummyrestaurant;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
/**
 * Created by chandan on 08-03-2017.
 */

public class AllTransactionAdapter extends ArrayAdapter<Transaction> {

    Activity mActivity;

    public AllTransactionAdapter(Activity activity, ArrayList<Transaction> arrayListAllProductsInCart) {
        super(activity, 0, arrayListAllProductsInCart);
        mActivity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Transaction eachTransaction = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_single_transaction, parent, false);
        }


        EasyFlipView mFlipViewTransactionDetails = (EasyFlipView)  convertView.findViewById(R.id.flip_view_transaction_details);
        mFlipViewTransactionDetails.flipTheView();

        TextView textViewAmountPaid = (TextView) convertView.findViewById(R.id.text_view_amount_paid);
        textViewAmountPaid.setText(eachTransaction.getAmountPaid());

        TextView textViewCustomerName = (TextView) convertView.findViewById(R.id.text_view_name);
        textViewCustomerName.setText(eachTransaction.getName());

        TextView textViewCustomerPhoneNo = (TextView) convertView.findViewById(R.id.text_view_phone_no);
        textViewCustomerPhoneNo.setText(eachTransaction.getPhoneNo());

        TextView textViewDateOfTransaction = (TextView) convertView.findViewById(R.id.text_view_date_of_transaction);
        textViewDateOfTransaction.setText(eachTransaction.getDateOfTransaction());


        return convertView;
    }

}

