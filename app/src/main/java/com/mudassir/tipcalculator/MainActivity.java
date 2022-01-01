package com.mudassir.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat = NumberFormat.getPercentInstance();

    private double bill = 0.0;
    private int share = 0;
    private double percent = 0.15;
    private TextView amountTextView;
    private TextView percentTextView;
    private TextView shareTextView;
    private TextView tipTextView;
    private TextView totalTextView;
    private TextView sharedAmountTextView;

    private TextWatcher amountEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        /**
         * Parses the user input and displays it on the UI
         * @param charSequence the input characters
         * @param i
         * @param i1
         * @param i2
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                bill = Double.parseDouble(charSequence.toString());
                amountTextView.setText(currencyFormat.format(bill));
                calculate();
            }
            catch (NumberFormatException e) {
                amountTextView.setText("");
                bill = 0;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private TextWatcher shareEditTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        /**
         * Parses the share input from the user
         * @param charSequence
         * @param i
         * @param i1
         * @param i2
         */
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            try {
                share = Integer.parseInt(charSequence.toString());
                shareTextView.setText(NumberFormat.getInstance().format(share));
                calculate();
            }
            catch (NumberFormatException e) {
                shareTextView.setText("");
                share = 0;
            }

            calculate();
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    /**
     * Implements the seekbar change listener
     */
    private final SeekBar.OnSeekBarChangeListener seekBarChangeListener =
            new SeekBar.OnSeekBarChangeListener() {
        /**
         * event to handle when progress has changed
         * @param seekBar the Seekbar instance
         * @param i the current progress of the seekbar
         * @param b determines if the user initiated the change
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
            percent = i / 100.0;
            calculate();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {}

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {}
    };

    /**
     * Called when the activity is first created
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references to programmatically manipulated TextViews
        amountTextView = (TextView) findViewById(R.id.amountTextView);
        percentTextView = (TextView) findViewById(R.id.percentTextView);
        shareTextView = (TextView) findViewById(R.id.shareTextView);
        tipTextView = (TextView) findViewById(R.id.tipTextView);
        totalTextView = (TextView) findViewById(R.id.totalTextView);
        sharedAmountTextView = (TextView) findViewById(R.id.sharedAmountTextView);

        // set amountEditText's TextWatcher
        EditText amountEditText = (EditText) findViewById(R.id.amountEditText);
        amountEditText.addTextChangedListener(amountEditTextWatcher);

        // set shareEditText's TextWatcher
        EditText shareEditText = (EditText) findViewById(R.id.shareEditText);
        shareEditText.addTextChangedListener(shareEditTextWatcher);

        // set percentSeekBar's onSeekBarChangeListener
        SeekBar percentSeekBar = (SeekBar) findViewById(R.id.percentSeekBar);
        percentSeekBar.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    private void calculate() {
        percentTextView.setText(percentFormat.format(percent));

        double tip = bill * percent;
        double total = bill + tip;
        double sharedAmount = 0;

        if (share != 0) sharedAmount = total / share;

        sharedAmountTextView.setText(currencyFormat.format(sharedAmount));
        tipTextView.setText(currencyFormat.format(tip));
        totalTextView.setText(currencyFormat.format(total));
    }
}