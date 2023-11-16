package com.example.crypto;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Exchange_Fragment extends Fragment {
    private View exchangeView;
    LinearLayout Crypto_view_layout, nft_view_layout;
    TextView crypto_view, nft_view;
    RecyclerView crypto_list;
    SearchView search_bar;
    Toolbar toolbar;
    private ArrayList<Crypto_Model> crypto_modelArrayList;
    private Crypto_Adapter crypto_adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        exchangeView = inflater.inflate(R.layout.fragment_exchange_, container, false);
        toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Exchange");
        Crypto_view_layout = exchangeView.findViewById(R.id.crypto_view_layout);
        nft_view_layout = exchangeView.findViewById(R.id.nft_view_layout);
        crypto_view = exchangeView.findViewById(R.id.crypto_view);
        nft_view = exchangeView.findViewById(R.id.nft_view);
        crypto_list = exchangeView.findViewById(R.id.crypto_list);
        search_bar = exchangeView.findViewById(R.id.search_bar);
        crypto_modelArrayList = new ArrayList<>();
        crypto_adapter = new Crypto_Adapter(crypto_modelArrayList, getContext());


        nft_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Crypto_view_layout.setVisibility(View.GONE);
                crypto_view.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
                nft_view_layout.setVisibility(View.VISIBLE);
                nft_view.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
            }
        });
        crypto_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nft_view_layout.setVisibility(View.GONE);
                nft_view.setTextColor(ContextCompat.getColor(getContext(), R.color.grey));
                crypto_view.setTextColor(ContextCompat.getColor(getContext(), R.color.black));
                Crypto_view_layout.setVisibility(View.VISIBLE);

            }
        });

        search_bar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                filterCurrencies(newText.toString());
                return true;
            }

        });

        crypto_list.setLayoutManager(new LinearLayoutManager(getContext()));
        crypto_list.setAdapter(crypto_adapter);
        getCurrencyData();


        return exchangeView;
    }

    private void filterCurrencies(String currency) {
        ArrayList<Crypto_Model> filteredList = new ArrayList<>();
        for (Crypto_Model item : crypto_modelArrayList) {
            if (item.getName().toLowerCase().contains(currency.toLowerCase())) {
                filteredList.add(item);
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(getContext(), "No Currency found", Toast.LENGTH_SHORT).show();
            } else {
                crypto_adapter.filterList(filteredList);
            }
        }
    }

    private void getCurrencyData() {
        RequestQueue requestQueue1 = Volley.newRequestQueue(getContext());

        String url = "https://pro-api.coinmarketcap.com/v1/cryptocurrency/listings/latest" + "?CMC_PRO_API_KEY=" + "91af7cca-6f37-4000-a7b9-bff76ef87c65";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    crypto_modelArrayList.clear();
                    JSONArray dataArray = response.getJSONArray("data");

                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject currencyData = dataArray.getJSONObject(i);
                        Crypto_Model crypto_model = new Crypto_Model();
                        crypto_model.setName(currencyData.getString("name"));
                        int id = currencyData.getInt("id");

                        crypto_model.setSymbol(currencyData.getString("symbol"));

////                        // Check if "quote" object and "USD" object exist
                        if (currencyData.has("quote") && currencyData.getJSONObject("quote").has("USD")) {
                            crypto_model.setMarketCap(currencyData.getJSONObject("quote").getJSONObject("USD").getDouble("market_cap"));
                            crypto_model.setPrice(currencyData.getJSONObject("quote").getJSONObject("USD").getDouble("price"));
                            crypto_model.setPercent_change_1h(currencyData.getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_1h"));
                            crypto_model.setPercent_change_7d(currencyData.getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_7d"));
                            crypto_model.setPercent_change_24h(currencyData.getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_24h"));
                            crypto_model.setPercent_change_30d(currencyData.getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_30d"));
                            crypto_model.setPercent_change_60d(currencyData.getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_60d"));
                            crypto_model.setPercent_change_90d(currencyData.getJSONObject("quote").getJSONObject("USD").getDouble("percent_change_90d"));


                        } else {
                            // Handle the case where "USD" data is missing
                            crypto_model.setMarketCap(0.00);
                        }
                        //fetch logo
                        String url1 = "https://pro-api.coinmarketcap.com/v2/cryptocurrency/info" + "?id=" + id + "&CMC_PRO_API_KEY=" + "91af7cca-6f37-4000-a7b9-bff76ef87c65";
                        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url1, null, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String image_url = response.getJSONObject("data").getJSONObject("" + id).getString("logo");
                                    if (image_url != null)
                                        crypto_model.setIconUrl(image_url);
                                } catch (JSONException e) {

                                }

                                crypto_adapter.notifyDataSetChanged();

                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }

                        });
//

                        requestQueue1.add(jsonObjectRequest1);
                        //Initial sory by marcket cap
//        sortDataByMarketCap();
                        sortDataByPrice();
                        // Update the adapter with the sorted dat
                        crypto_modelArrayList.add(crypto_model);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                crypto_adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Fail to gt the data" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
//        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        requestQueue1.add(jsonObjectRequest);


    }

    private void sortDataByPrice() {
        Collections.sort(crypto_modelArrayList, new Comparator<Crypto_Model>() {
            @Override
            public int compare(Crypto_Model crypto_model, Crypto_Model t1) {
                return Double.compare(t1.getPrice(), crypto_model.getPrice());
            }
        });
    }

    private void sortDataByMarketCap() {
        Collections.sort(crypto_modelArrayList, new Comparator<Crypto_Model>() {
            @Override
            public int compare(Crypto_Model crypto_model, Crypto_Model t1) {
                try {
                    return Double.compare(t1.getMarketCap(), crypto_model.getMarketCap());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return 0;
                }
            }
        });
    }
}