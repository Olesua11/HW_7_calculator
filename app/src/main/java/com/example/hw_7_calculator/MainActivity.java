package com.example.hw_7_calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private double doubleFirstOperand, doubleSecondOperand, doubleResult;
    private Boolean isOperationClick;
    private String lastOperation;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.text_view);
    }

    public void onNumberClick(View view) {
        if (view.getId() == R.id.btn_clear) {
            textView.setText("0");
            doubleFirstOperand = 0;
            doubleSecondOperand = 0;
            lastOperation = null;
        } else if (view instanceof MaterialButton) {
            String text = ((MaterialButton) view).getText().toString();

            if (text.equals(".") && textView.getText().toString().contains(".")) {
                return;
            }

            if (text.equals(".") && !isOperationClick) {
                textView.append(text);
            } else {
                String currentText = textView.getText().toString();
                if (currentText.equals("0") || isOperationClick) {
                    textView.setText(text);
                } else {
                    textView.append(text);
                }
            }
        }

        isOperationClick = false;
    }

    public void onOperationClick(View view) {
        if (view.getId() == R.id.btn_plus || view.getId() == R.id.btn_minus ||
                view.getId() == R.id.btn_multiply || view.getId() == R.id.btn_divide) {
            if (lastOperation != null) {
                calculateResult();
            }

            String operandText = textView.getText().toString();
            if (operandText.contains(".")) {
                doubleFirstOperand = Double.parseDouble(operandText);
            } else {
                doubleFirstOperand = Double.valueOf(operandText);
            }

            lastOperation = ((MaterialButton) view).getText().toString();
        } else if (view.getId() == R.id.btn_equal) {
            if (lastOperation != null) {
                calculateResult();
                lastOperation = null;
            }
        }

        isOperationClick = true;
    }

    private void calculateResult() {
        String operandText = textView.getText().toString();
        if (operandText.contains(".")) {
            doubleSecondOperand = Double.parseDouble(operandText);
        } else {
            doubleSecondOperand = Double.valueOf(operandText);
        }

        switch (lastOperation) {
            case "+":
                doubleResult = doubleFirstOperand + doubleSecondOperand;
                break;
            case "-":
                doubleResult = doubleFirstOperand - doubleSecondOperand;
                break;
            case "*":
                doubleResult = doubleFirstOperand * doubleSecondOperand;
                break;
            case "/":
                if (doubleSecondOperand != 0) {
                    doubleResult = doubleFirstOperand / doubleSecondOperand;
                } else {
                    textView.setText("Error");
                    return;
                }
                break;
        }

        if (doubleResult % 1 == 0) {
            textView.setText(String.valueOf((int) doubleResult));
        } else {
            textView.setText(String.valueOf(doubleResult));
        }
    }
}