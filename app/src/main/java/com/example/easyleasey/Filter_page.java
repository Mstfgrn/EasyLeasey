package com.example.easyleasey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.easyleasey.Fragments.Home_fragment;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Filter_page extends AppCompatActivity  implements View.OnClickListener {


    EditText alistarihi,iadetarihi,alissaati,iadesaati,konum;
    String selectedLong,selectedLat;
    private int mYear, mMonth, mDay, mHour, mMinute;
    final DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private Button  btn_filter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filterpage);


        alistarihi=(EditText)findViewById(R.id.alistarihi);
        iadetarihi=(EditText)findViewById(R.id.iadetarihi);
        alissaati=(EditText)findViewById(R.id.alissaati);
        iadesaati=(EditText)findViewById(R.id.iadesaati);
        konum= (EditText)findViewById(R.id.konum);
        btn_filter = (Button)findViewById(R.id.btn_filter);

        alistarihi.setOnClickListener(this);
        iadetarihi.setOnClickListener(this);
        alissaati.setOnClickListener(this);
        iadesaati.setOnClickListener(this);
        konum.setOnClickListener(this);
        btn_filter.setOnClickListener(this);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null){
            String text = (String) bundle.get("address");
            konum.setText(text);
        }


    }

    @Override
    public void onClick(View view) {
        if (view==btn_filter){

                Long aliszamani =  converttime(alistarihi.getText().toString() + " " + alissaati.getText().toString());
                Long iadezamani = converttime(iadetarihi.getText().toString() + " " + iadesaati.getText().toString());
                System.out.println(aliszamani);
                System.out.println(iadezamani);
                Intent intent = new Intent(Filter_page.this, Market_page.class);
                intent.putExtra("aliszamani",aliszamani);
                intent.putExtra("iadezamani",iadezamani);
                intent.putExtra("Enlem",selectedLat);
                intent.putExtra("Boylam",selectedLong);
                startActivity(intent);
                finish();

        }
        if (view == alistarihi) {
            try {
                Date date = dateFormat.parse(iadetarihi.getText().toString());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                alistarihi.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis()-1000);
                datePickerDialog.show();
            } catch (ParseException e) {
                e.printStackTrace();
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                alistarihi.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        }

        if (view == alissaati) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            String time;
                            if (hourOfDay/10 < 1 ){time = "0" + hourOfDay + ":";}
                            else{time = hourOfDay + ":"; }
                            if (minute/10 < 1 ){time += "0" + minute;}
                            else{time += minute;}
                            alissaati.setText(time);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }

        if (view == iadetarihi) {
            try {
                Date date = dateFormat.parse(alistarihi.getText().toString());
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                iadetarihi.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                datePickerDialog.show();

            } catch (ParseException e) {
                e.printStackTrace();
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                iadetarihi.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }

        }

        if (view == iadesaati) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {
                            String time;
                            if (hourOfDay/10 < 1 ){time = "0" + hourOfDay + ":";}
                            else{time = hourOfDay + ":"; }
                            if (minute/10 < 1 ){time += "0" + minute;}
                            else{time += minute;}
                            iadesaati.setText(time);
                        }
                    }, mHour, mMinute, true);
            timePickerDialog.show();
        }

        if(view == konum ){
            Intent intent= new Intent(Filter_page.this,Location_page.class);
            startActivityForResult(intent,1);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(data.getStringExtra("address"));
        if (requestCode==1){
            if (resultCode==RESULT_OK){
                konum.setText(data.getStringExtra("address"));
                selectedLat = data.getStringExtra("latitude");
                selectedLong = (data.getStringExtra("longitude"));
            }
        }

    }

    public Long converttime(String strDate){
        try {
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            // you can change format of date
            Date date = formatter.parse(strDate);
            Timestamp timeStampDate = new Timestamp(date.getTime());
            return timeStampDate.getTime();
        } catch (ParseException e) {
            System.out.println("Exception :" + e);
            return null;
        }
    }

}
