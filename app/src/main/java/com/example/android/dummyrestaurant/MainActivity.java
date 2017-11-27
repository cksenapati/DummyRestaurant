package com.example.android.dummyrestaurant;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView mTextViewTodaySaleCount,mTextViewTodaySaleSum,mTextViewTotalSaleCount,mTextViewTotalSaleSum;
    ListView mListViewAllTransactions;
    ArrayList<Transaction> mArrayListAllTransactions;

    String todaySaleCount,todaySaleSum,totalSaleCount,totalSaleSum;
    AllTransactionAdapter transactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeScreen();

        createThread();
    }

    public void initializeScreen()
    {
        getSupportActionBar().setTitle("Dummy Restaurant");

        mListViewAllTransactions = (ListView) findViewById(R.id.list_view_all_transactions);

        View header = getLayoutInflater().inflate(R.layout.main_activity_header, null);
        mListViewAllTransactions.addHeaderView(header);

        mTextViewTodaySaleCount = (TextView) header.findViewById(R.id.text_view_today_sale_count);
        mTextViewTodaySaleSum = (TextView) header.findViewById(R.id.text_view_today_sale_sum);
        mTextViewTotalSaleCount = (TextView) header.findViewById(R.id.text_view_total_sale_count);
        mTextViewTotalSaleSum = (TextView) header.findViewById(R.id.text_view_total_sale_sum);



        mArrayListAllTransactions = new ArrayList<>();
    }

    public void createThread()
    {

        // Create URL object
        String openTDbBaseUrl = Constants.OPENTDB_BASE_URL;

        doYouKnowAsyncTask task = new doYouKnowAsyncTask();
        task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,openTDbBaseUrl);

    }

    public void getCustomerNames(String openTDbBaseUrl)
    {


        URL url = null;
        try {
            url = new URL(openTDbBaseUrl);
        } catch (MalformedURLException e) {

        }


        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = Utils.makeHttpRequest(url);
        }
        catch (IOException e) {
        }


        extractFeatureFromJson(jsonResponse);
    }

    private void extractFeatureFromJson(String jsonObjectURL) {



        if (TextUtils.isEmpty(jsonObjectURL)) {
            return ;
        }


        try
        {

            JSONObject rootJsonObject = new JSONObject(jsonObjectURL);

            JSONArray arrayOfTransactions = rootJsonObject.getJSONArray("transactions");


            JSONArray arrayOfSaleHistory = rootJsonObject.getJSONArray("kpis");
            todaySaleCount = arrayOfSaleHistory.getJSONObject(0).getString("value");
            todaySaleSum = arrayOfSaleHistory.getJSONObject(1).getString("value");
            totalSaleCount = arrayOfSaleHistory.getJSONObject(2).getString("value");
            totalSaleSum = arrayOfSaleHistory.getJSONObject(3).getString("value");


            mArrayListAllTransactions.clear();
            for (int i = 0; i < arrayOfTransactions.length(); i++) {
                JSONObject eachTransaction = arrayOfTransactions.getJSONObject(i);

                try
                {
                    String customerName = eachTransaction.getJSONObject("customer").getString("name");
                    String customerPhoneNo = eachTransaction.getJSONObject("customer").getString("mobileNumber");

                    String amountPaid = eachTransaction.getString("amountPaid");
                    String dateOfTransaction = eachTransaction.getString("dateTransaction");

                    Transaction transaction = new Transaction(customerName,customerPhoneNo,dateOfTransaction,amountPaid);
                    mArrayListAllTransactions.add(transaction);
                }
                catch (Exception ex){}

            }

        }
        catch (Exception e)
        {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
    }

    public  void displayData()
    {
        mTextViewTodaySaleCount.setText(todaySaleCount);
          mTextViewTodaySaleSum.setText(todaySaleSum);
        mTextViewTotalSaleCount.setText(totalSaleCount);
          mTextViewTotalSaleSum.setText(totalSaleSum);

        if (transactionAdapter != null)
            transactionAdapter.clear();

        transactionAdapter = new AllTransactionAdapter(this, mArrayListAllTransactions);
        mListViewAllTransactions.setAdapter(transactionAdapter);

    }

    private class doYouKnowAsyncTask extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... urls) {
            if(urls.length <1 || urls[0] == null)
                return null ;

            getCustomerNames(urls[0]);
            return "success";
        }

        @Override
        protected void onPostExecute(String string)
        {
            if(string == null)
                return;
            else
                displayData();
        }
    }
}
