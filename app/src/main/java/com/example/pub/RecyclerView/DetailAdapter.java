package com.example.pub.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pub.Presenters.DetailPresenter;
import com.example.pub.R;
import com.example.pub.views.DetailViewHolder;


public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailHolder> {
    private DetailPresenter presenter;

    public DetailAdapter(DetailPresenter presenter) {
        this.presenter = presenter;
    }


    @NonNull
    @Override
    public DetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.detail_list_item, parent, false);
        return new DetailHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailHolder holder, int position) {
        presenter.onBindHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }


    public class DetailHolder extends RecyclerView.ViewHolder implements View.OnClickListener, DetailViewHolder, View.OnLongClickListener {
        TextView MoneyView;
        TextView DateView;
        ImageView CategoryView;
        TextView CategoryName;

        DetailHolder(View v) {
            super(v);
            CategoryView = v.findViewById(R.id.picture);
            MoneyView = v.findViewById(R.id.money);
            DateView = v.findViewById(R.id.date);
            CategoryName = v.findViewById(R.id.category_name);
            v.setOnClickListener(this);
            v.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            presenter.detailSelected(getAdapterPosition());
        }

        @Override
        public void setMoney(String money) {
            String text = money.concat("\u20BD");
            MoneyView.setText(text);
            if(money.contains("-")) MoneyView.setTextColor(Color.RED);
            else MoneyView.setTextColor(Color.GREEN);
        }

        @Override
        public void setDate(String date) {
            DateView.setText(date);
        }

        @Override
        public void setImage(int id) {
            CategoryView.setImageResource(id);
        }

        @Override
        public void setCategory(String c) {
            CategoryName.setText(c);
        }

        @Override
        public boolean onLongClick(View v) {
            presenter.selectDetail(getAdapterPosition(), v);
            return true;
        }
    }
}

