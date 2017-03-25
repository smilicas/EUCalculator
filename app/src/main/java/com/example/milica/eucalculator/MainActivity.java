package com.example.milica.eucalculator;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import adapters.VisitsAdaper;
import database.DAOVisit;
import database.Visit;
import views.IndicatorView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DAOVisit daoVisit;
    public static final int currentdbVersion = 1;
    ListView listView;
    VisitsAdaper adapter;
    List<Visit> visits;
    List<Visit> countableVisits;

    public static final String KEY_COUNTRY = "visit_country";
    public static final String KEY_DESC = "visit_desc";
    public static final String KEY_ID = "visit_id";
    public static final String KEY_ENTERDATE = "enter_date";
    public static final String KEY_EXITDATE = "exit_date";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, AddVisitActivity.class));
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //my code from here

        daoVisit = new DAOVisit(this);
        /*
        if (currentdbVersion == daoVisit.dbVersion()) {
            Calendar calendar = new GregorianCalendar(2017, 01, 14);
            Calendar calendar1 = new GregorianCalendar(2017, 02, 14);
            long datumUlaska = calendar.getTimeInMillis();
            long datumIzlaska = calendar1.getTimeInMillis();
            daoVisit.addVisit(new Visit("0", datumUlaska, datumIzlaska, "Nemacka", "Poseta tetki"));
            daoVisit.addVisit(new Visit("1", datumUlaska, datumIzlaska, "Italija", "Poseta tetki"));
            daoVisit.addVisit(new Visit("2", datumUlaska, datumIzlaska, "Spanija", "Poseta tetki"));
            daoVisit.addVisit(new Visit("3", datumUlaska, datumIzlaska, "Francuska", "Poseta tetki"));
        }
        */
        visits = daoVisit.getAllVisits();

        updateRemainingDays();
        

        adapter = new VisitsAdaper(this, visits);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Visit visit = adapter.getItem(position);
                Intent intent = new Intent(MainActivity.this, VisitDetailsActivity.class);
                intent.putExtra(KEY_COUNTRY, visit.getCountry());
                intent.putExtra(KEY_DESC, visit.getDesc());
                intent.putExtra(KEY_ID, visit.getVisitId());
                intent.putExtra(KEY_ENTERDATE, visit.getEntryDate());
                intent.putExtra(KEY_EXITDATE, visit.getExitDate());
                startActivity(intent);
            }
        });

        /*
        for (Visit v : visits){
            Date date = new Date(v.getEntryDate());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            String entryDate = sdf.format(date);
            Date date1 = new Date(v.getExitDate());
            SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
            String exitDate = sdf.format(date1);

            String log = "Id: " + v.getVisitId() + " datum ulaska: " + entryDate + " datum izlaska: " + exitDate +
                    " Zemlja: " + v.getCountry() + " Razlog posete: " + v.getCountry();
            Log.d("TEST| ", log);
        }

        Button btnPlus = (Button) findViewById(R.id.btn_plus);
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView counter = (TextView) findViewById(R.id.txt_noOfDays);
                int number = Integer.decode((String)counter.getText());
                number++;
                counter.setText(String.valueOf(number));
                updateCircle(number);
            }
        });
        Button btnMinus = (Button) findViewById(R.id.btn_minus);
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView counter = (TextView) findViewById(R.id.txt_noOfDays);
                int number = Integer.decode((String)counter.getText());
                number--;
                counter.setText(String.valueOf(number));
                updateCircle(number);
            }
        });
*/
    }

    public void updateCircle(int number){
        View view = findViewById(R.id.circle);
        GradientDrawable circle = (GradientDrawable) view.getBackground();
        if(number > 30){
            circle.setColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        else{
            circle.setColor(getResources().getColor(R.color.colorAccent));
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        visits = daoVisit.getAllVisits();

        adapter = new VisitsAdaper(this, visits);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(adapter);
        updateRemainingDays();
    }

    private long calculteRemainingDays(){
        long days = 90;
        countableVisits = daoVisit.getCountableVisits();
        for (Visit v : countableVisits) {
            long enter = v.getEntryDate();
            long exit = v.getExitDate();
            long daysBetween = TimeUnit.MILLISECONDS.toDays(Math.abs(exit - enter)) + 1;
            days -= daysBetween;
        }
        return days;
    }

    private void updateRemainingDays(){

        TextView numOrRemainDays = (TextView) findViewById(R.id.txt_noOfDays);
        long days = calculteRemainingDays();
        numOrRemainDays.setText(String.valueOf(days));
    }

}
