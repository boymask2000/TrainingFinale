package com.boymask.training.math;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.boymask.training.R;

public class MathParametersActivity extends AppCompatActivity {
    private Button start;
    private Button exit;
    private RadioButton op1;
    private RadioButton op2;
    private RadioButton cifre1;
    private RadioButton cifre2;
    private RadioButton op3;
    private TextView delay;
    private MathParameters params = new MathParameters();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_parameters);

        cifre1 = (RadioButton) findViewById(R.id.radio_cifre1);
        cifre2 = (RadioButton) findViewById(R.id.radio_cifre2);

        start = (Button) findViewById(R.id.button_start);
        delay = (TextView) findViewById(R.id.delay);
        delay.setText("5");
        exit = (Button) findViewById(R.id.button_exit);
        op1 = (RadioButton) findViewById(R.id.op1);
        op2 = (RadioButton) findViewById(R.id.op2);
        op3 = (RadioButton) findViewById(R.id.op3);

        setButtons();

    }

    private void setButtons() {


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cifre1.isChecked()) params.setNumDigits(1);
                if (cifre2.isChecked()) params.setNumDigits(2);
                if (op1.isChecked()) params.setNumOps(1);
                if (op2.isChecked()) params.setNumOps(2);
                if (op3.isChecked()) params.setNumOps(3);

                int nSecs = 1;
                try {
                    nSecs = Integer.parseInt("" + delay.getText());
                } catch (NumberFormatException e) {

                }
                params.setDelay(nSecs);
                Intent intent = new Intent(MathParametersActivity.this, MathActivity.class);


                Bundle b = new Bundle();
                b.putSerializable("params", params);
                intent.putExtras(b);

                startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}