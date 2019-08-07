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
import com.example.pub.Models.Detail;
import com.example.pub.Presenters.CalculatorPresenter;
import com.example.pub.R;
import com.example.pub.views.CalculateView;


public class CalculActivity extends MvpAppCompatActivity implements CalculateView, View.OnClickListener, View.OnLongClickListener {
    private Button IncomeButton;
    private Button ConsumeButton;
    private TextView ResultTextView;
    private TextView ResultTextTooView;
    private ImageView PlusMinusView;
    private boolean flag = false;
    @InjectPresenter
    CalculatorPresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> cancelOnClick());
        initialization();
        ConsumeButton.callOnClick();
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

    @Override
    public void addDetail(Detail detail) {
        Intent intent = new Intent();
        intent.putExtra("newdetail", detail);
        setResult(RESULT_OK, intent);
        finish();
    }

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

    public void initialization() {
        Button numZero = findViewById(R.id.button_zero);
        numZero.setOnClickListener(this);

        Button numOne = findViewById(R.id.button_one);
        numOne.setOnClickListener(this);

        Button numTwo = findViewById(R.id.button_two);
        numTwo.setOnClickListener(this);

        Button numThree = findViewById(R.id.button_three);
        numThree.setOnClickListener(this);

        Button numFour = findViewById(R.id.button_four);
        numFour.setOnClickListener(this);

        Button numFive = findViewById(R.id.button_five);
        numFive.setOnClickListener(this);

        Button numSix = findViewById(R.id.button_six);
        numSix.setOnClickListener(this);

        Button numSeven = findViewById(R.id.button_seven);
        numSeven.setOnClickListener(this);

        Button numEight = findViewById(R.id.button_eight);
        numEight.setOnClickListener(this);

        Button numNine = findViewById(R.id.button_nine);
        numNine.setOnClickListener(this);

        Button dotButton = findViewById(R.id.button_dot);
        dotButton.setOnClickListener(this);


        ImageButton CleanButton = findViewById(R.id.button_clean);
        CleanButton.setOnClickListener(this);
        CleanButton.setOnLongClickListener(this);

        Button EqualButton = findViewById(R.id.button_equal);
        EqualButton.setOnClickListener(this);

        Button AddButton = findViewById(R.id.button_add);
        AddButton.setOnClickListener(this);

        Button MinusButton = findViewById(R.id.button_substraction);
        MinusButton.setOnClickListener(this);

        Button DivButton = findViewById(R.id.button_division);
        DivButton.setOnClickListener(this);


        Button MultiButton = findViewById(R.id.button_multi);
        MultiButton.setOnClickListener(this);


        Button categorButton = findViewById(R.id.button_type);
        categorButton.setOnClickListener(v -> presenter.describeDetail(ResultTextView.getText().toString()));
        IncomeButton = findViewById(R.id.income_button);
        IncomeButton.setOnClickListener(v -> presenter.incomeOnClick());
        ConsumeButton = findViewById(R.id.consume_button);
        ConsumeButton.setOnClickListener(v -> presenter.consumeOnClick());
        ResultTextView = findViewById(R.id.autofit);
        ResultTextView.setAutoSizeTextTypeUniformWithConfiguration(40, 80, 3, TypedValue.COMPLEX_UNIT_SP);
        ResultTextTooView = findViewById(R.id.result_text_too);
        ResultTextTooView.setText("");
        PlusMinusView = findViewById(R.id.plus_minus);
    }

    @Override
    public void onClick(View v) {
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

    @Override
    public boolean onLongClick(View v) {
        presenter.clean_long_Click();
        return true;
    }
}
