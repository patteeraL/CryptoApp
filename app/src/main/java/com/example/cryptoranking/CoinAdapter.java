package com.example.cryptoranking;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class CoinAdapter extends RecyclerView.Adapter<CoinAdapter.CoinViewHolder> {

    List<Coin> coinList;
    Context context;

    public CoinAdapter(Context context, List<Coin> coins){
         this.context = context;
         coinList = coins;
    }
    @NonNull
    @Override
    public CoinViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_coin, parent, false);
        return new CoinViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoinViewHolder holder, int position) {
        Coin coin = coinList.get(position);
        holder.title.setText(coin.getSymbol());
        holder.name.setText(coin.getName());
        holder.price.setText(coin.getPrice());

        // Load the image using Glide
        Glide.with(context)
                .load(coin.getIconUrl())
                .placeholder(R.drawable.ic_launcher_foreground) // Add a placeholder image
                .error(R.drawable.ic_launcher_foreground) // Add an error image if loading fails
                .into(holder.icon);

        // Just to check if data binding works
        Log.d("CoinAdapter", "Binding coin: " + coin.getName());
    }

    @Override
    public int getItemCount() {
        return coinList.size();
    }

    public static class CoinViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView title, name, price;

        public CoinViewHolder(@NonNull View itemView) {
            super(itemView);

            icon = itemView.findViewById(R.id.coinIcon);
            title = itemView.findViewById(R.id.coinSymbol);
            name = itemView.findViewById(R.id.coinName);
            price = itemView.findViewById(R.id.coinPrice);

        }
    }
    }

