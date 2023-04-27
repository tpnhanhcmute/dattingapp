package com.example.dattingapp.utils;
import android.app.Application;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

import com.example.dattingapp.observerpattern.Observer;
import com.example.dattingapp.observerpattern.Subject;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MessageManager extends Application implements Subject {

    //--------------------Observer------------------------------//
    List<Observer> messageObserverList;
    //-------------------------Database -------------------------//
    private FirebaseDatabase database;
    private DatabaseReference messageRef;
    private static MessageManager _instance;
    public static MessageManager  getInstance(){
        if(_instance ==null){
            _instance = new MessageManager();
        }
        return _instance;
    }
    private  MessageManager(){
        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        messageObserverList = new ArrayList<>();
        messageRef= database.getReference("message");

        messageRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                notifyObservers(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public FirebaseDatabase getDatabase(){
        return database;
    }
    public DatabaseReference getMessageRef(){
        return messageRef;
    }

    @Override
    public void register(Observer obj) {
        if(obj == null) throw new NullPointerException("Null Observer");
        synchronized (messageObserverList) {
            if(!messageObserverList.contains(obj)){
                messageObserverList.add(obj);
                obj.setSubject(this);
            }
        }
    }

    @Override
    public void unregister(Observer obj) {
        synchronized (messageObserverList) {
            messageObserverList.remove(obj);
        }
    }

    @Override
    public void notifyObservers(Object object) {
        List<Observer> observersLocal = null;
        //synchronization is used to make sure any observer registered after message is received is not notified
        synchronized (messageObserverList) {
            if (messageObserverList == null)
                return;
            observersLocal = new ArrayList<>(this.messageObserverList);
        }
        for (Observer obj : observersLocal) {
            obj.update(object);
        }
    }
    @Override
    public Object getUpdate(Observer obj) {
        return null;
    }
}
