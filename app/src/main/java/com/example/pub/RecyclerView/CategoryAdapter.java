package com.example.pub.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pub.Presenters.CategoryPresenter;
import com.example.pub.R;
import com.example.pub.Views.ViewHolderInt;


public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.ImageViewHolder> {
    private CategoryPresenter presenter;

    public CategoryAdapter(CategoryPresenter presenter) {
        this.presenter = presenter;
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        presenter.onBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return presenter.getCount();
    }


    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, ViewHolderInt {
        private TextView mTitle;
        private ImageView mPicture;

        ImageViewHolder(View v) {
            super(v);
            mTitle = v.findViewById(R.id.title);
            mPicture = v.findViewById(R.id.picture);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            presenter.categoryIsSelected(getAdapterPosition());
        }

        @Override
        public void setTitle(String s) {
            mTitle.setText(s);
        }

        @Override
        public void setImage(int id) {
            mPicture.setImageResource(id);
        }
    }
}
