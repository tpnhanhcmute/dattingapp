package com.example.dattingapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattingapp.Adapter.HobbyAdapter;
import com.example.dattingapp.Adapter.MatchAdapter;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

public class FillProfileActivity  extends AppCompatActivity {
    EditText editTextDateOfBirth;
    Spinner  spnGender;
    int year, month, day;
    List<String> listGender;
    LinearLayout linearLayoutEditImage;
    ImageView imageViewBack;
    RecyclerView rcHobby;
    HobbyAdapter hobbyAdapter;
    List<String> listHobby;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_profile);
        Mapping();
        SetListener();
        SetData();
        BindingData();


    }

    private void BindingData() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,
                listGender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnGender.setAdapter(adapter);
        spnGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                onItemSelectedHandler(parent, view, position, id);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        listHobby = new ArrayList<String>();
        listHobby.add("Nuôi chó");
        listHobby.add("Nuôi mèo");
        listHobby.add("Cung nhân mã");
        listHobby.add("Cung con bò");
        listHobby.add("Nuôi con mèo con");

        hobbyAdapter = new HobbyAdapter(this, listHobby);
        rcHobby.setHasFixedSize(true);
//        RecyclerView.LayoutManager layoutManager =
//                new GridLayoutManager(this,2);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        rcHobby.setLayoutManager(layoutManager);
        rcHobby.setAdapter(hobbyAdapter);

        //recyclerViewMatch.scrollToPosition(messageList.size()-1);
        hobbyAdapter.notifyDataSetChanged();

    }

    private void onItemSelectedHandler(AdapterView<?> parent, View view, int position, long id) {
        Adapter adapter = parent.getAdapter();
        String gender = (String)adapter.getItem(position);
        Toast.makeText(getApplicationContext(), "Selected Employee: " +gender ,Toast.LENGTH_SHORT).show();

    }

    private void SetData() {
        listGender = new ArrayList<>();
        listGender.add("Male");
        listGender.add("Female");
        listGender.add("None");
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
            editTextDateOfBirth.setText(date);
        }
    };

    private  void Mapping(){
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        spnGender = findViewById(R.id.spinnerGender);
        linearLayoutEditImage = findViewById(R.id.LinearLayoutEditPic);
        imageViewBack = findViewById(R.id.imageViewBack);
        rcHobby = findViewById(R.id.rcHobby);
    }
    private  void SetListener(){
        editTextDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        editTextDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        linearLayoutEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FillProfileActivity.this,AddPictureActivity.class);
                startActivity(intent);
            }
        });
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FillProfileActivity.super.onBackPressed();
            }
        });
    }
}
