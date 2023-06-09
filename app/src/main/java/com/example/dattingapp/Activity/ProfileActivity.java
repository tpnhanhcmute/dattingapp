package com.example.dattingapp.Activity;

import static java.security.AccessController.getContext;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.dattingapp.Adapter.HobbyAdapter;
import com.example.dattingapp.Adapter.ImagePagerAdapter;
import com.example.dattingapp.DTO.DiscoverModel;
import com.example.dattingapp.DTO.DiscoverResponse;
import com.example.dattingapp.DTO.GetUserResponse;
import com.example.dattingapp.DTO.GetmatcModel;
import com.example.dattingapp.DTO.GetmatchRespone;
import com.example.dattingapp.DTO.ResponseModel;
import com.example.dattingapp.DTO.UserRequest;
import com.example.dattingapp.R;
import com.example.dattingapp.common.Const;
import com.example.dattingapp.common.RetrofitClient;
import com.example.dattingapp.service.APIService;
import com.example.dattingapp.utils.SharedPreference;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    TextView textViewNameAge,textViewDistance,textViewIntroduce;
    ViewPager viewPager;
    private List<String> imageUrl;
    private String userID;

    private ImageButton signinBack;
    private String muserID;
    private RecyclerView rcHobby;
    private  HobbyAdapter hobbyAdapter;
    ImagePagerAdapter adapter;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actitvity_profile);

        Mapping();
        GetmatcModel model = (GetmatcModel) getIntent().getSerializableExtra(Const.USER);
        imageUrl = new ArrayList<>();
        imageUrl.add(model.imageUrl);
        SharedPreference userShare = SharedPreference.getInstance(getApplicationContext());
        userID = model.userID;
        muserID = userShare.GetUser().userID;

        GetProfile(userID,muserID);
        textViewNameAge.setText( model.fullName+ " "+ model.age);
        //textViewIntroduce.setText(model.);
        textViewDistance.setText(model.distance!= null?model.distance.toString(): "NaN");

        adapter = new ImagePagerAdapter(this, imageUrl);
        viewPager.setAdapter(adapter);

        hobbyAdapter = new HobbyAdapter(this, model.hobby,true);
        rcHobby.setHasFixedSize(true);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(this);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        rcHobby.setLayoutManager(layoutManager);
        rcHobby.setAdapter(hobbyAdapter);
        hobbyAdapter.notifyDataSetChanged();
        signinBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Mapping() {
        viewPager = findViewById(R.id.viewPager);
        textViewNameAge = findViewById(R.id.textViewNameAge);
        textViewDistance= findViewById(R.id.textViewDistance);
        textViewIntroduce= findViewById(R.id.textViewIntroduce);
        rcHobby = findViewById(R.id.rcHobby);
        signinBack = findViewById(R.id.signinBack);
    }

    private void GetProfile(String userID,String muserID) {
        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        UserRequest request = new UserRequest();
        request.userID = userID;
        request.muserID = muserID;
        apiService.getProfile(request).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(!response.isSuccessful() || response.body().isError) return;

                Type type = new TypeToken<GetUserResponse>(){}.getType();
                GetUserResponse discoverModel = new Gson().fromJson(new Gson().toJson(response.body().data), type);
                imageUrl.clear();
                imageUrl.addAll(discoverModel.user.imageUrl);
                adapter.notifyDataSetChanged();
                textViewDistance.setText((discoverModel.user.distance).toString());

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
