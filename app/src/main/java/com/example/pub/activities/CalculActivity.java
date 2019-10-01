package com.example.pub.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatActivity;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.example.pub.R;
import com.example.pub.models.Detail;
import com.example.pub.presenters.CalculatorPresenter;
import com.example.pub.views.CalculateView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CalculActivity extends MvpAppCompatActivity implements CalculateView, View.OnLongClickListener {
    @BindView(R.id.income_button) Button IncomeButton;
    @BindView(R.id.consume_button) Button ConsumeButton;
    @BindView(R.id.autofit) TextView ResultTextView;
    @BindView(R.id.result_text_too) TextView ResultTextTooView;
    @BindView(R.id.plus_minus) ImageView PlusMinusView;
    @BindView(R.id.button_clean) ImageButton CleanButton;
    @BindView(R.id.toolbar) Toolbar toolbar;
    private boolean flag = false;
    @InjectPresenter
    CalculatorPresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_detail);
        ButterKnife.bind(this);
        consumeClick();
        CleanButton.setOnLongClickListener(this);
        ResultTextView.setAutoSizeTextTypeUniformWithConfiguration(40, 80, 3, TypedValue.COMPLEX_UNIT_SP);
        ResultTextTooView.setText("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> cancelOnClick());
    }

    private void cancelOnClick(){
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_detail) {
            presenter.addMenuClick(ResultTextView.getText().toString(), flag);
        }
        return super.onOptionsItemSelected(item);
    }

    //позволяет добавить доход(расход)
    @Override
    public void showPlus() {
        flag = true;
        PlusMinusView.setImageDrawable(getDrawable(R.drawable.plus_icon));
        IncomeButton.setEnabled(false);
        ConsumeButton.setEnabled(true);
        IncomeButton.setBackgroundColor(getColor(R.color.disable_button));
        ConsumeButton.setBackgroundColor(getColor(R.color.enabled_button));
    }

    @Override
    public void showMinus() {
        flag = false;
        PlusMinusView.setImageDrawable(getDrawable(R.drawable.minus_icon));
        IncomeButton.setEnabled(true);
        ConsumeButton.setEnabled(false);
        IncomeButton.setBackgroundColor(getColor(R.color.enabled_button));
        ConsumeButton.setBackgroundColor(getColor(R.color.disable_button));
    }

    //добавляет в список деталей
    @Override
    public void addDetail(Detail detail) {
        Intent intent = new Intent();
        intent.putExtra("newdetail", detail);
        setResult(RESULT_OK, intent);
        finish();
    }

    //окрывает экран с описанием детали
    @Override
    public void openDescription(Detail detail) {
        Intent i = new Intent(this, DescribeActivity.class);
        i.putExtra("describedetail", detail);
        startActivityForResult(i, 5);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            presenter.makeResult(((Detail) data.getSerializableExtra("newdetail")));
        }
    }

    @Override
    public void sendResult(Detail d) {
        Intent i = new Intent();
        i.putExtra("newdetail", d);
        setResult(RESULT_OK, i);
        finish();
    }

    @Override
    public void setSign(String s) {
        ResultTextView.setText(s);
    }

    @Override
    public void showOperator(String s) {
        ResultTextTooView.setText(s);
    }

    @OnClick({R.id.button_one, R.id.button_two, R.id.button_three, R.id.button_four, R.id.button_five, R.id.button_six,
            R.id.button_seven, R.id.button_eight, R.id.button_nine, R.id.button_zero,R.id.button_dot, R.id.button_equal,
            R.id.button_division, R.id.button_multi, R.id.button_add, R.id.button_substraction, R.id.button_clean})
    void keyBoard(View v) {
        switch (v.getId()) {
            case R.id.button_zero:
                presenter.zero_Click();
                break;
            case R.id.button_one:
                presenter.one_Click();
                break;
            case R.id.button_two:
                presenter.two_Click();
                break;
            case R.id.button_three:
                presenter.three_Click();
                break;
            case R.id.button_four:
                presenter.four_Click();
                break;
            case R.id.button_five:
                presenter.five_Click();
                break;
            case R.id.button_six:
                presenter.six_Click();
                break;
            case R.id.button_seven:
                presenter.seven_Click();
                break;
            case R.id.button_eight:
                presenter.eight_Click();
                break;
            case R.id.button_nine:
                presenter.nine_Click();
                break;
            case R.id.button_dot:
                presenter.dot_Click();
                break;
            case R.id.button_equal:
                presenter.equal_Click();
                break;
            case R.id.button_division:
                presenter.division_Click(ResultTextView.getText().toString());
                break;
            case R.id.button_multi:
                presenter.multi_Click(ResultTextView.getText().toString());
                break;
            case R.id.button_add:
                presenter.add_Click(ResultTextView.getText().toString());
                break;
            case R.id.button_substraction:
                presenter.sub_Click(ResultTextView.getText().toString());
                break;
            case R.id.button_clean:
                presenter.clean_short_Click(ResultTextView.getText().toString());
                break;
        }
    }
    @OnClick(R.id.income_button)
    void incomeClick(){
        presenter.incomeOnClick();
    }

    @OnClick({R.id.consume_button})
    void consumeClick(){
        presenter.consumeOnClick();
    }

    @OnClick(R.id.button_type)
    void categoryClick(){
        presenter.describeDetail(ResultTextView.getText().toString());
    }

    @Override
    public boolean onLongClick(View v) {
        presenter.clean_long_Click();
        return true;
    }
}
