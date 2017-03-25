package com.example.milica.eucalculator;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import database.DAOVisit;
import database.Visit;

import static com.example.milica.eucalculator.MainActivity.KEY_COUNTRY;
import static com.example.milica.eucalculator.MainActivity.KEY_DESC;
import static com.example.milica.eucalculator.MainActivity.KEY_ENTERDATE;
import static com.example.milica.eucalculator.MainActivity.KEY_EXITDATE;
import static com.example.milica.eucalculator.MainActivity.KEY_ID;

public class AddVisitActivity extends AppCompatActivity implements View.OnClickListener{


    Button btnEntryDate, btnExitDate, btnSave;
    EditText txtCountry, txtEntryDate, txtExitDate, txtDesc;
    private int mYear, mMonth, mDay;
    private DAOVisit daoVisit;
    private String id;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_visit);
        daoVisit = new DAOVisit(this);

        intent = getIntent();

        btnEntryDate = (Button) findViewById(R.id.btn_entry);
        btnExitDate = (Button) findViewById(R.id.btn_exit);
        btnSave = (Button) findViewById(R.id.btn_save);
        txtCountry = (EditText) findViewById(R.id.in_country);
        txtEntryDate = (EditText) findViewById(R.id.in_entry);
        txtExitDate = (EditText) findViewById(R.id.in_exit);
        txtDesc= (EditText) findViewById(R.id.in_desc);

        if (intent != null){
            id = intent.getStringExtra(KEY_ID);
            if(id != null) {
                txtCountry.setText(intent.getStringExtra(KEY_COUNTRY));
                txtEntryDate.setText(intent.getStringExtra(KEY_ENTERDATE));
                txtExitDate.setText(intent.getStringExtra(KEY_EXITDATE));
                txtDesc.setText(intent.getStringExtra(KEY_DESC));
                //id = intent.getStringExtra(KEY_ID);
            }
        }


        btnEntryDate.setOnClickListener(this);
        btnExitDate.setOnClickListener(this);
        btnSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view == btnEntryDate || view == btnExitDate){
            Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            if (view == btnEntryDate){
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener(){
                @Override
                public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                    txtEntryDate.setText(i2 + "-" + (i1+1) + "-" + i);
                }
            }, mYear, mMonth, mDay);
            datePickerDialog.show();
                txtEntryDate.requestFocus();
            }
            else {
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        txtExitDate.setText(i2 + "-" + (i1+1) + "-" + i);
                    }
                }, mYear, mMonth, mDay);
            datePickerDialog.show();
                txtExitDate.requestFocus();
            }
        }

        if (view == btnSave){

            String entryDate = txtEntryDate.getText().toString();
            String exitDate = txtExitDate.getText().toString();
            String country = txtCountry.getText().toString();
            String desc = txtDesc.getText().toString();

            Log.d("Entry: ",entryDate);
            Log.d("Exit: ", exitDate);

            Visit visit = new Visit();


            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            Calendar cal1 = Calendar.getInstance();
            try {
                cal1.setTime(sdf.parse(entryDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            visit.setEntryDate(cal1.getTimeInMillis());
            visit.setCountry(country);
            visit.setDesc(desc);
            //visit.setExitDate(0);

           if (exitDate.length() > 1) {
                Calendar cal2 = Calendar.getInstance();
                try {
                    cal2.setTime(sdf.parse(exitDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                visit.setExitDate(cal2.getTimeInMillis());
            }
            else{
                visit.setExitDate(0);
            }



            if (id != null){
                visit.setVisitId(id);
                daoVisit.updateVisit(visit);
                Intent i=new Intent(this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }

            else {
                daoVisit.addVisit(visit);
            }

            this.finish();
        }

    }
}
