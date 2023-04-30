package com.example.dattingapp.observerpattern;

import com.google.firebase.database.ValueEventListener;

public interface MessageObserver {
    void update(Object object);

}
