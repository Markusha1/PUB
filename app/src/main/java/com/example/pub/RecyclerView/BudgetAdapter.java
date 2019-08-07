package com.example.pub.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pub.Models.Budget;
import com.example.pub.Presenters.BudgetListPresenter;
import com.example.pub.R;

import java.util.List;

import static android.content.ContentValues.TAG;

public class BudgetAdapter extends RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder> {
    private List<Budget> mBudgets;
    private BudgetListPresenter presenter;

    public BudgetAdapter(List<Budget> budgets, BudgetListPresenter presenter) {
        this.presenter = presenter;
        mBudgets = budgets;
    }


    @NonNull
    @Override
    public BudgetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.budget_list_item, parent, false);
        return new BudgetViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BudgetViewHolder holder, int position) {
        Budget budget = mBudgets.get(position);
        holder.TitleView.setText(budget.getBudgetText());
        holder.MoneyView.setText(budget.getBudgetMoney().concat("\u20BD"));
        holder.DateView.setText(budget.getBudgetDate());
    }

    @Override
    public int getItemCount() {
        if (mBudgets == null)
            return 0;
        else return mBudgets.size();
    }

    public void setList(List<Budget> budgets) {
        mBudgets = budgets;
    }

    public class BudgetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView TitleView;
        Button MoneyView;
        TextView DateView;
        View Base;

        BudgetViewHolder(View v) {
            super(v);
            TitleView = v.findViewById(R.id.title);
            MoneyView = v.findViewById(R.id.cash);
            DateView = v.findViewById(R.id.time);
            Base = v.findViewById(R.id.base);
            Base.setOnClickListener(this);
            Base.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            presenter.onClickBudget(mBudgets.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View v) {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.delete_edit_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                Budget budget1 = mBudgets.get(getAdapterPosition());
                switch (item.getItemId()) {
                    case R.id.delete:
                        presenter.deleteBudget(budget1);
                        mBudgets.remove(budget1);
                        notifyItemRemoved(getAdapterPosition());
                        return true;
                    case R.id.edit:
                        Log.d(TAG, "onBindViewHolder: изменить ");
                        presenter.editBudget(budget1);
                        return true;
                    default:
                        return false;
                }
            });
            popupMenu.show();
            return true;
        }
    }
}

