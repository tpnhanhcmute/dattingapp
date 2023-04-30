package com.example.dattingapp.observerpattern;

import com.google.firebase.database.ValueEventListener;

public abstract class MessageObserverImpl implements  MessageObserver{
    private ValueEventListener valueEventListener;
    public abstract void update(Object object);

    public void setValueEventListener(ValueEventListener valueEventListener) {
        this.valueEventListener = valueEventListener;
    }
    public  ValueEventListener getValueEventListener(){
        return valueEventListener;
    }
}
