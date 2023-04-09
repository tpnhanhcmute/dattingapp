package com.example.dattingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dattingapp.Adapter.MatchAdapter;
import com.example.dattingapp.Models.Match;

import java.util.ArrayList;
import java.util.List;

public class MatchFragment extends Fragment {

    private RecyclerView recyclerViewMatch;
    List<Match> matchList;
    MatchAdapter matchAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_match, container, false);

        Mapping(view);
        SetListener();
        AddData();
        return  view;
    }

    private void AddData() {
        matchList = new ArrayList<>();
        matchList.add(new Match());
        matchList.add(new Match());
        matchList.add(new Match());
        matchList.add(new Match());
        matchList.add(new Match());
        matchList.add(new Match());
        matchList.add(new Match());
        matchList.add(new Match());
        matchList.add(new Match());
        matchList.add(new Match());
        matchList.add(new Match());
        matchList.add(new Match());
        matchList.add(new Match());

        matchAdapter = new MatchAdapter(this.getContext(), matchList);
        recyclerViewMatch.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager =
                new GridLayoutManager(getContext().getApplicationContext(),2);

        recyclerViewMatch.setLayoutManager(layoutManager);
        recyclerViewMatch.setAdapter(matchAdapter);

        //recyclerViewMatch.scrollToPosition(messageList.size()-1);
        matchAdapter.notifyDataSetChanged();
    }

    private void SetListener() {

    }

    private void Mapping(View view) {
        recyclerViewMatch = view.findViewById(R.id.recyclerViewMatch);
    }
}