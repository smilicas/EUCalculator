package com.example.milica.eucalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import converters.ConvertDate;
import database.DAOVisit;

import static com.example.milica.eucalculator.MainActivity.KEY_COUNTRY;
import static com.example.milica.eucalculator.MainActivity.KEY_DESC;
import static com.example.milica.eucalculator.MainActivity.KEY_ENTERDATE;
import static com.example.milica.eucalculator.MainActivity.KEY_EXITDATE;
import static com.example.milica.eucalculator.MainActivity.KEY_ID;

public class VisitDetailsActivity extends AppCompatActivity {

    private DAOVisit daoVisit;
    private String id = "";
    String country = "";
    String desc = "";
    long enterDate;
    long exitDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visit_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        daoVisit = new DAOVisit(this);

        Intent intent = getIntent();
        if (intent != null) {
            country = intent.getStringExtra(KEY_COUNTRY);
            desc = intent.getStringExtra(KEY_DESC);
            id = intent.getStringExtra(KEY_ID);
            // 0 denotes default value, if you do not put anything in the activity that called it.
            enterDate = intent.getLongExtra(KEY_ENTERDATE, 0);
            exitDate = intent.getLongExtra(KEY_EXITDATE, 0);
        }
        final TextView txtCountry = (TextView) findViewById(R.id.out_country);
        final TextView txtDesc = (TextView) findViewById(R.id.textDesc);
        final TextView txtEnterDate = (TextView)findViewById(R.id.txtEnterDate);
        final TextView txtExitDate = (TextView)findViewById(R.id.txtExitDate);

        txtCountry.setText(country);
        txtDesc.setText(desc);
        txtEnterDate.setText(ConvertDate.castDate(enterDate));
        txtExitDate.setText(ConvertDate.castDate(exitDate));

        Button btnDelete = (Button) findViewById(R.id.btn_delete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                daoVisit.deleteVisit(id);
                finish();
            }
        });

        Button btnUpdate = (Button) findViewById(R.id.btn_update);
        if (btnUpdate != null) {
            btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(VisitDetailsActivity.this, AddVisitActivity.class);
                    intent.putExtra(KEY_COUNTRY, txtCountry.getText());
                    intent.putExtra(KEY_DESC, txtDesc.getText());
                    intent.putExtra(KEY_ID, id);
                    intent.putExtra(KEY_ENTERDATE, txtEnterDate.getText());
                    intent.putExtra(KEY_EXITDATE, txtExitDate.getText());
                    startActivity(intent);
                }
            });
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        //visits = daoVisit.getAllVisits();

    }

}
