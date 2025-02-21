package com.sri.mycalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import org.mozilla.javascript.*;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView resultTv, solutionTv;
    MaterialButton buttonC, buttonBrackOpen, buttonBrackClose;
    MaterialButton buttonDivide, buttonMultiply, buttonPlus, buttonMinus, buttonEquals;
    MaterialButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9;
    MaterialButton buttonAC, buttonDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTv = findViewById(R.id.result_tv);
        solutionTv = findViewById(R.id.solution_tv);

        buttonC = assignId(R.id.button_c);
        buttonBrackOpen = assignId(R.id.button_open_bracket);
        buttonBrackClose = assignId(R.id.button_close_bracket);
        buttonDivide = assignId(R.id.button_divide);
        buttonMultiply = assignId(R.id.button_multiply);
        buttonPlus = assignId(R.id.button_plus);
        buttonMinus = assignId(R.id.button_minus);
        buttonEquals = assignId(R.id.button_equals);
        button0 = assignId(R.id.button_0);
        button1 = assignId(R.id.button_1);
        button2 = assignId(R.id.button_2);
        button3 = assignId(R.id.button_3);
        button4 = assignId(R.id.button_4);
        button5 = assignId(R.id.button_5);
        button6 = assignId(R.id.button_6);
        button7 = assignId(R.id.button_7);
        button8 = assignId(R.id.button_8);
        button9 = assignId(R.id.button_9);
        buttonAC = assignId(R.id.button_ac);
        buttonDot = assignId(R.id.button_dot);
    }

    private MaterialButton assignId(int id) {
        MaterialButton btn = findViewById(id);
        if (btn != null) {
            btn.setOnClickListener(this);
        }
        return btn;
    }

    @Override
    public void onClick(View view) {
        MaterialButton button = (MaterialButton) view;
        String buttonText = button.getText().toString();
        String dataToCalculate = solutionTv.getText().toString();
        // If "AC" is clicked, reset everything
        if (buttonText.equals("AC")) {
            solutionTv.setText("0");
            resultTv.setText("");  // Clear result only when AC is pressed
            return;
        }

        // If "C" is clicked, remove the last character
        if (buttonText.equals("C")) {
            if (!dataToCalculate.equals("0") && dataToCalculate.length() > 0) {
                dataToCalculate = dataToCalculate.substring(0, dataToCalculate.length() - 1);
                if (dataToCalculate.isEmpty()) {
                    dataToCalculate = "0"; // Ensure it doesn’t become blank
                }
            }
            solutionTv.setText(dataToCalculate);
            return;
        }

        // If "=" is clicked, display final result in resultTv
        if (buttonText.equals("=")) {
            String finalResult = getResult(dataToCalculate);
            if (!finalResult.equals("Err")) {
                resultTv.setText(finalResult);  // Show result only here!
            } else {
                resultTv.setText("Error");
            }
            return;
        }

        // Prevent "0" from being appended incorrectly
        if (dataToCalculate.equals("0")) {
            dataToCalculate = "";
        }

        dataToCalculate += buttonText;
        solutionTv.setText(dataToCalculate);
    }




    private String getResult(String data) {
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initStandardObjects();
            String finalResult = context.evaluateString(scriptable, data, "Javascript", 1, null).toString();
            if (finalResult.endsWith(".0")) {
                finalResult = finalResult.replace(".0", "");
            }
            return finalResult;
        } catch (Exception e) {
            return "Err";
        } finally {
            Context.exit();
        }
    }
}
