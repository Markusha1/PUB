package com.example.pub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.pub.Models.Budget;
import com.example.pub.Presenters.AddBudgetPresenter;
import com.example.pub.R;
import com.example.pub.Views.AddBudgetView;


public class NewBudgetActivity extends MvpAppCompatActivity implements AddBudgetView {
    private EditText TitleEditText;
    private EditText MoneyEditText;
    private String Title = "";
    private String Money = "";
    @InjectPresenter
    AddBudgetPresenter presenter;

    @Override
    public void onCreate(Bundle savedStateInstance){
        super.onCreate(savedStateInstance);
        presenter.initBudget(getIntent());
        setContentView(R.layout.add_budget);
        TitleEditText = findViewById(R.id.name_budget);
        TitleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Title = s.toString();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        MoneyEditText = findViewById(R.id.money_budget);
        MoneyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Money = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        findViewById(R.id.save_button).setOnClickListener(v -> presenter.getBudget(Title, Money));
    }

    @Override
    public void showErrorTittle() {
        TitleEditText.setHint("Введите название");
        TitleEditText.setHintTextColor(getColor(R.color.errorEdit));
    }

    @Override
    public void saveBudget(Budget budget) {
        Intent i = new Intent();
        i.putExtra("budget", budget);
        setResult(RESULT_OK, i);
        finish();

    }

    @Override
    public void setTitle(String title) {
        TitleEditText.setText(title);
    }

    @Override
    public void setMoney(String money) {
        MoneyEditText.setText(money);
    }

}
