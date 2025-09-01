package com.example.calculator;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class MainActivity extends AppCompatActivity {
    TextView inputScreen, resultScreen;
    private String input ="", output = "", newOutput;
    private ImageButton button0, button1, button2, button3, button4, button5, button6, button7, button8, button9,
            button_plus, button_subtract, button_multiply, button_divide, button_delete, button_Ac, button_equal, button_dot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputScreen = findViewById(R.id.inputScreen);
        resultScreen = findViewById(R.id.resultScreen);

        button0 = findViewById(R.id.btn_zero_0);
        button1 = findViewById(R.id.btn_one);
        button2 = findViewById(R.id.btn_two);
        button3 = findViewById(R.id.btn_three);
        button4 = findViewById(R.id.btn_four);
        button5 = findViewById(R.id.btn_five);
        button6 = findViewById(R.id.btn_six);
        button7 = findViewById(R.id.btn_seven);
        button8 = findViewById(R.id.btn_eight);
        button9 = findViewById(R.id.btn_nine);
        button_plus = findViewById(R.id.btn_plus);
        button_subtract = findViewById(R.id.btn_subtract);
        button_multiply = findViewById(R.id.btn_multiplication);
        button_divide = findViewById(R.id.btn_division);
        button_delete = findViewById(R.id.btn_delete);
        button_Ac = findViewById(R.id.btn_Ac_0);
        button_equal = findViewById(R.id.btn_equal_0);
        button_dot = findViewById(R.id.btn_dot_0);
    }

    public void onButtonClicked(View view) {
        ImageButton button = (ImageButton) view;
        String data = button.getContentDescription().toString();

        switch (data) {
            case "C":
                input = "";
                output = "";
                resultScreen.setText("");
                break;

            case "delete":
                if (!input.isEmpty()) {
                    input = input.substring(0, input.length() - 1);
                }
                break;

            case "=":
                advancedSolve();
                break;

            default:
                if ("+-*/".contains(data)) { //Nhâp vào chưa các toán tử này thì chạy lệnh bên dưới
                    if (input.isEmpty()) { //Chỉ cho đầu vào là số âm
                        if (data.equals("-")) {
                            input = "-";
                        }
                    } else { //Không cho nhập 2 toán tử liền kề nhau
                        char last = input.charAt(input.length() - 1); //Ex: "123+" thì last = "+"
                        if ("+-*/".indexOf(last) == -1) { //Tìm kí tự của nó trong chuỗi nếu nó không là +-*/ thì được + vào
                            input += data; //
                        }
                    }
                } else {
                    input += data;
                }
                break;
        }
        inputScreen.setText(input);
    }
    public void advancedSolve() {
        if (input == null || input.isEmpty()) return;

        try {
            java.util.List<Double> nums = new java.util.ArrayList<>();
            java.util.List<Character> ops = new java.util.ArrayList<>();
            StringBuilder number = new StringBuilder();

            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);

                if (Character.isDigit(c) || c == '.') {
                    number.append(c);
                } else {
                    if (c == '-' && (i == 0 || (!Character.isDigit(input.charAt(i - 1)) && input.charAt(i - 1) != '.'))) {
                        number.append(c);
                    } else {
                        if (number.length() > 0) {
                            nums.add(Double.parseDouble(number.toString()));
                            number.setLength(0);
                        }
                        ops.add(c);
                    }
                }
            }
            if (number.length() > 0) {
                nums.add(Double.parseDouble(number.toString()));
            }

            int i = 0;
            while (i < ops.size()) {
                char op = ops.get(i);
                if (op == '*' || op == '/') {
                    double a = nums.get(i);
                    double b = nums.get(i + 1);
                    double res = (op == '*') ? (a * b) : (a / b);
                    nums.set(i, res);
                    nums.remove(i + 1);
                    ops.remove(i);
                } else {
                    i++;
                }
            }

            double result = nums.get(0);
            for (i = 0; i < ops.size(); i++) {
                char op = ops.get(i);
                double b = nums.get(i + 1);
                if (op == '+') {
                    result += b;
                } else if (op == '-') {
                    result -= b;
                }
            }

            output = Double.toString(result);
            input = output;
            resultScreen.setText("=" + output);

        } catch (Exception e) {
            resultScreen.setText("Error");
        }
    }

}