package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import java.util.Stack;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    private TextView display;
    private Switch switchDegRad;

    private double currentValue = 0;
    private String currentOperation = "";
    private boolean isTrigonometric = false;
    private boolean isDegrees = false;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        display = findViewById(R.id.display);
        switchDegRad = findViewById(R.id.switchDegRad);

        setButtonListeners();

        switchDegRad.setOnCheckedChangeListener((buttonView, isChecked) -> {
            isDegrees = isChecked;
            switchDegRad.setText(isChecked ? "grados" : "radianes");
        });
    }

    private void setButtonListeners() {
        findViewById(R.id.button0).setOnClickListener(this::appendNumber);
        findViewById(R.id.button1).setOnClickListener(this::appendNumber);
        findViewById(R.id.button2).setOnClickListener(this::appendNumber);
        findViewById(R.id.button3).setOnClickListener(this::appendNumber);
        findViewById(R.id.button4).setOnClickListener(this::appendNumber);
        findViewById(R.id.button5).setOnClickListener(this::appendNumber);
        findViewById(R.id.button6).setOnClickListener(this::appendNumber);
        findViewById(R.id.button7).setOnClickListener(this::appendNumber);
        findViewById(R.id.button8).setOnClickListener(this::appendNumber);
        findViewById(R.id.button9).setOnClickListener(this::appendNumber);

        findViewById(R.id.buttonAdd).setOnClickListener(this::setOperation);
        findViewById(R.id.buttonSub).setOnClickListener(this::setOperation);
        findViewById(R.id.buttonMul).setOnClickListener(this::setOperation);
        findViewById(R.id.buttonDiv).setOnClickListener(this::setOperation);

        findViewById(R.id.buttonSin).setOnClickListener(v -> setTrigOperation("sin"));
        findViewById(R.id.buttonCos).setOnClickListener(v -> setTrigOperation("cos"));
        findViewById(R.id.buttonTan).setOnClickListener(v -> setTrigOperation("tan"));

        findViewById(R.id.buttonEqual).setOnClickListener(v -> executeOperation());
        findViewById(R.id.buttonClear).setOnClickListener(v -> clear());
    }

    private void appendNumber(View view) {
        Button button = (Button) view;
        display.append(button.getText().toString());
    }

    private void setOperation(View view) {
        if (!display.getText().toString().isEmpty()) {
            currentValue = Double.parseDouble(display.getText().toString());
            display.setText("");
            Button button = (Button) view;
            currentOperation = button.getText().toString();
        }
    }

    private void setTrigOperation(String trig) {
        if (!display.getText().toString().isEmpty()) {
            currentValue = Double.parseDouble(display.getText().toString());
            isTrigonometric = true;
            currentOperation = trig;
            executeOperation();
        }
    }

    private void executeOperation() {
        double result = currentValue;

        if (isTrigonometric) {
            if (isDegrees) {
                currentValue = Math.toRadians(currentValue);
            }
            switch (currentOperation) {
                case "sin":
                    result = Math.sin(currentValue);
                    break;
                case "cos":
                    result = Math.cos(currentValue);
                    break;
                case "tan":
                    result = Math.tan(currentValue);
                    break;
            }
            isTrigonometric = false;
        } else {
            if (!display.getText().toString().isEmpty()) {
                double newValue = Double.parseDouble(display.getText().toString());
                switch (currentOperation) {
                    case "+":
                        result += newValue;
                        break;
                    case "-":
                        result -= newValue;
                        break;
                    case "*":
                        result *= newValue;
                        break;
                    case "/":
                        if (newValue != 0) {
                            result /= newValue;
                        }

                        break;
                }
            }
        }

        display.setText(String.valueOf(result));
        currentValue = result;
        currentOperation = "";
    }

    private void clear() {
        display.setText("");
        currentValue = 0;
        currentOperation = "";
        isTrigonometric = false;
    }
}
