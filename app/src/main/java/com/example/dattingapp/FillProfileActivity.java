package com.example.dattingapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class FillProfileActivity  extends AppCompatActivity {
    EditText dateEditText;
    int year, month, day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_profile);
        Mapping();
        SetListener();
    }

    public void showDatePickerDialog(View v) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
        datePickerDialog.show();
    }
    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            // Do something with the date chosen by the user
            String date = day + "/" + (month + 1) + "/" + year;
            dateEditText.setText(date);
        }
    };

    private  void Mapping(){
        dateEditText= findViewById(R.id.editTextDateOfBirth);
    }
    private  void SetListener(){
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        dateEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    showDatePickerDialog(v);
                }
            }
        });
    }
}
