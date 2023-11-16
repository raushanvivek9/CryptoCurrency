package com.example.crypto;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Crypto_Adapter extends RecyclerView.Adapter<Crypto_Adapter.ViewHolder> {
    private ArrayList<Crypto_Model> cryptoArrayLists;
    //    private List<List<DataPoint>> graphDataList;
    private Context context;
    private static DecimalFormat df2 = new DecimalFormat("#.##");


    public Crypto_Adapter(ArrayList<Crypto_Model> cryptoArrayLists, Context context) {
        this.cryptoArrayLists = cryptoArrayLists;
        this.context = context;
    }

    public void filterList(ArrayList<Crypto_Model> filteredList) {
        cryptoArrayLists = filteredList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Crypto_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cypto_recycle_view, parent, false);
        return new Crypto_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Crypto_Adapter.ViewHolder holder, int position) {
        // Get the data for the current position
        Crypto_Model crypto_model = cryptoArrayLists.get(position);
        holder.crypto_name.setText(crypto_model.getName());
        holder.crypto_symbol.setText(crypto_model.getSymbol());
        String price = "$" + "" + df2.format(crypto_model.getPrice()) + " USD";
        holder.crypto_price.setText(price);
        String img_link = crypto_model.getIconUrl();
        if (img_link != null) {
            Picasso.get().load(img_link).into(holder.crypto_icon);
        }
        List<DataPoint> dataPoints1 = Arrays.asList(
                new DataPoint(1, crypto_model.getPercent_change_1h()),
                new DataPoint(2, crypto_model.getPercent_change_24h()),
                new DataPoint(3, crypto_model.getPercent_change_7d()),
                new DataPoint(4, crypto_model.getPercent_change_30d()),
                new DataPoint(5, crypto_model.getPercent_change_60d()),
                new DataPoint(5, crypto_model.getPercent_change_90d())
        );
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints1.toArray(new DataPoint[0]));
        series.setDrawDataPoints(false); // Hide data points
        series.setDrawBackground(false); // Disable background


        double percentage_1h = Double.parseDouble(df2.format(crypto_model.getPercent_change_1h()));
        if (percentage_1h > 0.0) {
            holder.crypto_percent.setText("+" + percentage_1h + "%");
            holder.crypto_percent.setTextColor(ContextCompat.getColor(context, R.color.green));
            series.setColor(Color.GREEN);
        } else if (percentage_1h < 0.0) {
            holder.crypto_percent.setText("" + percentage_1h + "%");
            holder.crypto_percent.setTextColor(ContextCompat.getColor(context, R.color.red));
            series.setColor(Color.RED);
        } else {
            holder.crypto_percent.setText("" + percentage_1h + "%");
            holder.crypto_percent.setTextColor(ContextCompat.getColor(context, R.color.black));
        }
        //         Add the series to the graph
        // Customize the grid
        holder.graphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.BOTH);
        // Disable the grid
        holder.graphView.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.NONE);

// Show/hide horizontal labels
        holder.graphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);

// Show/hide vertical labels
        holder.graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);
        holder.graphView.removeAllSeries();
        holder.graphView.addSeries(series);


    }

    @Override
    public int getItemCount() {
        return cryptoArrayLists.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView crypto_name, crypto_symbol, crypto_price, crypto_percent;
        private ImageView crypto_icon;
        private GraphView graphView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            crypto_name = itemView.findViewById(R.id.crypto_name);
            crypto_symbol = itemView.findViewById(R.id.crypto_Symbol);
            crypto_price = itemView.findViewById(R.id.crypto_price);
            crypto_percent = itemView.findViewById(R.id.crypto_percent);
            crypto_icon = itemView.findViewById(R.id.crypto_icon);
            graphView = itemView.findViewById(R.id.graph);
        }
    }

    // Method to update the data set
    public void updateData(ArrayList<Crypto_Model> newData) {
        cryptoArrayLists.clear();
        // Add only the first 20 items
        int count = Math.min(20, newData.size());
        cryptoArrayLists.addAll(newData.subList(0, count));
        notifyDataSetChanged(); // Notify the adapter about the changes
    }
}
