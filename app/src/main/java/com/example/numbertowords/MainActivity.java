package com.example.numbertowords;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private Button button, btnClear;
    private TextView textView;
    //Strings at 0th index is unused, for simplicity
    private static String ones[] =
            {"", "One ", "Two ", "Three ", "Four ",
            "Five ", "Six ", "Seven ", "Eight ",
            "Nine ", "Ten ", "Eleven ", "Twelve ",
            "Thirteen ", "Fourteen ", "Fifteen ",
            "Sixteen ", "Seventeen ", "Eighteen ",
            "Nineteen "
            };

    //Strings at 0th and 1st index are not used for making indexing simple
    private static String tens[] =
            {"", "", "Twenty ", "Thirty ", "Forty ",
            "Fifty ", "Sixty ", "Seventy ", "Eighty ",
            "Ninety "
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.etInput);
        button = (Button) findViewById(R.id.btnConvert);
        textView = (TextView) findViewById(R.id.tvResult);
        btnClear = (Button) findViewById(R.id.btnClear);

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                textView.setText("Your Result will be Displayed Here");
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long inputNumber = Long.parseLong(editText.getText().toString());

                convertToWords(inputNumber);
                hideKeyboard(MainActivity.this);
            }
        });


    }

    //n is a one or two digit number
    static String numToWords(int n, String s) {
        String str = "";
        // if n is more than 19, divide it
        if (n > 19) {
            str += tens[n / 10] + ones[n % 10];
        } else {
            str += ones[n];
        }
        // if n is non-zero
        if (n != 0) {
            str += s;
        }
        return str;
    }

    // Function to print a given number in words
    public void convertToWords(Long n) {
        // stores word representation of given number n
        String result = "";

        // handles digits at ten millions and hundred
        // millions places (if any)
        result += numToWords((int) (n / 10000000), "Crore ");

        // handles digits at hundred thousands and one
        // millions places (if any)
        result += numToWords((int) ((n / 100000) % 100), "Lakh ");

        // handles digits at thousands and tens thousands
        // places (if any)
        result += numToWords((int) ((n / 1000) % 100), "Thousand ");

        // handles digit at hundreds places (if any)
        result += numToWords((int) ((n / 100) % 10), "Hundred ");

        if (n > 100 && n % 100 > 0) {
            result += "and ";
        }

        // handles digits at ones and tens places (if any)
        result += numToWords((int) (n % 100), "");

        textView.setText(result);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}



















