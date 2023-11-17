package com.example.crypto;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class FilterFragment extends Fragment {
    private View view;
    RadioGroup sortBy;
    RadioButton marcketcap, price, volume;
    Button apply;
    String sortValue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_filter, container, false);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Filter");
        sortBy = view.findViewById(R.id.sortby);
        price = view.findViewById(R.id.price);
        marcketcap = view.findViewById(R.id.marcketCap);
        volume = view.findViewById(R.id.volume);
        apply = view.findViewById(R.id.apply);

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Exchange_Fragment exchange_fragment = new Exchange_Fragment();
                Bundle bundle = new Bundle();

//                    sending data
                if (sortValue.isEmpty()) {
                    sortValue = "MarcketCap";
                    bundle.putString("sorrtValue", sortValue);
                } else {
                    bundle.putString("sorrtValue", sortValue);
                }
                exchange_fragment.setArguments(bundle);
                //changing trans
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container, exchange_fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        sortBy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.price) {
                    sortValue = price.getText().toString();

                } else if (i == R.id.marcketCap) {
                    sortValue = marcketcap.getText().toString();
                } else if (i == R.id.volume) {
                    sortValue = volume.getText().toString();
                }

            }
        });
        return view;
    }
}