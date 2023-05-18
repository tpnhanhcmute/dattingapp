package com.example.dattingapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.example.dattingapp.DTO.GetImageResponse;
import com.example.dattingapp.DTO.ResponseModel;
import com.example.dattingapp.DTO.UpdateLocationRequest;
import com.example.dattingapp.DTO.UserRequest;
import com.example.dattingapp.Models.User;
import com.example.dattingapp.R;
import com.example.dattingapp.common.RetrofitClient;
import com.example.dattingapp.databinding.ActivityMainBinding;
import com.example.dattingapp.service.APIService;
import com.example.dattingapp.utils.MessageManager;
import com.example.dattingapp.utils.SharedPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 100;
    private static final int REQUEST_ENABLE_GPS = 100;
    LocationManager locationManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());
        GetImage();
        CheckGPS();
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.chat:
                    replaceFragment(new ChatFragment());
                    break;
                case R.id.match:
                    replaceFragment(new MatchFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new SettingFragment());
            }
            return true;
        });

    }


    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment, fragment);
        fragmentTransaction.commit();
    }

    private void GetImage() {

        User user = SharedPreference.getInstance(MainActivity.this).GetUser();
        UserRequest userRequest = new UserRequest();
        userRequest.userID = user.userID;
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        apiService.getImages(userRequest).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (!response.isSuccessful()) return;
                if (response.body().isError) {
                    SharedPreference.getInstance(getApplicationContext()).SetListImage(null);
                    return;
                }
                Type type = new TypeToken<GetImageResponse>() {
                }.getType();
                GetImageResponse getImageResponse = new Gson().fromJson(new Gson().toJson(response.body().data), type);
                SharedPreference.getInstance(getApplicationContext()).SetListImage(getImageResponse.listImage);
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }

    private void CheckGPS() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // GPS không được bật
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setMessage("GPS is off. Do you want to enable GPS?");
            alertDialogBuilder.setPositiveButton("Turn on", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, REQUEST_ENABLE_GPS);
                }
            });
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    finishAffinity();

                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else {
            UpdateLocation();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_GPS) {
            if (resultCode == RESULT_OK) {
                UpdateLocation();
            }
        }
    }

    private void UpdateLocation() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
            CallAPIUpdate();

        }
    }

    private void CallAPIUpdate() {
        @SuppressLint("MissingPermission")
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                UpdateLocation(location);
                locationManager.removeUpdates(this);
                // Đã lấy được vị trí, có thể làm gì đó với nó ở đây
                // ...
            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }

    public  void UpdateLocation(Location location){
        if (location != null) {
            com.example.dattingapp.Models.Location loc = new com.example.dattingapp.Models.Location();
            loc.lat = location.getLatitude();
            loc.lng = location.getLongitude();
            SharedPreference.getInstance(getApplicationContext()).SetLocation(loc);
            UpdateLocationRequest updateLocationRequest = new UpdateLocationRequest();
            updateLocationRequest.userID = SharedPreference.getInstance(MainActivity.this).GetUser().userID;
            updateLocationRequest.lat = loc.lat;
            updateLocationRequest.lng = loc.lng;
            APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
            apiService.updateLocation(updateLocationRequest).enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    if (response.body().isError) {
                        finish();
                        return;
                    }
                    Type type = new TypeToken<com.example.dattingapp.Models.Location>() {
                    }.getType();
                    com.example.dattingapp.Models.Location getLocationResponse = new Gson().fromJson(new Gson().toJson(response.body().data), type);
                    SharedPreference.getInstance(getApplicationContext()).SetLocation(getLocationResponse);
                    replaceFragment(new HomeFragment());
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {

                }
            });
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CallAPIUpdate();
                } else {
                    CheckGPS();
                }
                return;
            }
        }
    }

}