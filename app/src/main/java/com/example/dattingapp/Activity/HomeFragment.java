package com.example.dattingapp.Activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DiffUtil;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dattingapp.Adapter.CardStackAdapter;
import com.example.dattingapp.Adapter.CardStackCallback;
import com.example.dattingapp.DTO.DiscorverRequest;
import com.example.dattingapp.DTO.DiscoverModel;
import com.example.dattingapp.DTO.DiscoverResponse;
import com.example.dattingapp.DTO.LoginResponse;
import com.example.dattingapp.DTO.ResponseModel;
import com.example.dattingapp.Models.Filter;
import com.example.dattingapp.Models.ItemModel;
import com.example.dattingapp.R;
import com.example.dattingapp.common.RetrofitClient;
import com.example.dattingapp.service.APIService;
import com.example.dattingapp.utils.SharedPreference;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackListener;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.Direction;
import com.yuyakaido.android.cardstackview.StackFrom;
import com.yuyakaido.android.cardstackview.SwipeableMethod;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private static final String TAG = "MainActivity";
    private CardStackLayoutManager manager;
    public Button btnLike;
    private CardStackAdapter adapter;
    private  List<DiscoverModel> old;
    private List<DiscoverModel> stackItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stackItems = new ArrayList<DiscoverModel>();
        paginate();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        CardStackView cardStackView = view.findViewById(R.id.card_stack_view);
        manager = new CardStackLayoutManager(getContext(), new CardStackListener() {
            @Override
            public void onCardDragging(Direction direction, float ratio) {
                Log.d(TAG, "onCardDragging: d=" + direction.name() + " ratio=" + ratio);
            }

            @Override
            public void onCardSwiped(Direction direction) {
                Log.d(TAG, "onCardSwiped: p=" + manager.getTopPosition() + " d=" + direction);
                if (direction == Direction.Right){
                    //----------------------------------------------Like--------------------------------------------//
                    Toast.makeText(getContext(), "Direction Right", Toast.LENGTH_SHORT).show();
                }
                //----------------------------------------------Dislike--------------------------------------------//
                if (direction == Direction.Left){
                    Toast.makeText(getContext(), "Direction Left", Toast.LENGTH_SHORT).show();
                }
                // Paginating
                if (manager.getTopPosition() == adapter.getItemCount() -1){
                    paginate();
                }
            }

            @Override
            public void onCardRewound() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardCanceled() {
                Log.d(TAG, "onCardRewound: " + manager.getTopPosition());
            }

            @Override
            public void onCardAppeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }

            @Override
            public void onCardDisappeared(View view, int position) {
                TextView tv = view.findViewById(R.id.item_name);
                Log.d(TAG, "onCardAppeared: " + position + ", nama: " + tv.getText());
            }
        });

        manager.setStackFrom(StackFrom.None);
        manager.setVisibleCount(3);
        manager.setTranslationInterval(8.0f);
        manager.setScaleInterval(0.95f);
        manager.setSwipeThreshold(0.3f);
        manager.setMaxDegree(20.0f);
        manager.setDirections(Direction.FREEDOM);
        manager.setCanScrollHorizontal(true);
        manager.setSwipeableMethod(SwipeableMethod.Manual);
        manager.setOverlayInterpolator(new LinearInterpolator());
        adapter = new CardStackAdapter(stackItems,this.getContext());
        cardStackView.setLayoutManager(manager);
        cardStackView.setAdapter(adapter);
        cardStackView.setItemAnimator(new DefaultItemAnimator());
        return view;
    }
    private void paginate() {

        APIService apiService = RetrofitClient.getRetrofit().create(APIService.class);
        DiscorverRequest request = new DiscorverRequest();
        request.userID= SharedPreference.getInstance(getContext()).GetUser().userID;

        Filter filter = SharedPreference.getInstance(getContext()).GetFilter();
        request.distance = filter.distance;
        request.gender = filter.gender;
        request.maxAge = filter.maxAge;
        request.minAge = filter.minAge;

        apiService.getUserDiscorver(request).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()){
                    if(response.body().isError){
                        return;
                    }
                    Type type = new TypeToken<DiscoverResponse>(){}.getType();
                    DiscoverResponse discoverResponse = new Gson().fromJson(new Gson().toJson(response.body().data), type);
                    stackItems = discoverResponse.discorverUser;
                    adapter.AddItem(stackItems);
                    adapter.notifyDataSetChanged();
                    manager.setTopPosition(0);
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
            }
        });
    }
}