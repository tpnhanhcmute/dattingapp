package com.example.dattingapp.Activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dattingapp.Adapter.HobbyAdapter;
import com.example.dattingapp.DTO.AuthenticationRequest;
import com.example.dattingapp.DTO.FillProfileRequest;
import com.example.dattingapp.DTO.ResponseModel;
import com.example.dattingapp.Models.User;
import com.example.dattingapp.R;
import com.example.dattingapp.common.RetrofitClient;
import com.example.dattingapp.service.APIService;
import com.example.dattingapp.utils.SharedPreference;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FillProfileActivity  extends AppCompatActivity {

    Spinner  spnGender;
    int year, month, day;
    List<String> listGender;
    LinearLayout linearLayoutEditImage;
    ImageView imageViewBack;
    RecyclerView rcHobby;
    HobbyAdapter hobbyAdapter;
    List<String> listHobby;
    Button continueButton;


    EditText editTextFullName;
    EditText editTextCareer;
    EditText editTextDateOfBirth;
    EditText editTextEmail;
    EditText editTextPhoneNumber;


    //--------------------------------------------Request Fill Profile--------------------------------------------//
    FillProfileRequest fillProfileRequest = new FillProfileRequest();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_profile);
        Mapping();
        SetListener();
        SetData();
        BindingData();

        fillProfileRequest.id = SharedPreference.getInstance(this).GetUser().userID;

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
        hobbyAdapter.notifyDataSetChanged();




        //-----------------------------------------Binding infomation-----------------------------//
        User user = SharedPreference.getInstance(this).GetUser();
        if(!TextUtils.isEmpty(user.fullName)) editTextFullName.setText(user.fullName);
        if(!TextUtils.isEmpty(user.career)) editTextCareer.setText(user.career);
        if(!TextUtils.isEmpty(user.dateOfBirth)) editTextDateOfBirth.setText(user.dateOfBirth);
    }

    private void onItemSelectedHandler(AdapterView<?> parent, View view, int position, long id) {
        Adapter adapter = parent.getAdapter();
        String gender = (String)adapter.getItem(position);
        Toast.makeText(getApplicationContext(), "Selected Gender: " + gender ,Toast.LENGTH_SHORT).show();
//--------------------------------------------Request Fill Profile--------------------------------------------//

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
//--------------------------------------------Request Fill Profile--------------------------------------------/
        }
    };

    private  void Mapping(){
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        spnGender = findViewById(R.id.spinnerGender);
        linearLayoutEditImage = findViewById(R.id.LinearLayoutEditPic);
        imageViewBack = findViewById(R.id.imageViewBack);
        rcHobby = findViewById(R.id.rcHobby);
        continueButton = findViewById(R.id.continueButton);

        editTextFullName = findViewById(R.id.editTextTextName);
        editTextCareer = findViewById(R.id.editTextTextCareer);
        editTextDateOfBirth = findViewById(R.id.editTextDateOfBirth);
        editTextEmail =findViewById(R.id.editTextEmail);
        editTextPhoneNumber = findViewById(R.id.editTextPhone);
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

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfile();
            }
        });
    }

    private void UpdateProfile() {

        String fullName = editTextFullName.getText().toString();
        String career = editTextCareer.getText().toString();
        String dateOfBirth = editTextDateOfBirth.getText().toString();
        String gender = spnGender.getSelectedItem().toString();
        String email = editTextEmail.getText().toString();
        String phoneNumber = editTextPhoneNumber.getText().toString();

        if(TextUtils.isEmpty(fullName)){
            editTextFullName.setError("Pleas enter your full name");
            editTextFullName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(career)){
            editTextCareer.setError("Pleas enter your career");
            editTextCareer.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(dateOfBirth)){
            editTextDateOfBirth.setError("Pleas enter your password");
            editTextDateOfBirth.requestFocus();
            return;
        }

        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        fillProfileRequest.id = SharedPreference.getInstance(FillProfileActivity.this).GetUser().userID;
        fillProfileRequest.user = new User();
        fillProfileRequest.user.hobby = new ArrayList<>(GetHobbySellected());
        fillProfileRequest.user.fullName = fullName;
        fillProfileRequest.user.dateOfBirth = dateOfBirth;
        fillProfileRequest.user.gender = gender;
        fillProfileRequest.user.career = career;

        apiService.update(fillProfileRequest).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.body().isError) {
                    Toast.makeText(getApplicationContext(), "Error: "+ response.body().message,Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), response.body().message,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(FillProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error: "+ t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    public  List<String> GetHobbySellected(){
        //--------------------------------------------Request Fill Profile--------------------------------------------//
        return hobbyAdapter.GetHobbySellected();
    }
}
