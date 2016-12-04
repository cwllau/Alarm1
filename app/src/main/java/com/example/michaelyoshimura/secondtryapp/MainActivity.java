package com.example.michaelyoshimura.secondtryapp;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {        //
    private static Button removeC, addC, btnviewAll, map;

    EditText classNum, mapNum;
    DatabaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);
        getMap();
        OnClickButtonListener();
        viewAll();
        DeleteData();

    }

    public void OnClickButtonListener() {

        addC = (Button) findViewById(R.id.button2);

        addC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent("com.example.michaelyoshimura.secondtryapp.SecondActivity");
                startActivity(intent);
            }
        });
    }


    public void viewAll() {
        btnviewAll = (Button)findViewById(R.id.button3);
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if(res.getCount() == 0) {
                            // show message
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("Id :"+ res.getString(0)+"\n");
                            buffer.append("Day :"+ res.getString(1)+"\n");
                            buffer.append("Building :"+ res.getString(2)+"\n");
                            buffer.append("Time :"+ res.getString(3)+"\n\n");
                        }

                        // Show all data
                        showMessage("Class Schedule",buffer.toString());
                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void DeleteData() {
        removeC = (Button) findViewById(R.id.button);
        classNum = (EditText)findViewById(R.id.editText);
        removeC.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(classNum.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void getMap(){
        map = (Button)findViewById(R.id.button4);
        mapNum = (EditText)findViewById(R.id.editText2);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

}
