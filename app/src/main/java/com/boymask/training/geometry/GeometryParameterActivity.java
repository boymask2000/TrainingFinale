package com.boymask.training.geometry;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.boymask.training.R;



public class GeometryParameterActivity extends AppCompatActivity {

    private Button start;
    private Button exit;
    private RadioButton op1;
    private RadioButton op2;
    private RadioButton parole_yes;
    private RadioButton parole_no;
    private RadioButton op3;
    private TextView delay;
    private GeometryParameters params = new GeometryParameters();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geometry_parameter);

        parole_yes = (RadioButton) findViewById(R.id.parole_yes);
        parole_no = (RadioButton) findViewById(R.id.parole_no);

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
                if (parole_yes.isChecked()) params.setWithWords(true);
                if (parole_no.isChecked()) params.setWithWords(false);
                if (op1.isChecked()) params.setNumFigures(1);
                if (op2.isChecked()) params.setNumFigures(2);
                if (op3.isChecked()) params.setNumFigures(3);

                int nSecs = 1;
                try {
                    nSecs = Integer.parseInt("" + delay.getText());
                } catch (NumberFormatException e) {

                }
                params.setDelay(nSecs);
                Intent intent = new Intent(GeometryParameterActivity.this, GeometryActivity.class);


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