package com.example.michaelyoshimura.secondtryapp;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import static java.lang.Integer.parseInt;

public class SecondActivity extends AppCompatActivity {

    String[] SPINNERLIST = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    String[] SPINNERLIST2 = {"Holmes Hall", "Bilger", "Keller Hall", "POST", "MSB"};
    public int count = 0;
    //private static EditText day, building; //time
    private static Button submit;

    private AlarmManager myAlarmManager;
    private PendingIntent pending_intent;
    TimePicker time_picker;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);




        LoginButton();
    }

    public void LoginButton(){
        myDb = new DatabaseHelper(this);
        //day = (EditText)findViewById(R.id.dayEditText);
        //building = (EditText)findViewById(R.id.buildEditText);
        //time = (EditText)findViewById(R.id.timeEditText);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SPINNERLIST);
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, SPINNERLIST2);
        final MaterialBetterSpinner betterSpinner = (MaterialBetterSpinner)findViewById(R.id.android_material_design_spinner);
        final MaterialBetterSpinner betterSpinner2 = (MaterialBetterSpinner)findViewById(R.id.android_material_design_spinner2);
        betterSpinner.setAdapter(arrayAdapter);
        betterSpinner2.setAdapter(arrayAdapter2);

        final Calendar calendar = Calendar.getInstance();
        time_picker = (TimePicker)findViewById(R.id.timePicker);
        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String time;
                        calendar.set(Calendar.HOUR_OF_DAY, time_picker.getHour());
                        calendar.set(Calendar.MINUTE, time_picker.getMinute());

                        int hour = time_picker.getHour();
                        int minute = time_picker.getMinute();
                        myAlarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
                        Intent alarmIntent = new Intent(SecondActivity.this, Alarm_Reciever.class); //context.this
                        pending_intent = PendingIntent.getBroadcast(SecondActivity.this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);



                        time = (String.valueOf(hour) + String.valueOf(minute));

                        boolean isInserted = myDb.insertData(betterSpinner.getText().toString(),
                                betterSpinner2.getText().toString(),time);


                        if (isInserted == true){
                            Toast.makeText(SecondActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            myAlarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() - 900000, pending_intent);                                      //parseInt(time.getText().toString())

                            finish();
                        }
                        else {
                            Toast.makeText(SecondActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                        }


                    }

                }

        );

    }
}




