package com.boymask.training;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.boymask.training.colors.ColorsParameterActivity;
import com.boymask.training.geometry.GeometryParameterActivity;
import com.boymask.training.math.MathActivity;
import com.boymask.training.math.MathParametersActivity;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity {

    private Button matematica;
    private Button colori;
    private Button geo;
    private Button exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

   /*     if(checkDate()){
            alertDialog();
        }*/

        matematica = (Button) findViewById(R.id.matematica);
        colori = (Button) findViewById(R.id.colori);
        geo = (Button) findViewById(R.id.geometry);
        exit = (Button) findViewById(R.id.button_exit);

        setButtons();
    }
    private void alertDialog() {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                finish();
            }
        });
        dialog.setMessage("Questa app è scaduta");
     //   dialog.setTitle("Dialog Box");
        dialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        Toast.makeText(getApplicationContext(),"Yes is clicked",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
        dialog.setNegativeButton("cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"cancel is clicked",Toast.LENGTH_LONG).show();
                finish();
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }

    private boolean checkDate() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, 2021);
        cal.set(Calendar.MONTH, Calendar.APRIL);
        cal.set(Calendar.DAY_OF_MONTH, 30);
        Date lastDate = cal.getTime();

        Date now=new Date();

        return now.after(lastDate);
    }

    private void setButtons() {
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        matematica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MathParametersActivity.class);
                startActivity(intent);
            }
        });
        colori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ColorsParameterActivity.class);
                startActivity(intent);
            }
        });
        geo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GeometryParameterActivity.class);
                startActivity(intent);
            }
        });

    }
}