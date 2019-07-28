package com.example.pub.RecyclerView;

import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
        holder.itemView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.delete_edit_menu);
            popupMenu.setOnMenuItemClickListener(item -> {
                Budget budget1 = mBudgets.get(holder.getAdapterPosition());
                switch (item.getItemId()) {
                    case R.id.delete:
                        presenter.deleteBudget(budget1);
                        mBudgets.remove(budget1);
                        notifyItemRemoved(holder.getAdapterPosition());
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
        });
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

    public class BudgetViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView TitleView;
        TextView MoneyView;
        TextView DateView;

        BudgetViewHolder(View v) {
            super(v);
            TitleView = v.findViewById(R.id.title);
            MoneyView = v.findViewById(R.id.cash);
            DateView = v.findViewById(R.id.time);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            presenter.onClickBudget(mBudgets.get(getAdapterPosition()));
        }

    }
}

