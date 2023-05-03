package com.example.dattingapp.utils;
import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.List;

import com.example.dattingapp.Models.MessageContent;
import com.example.dattingapp.observerpattern.MessageObserver;
import com.example.dattingapp.observerpattern.MessageObserverImpl;
import com.example.dattingapp.observerpattern.Observer;
import com.example.dattingapp.observerpattern.Subject;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
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
    public void RegisterOnMessage(String path, MessageObserverImpl messageObserver){

        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference messageRef = database.getReference("message/"+path);

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageObserver.update(snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        messageObserver.setValueEventListener(valueEventListener);
        messageRef.addValueEventListener(valueEventListener);
    }

    public void UnRegisterOnMessage(String path, MessageObserverImpl messageObserver){
        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference messageRef = database.getReference("message/"+path);
        messageRef.removeEventListener(messageObserver.getValueEventListener());
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void RegisterNewMassage(List<String> listMessageIDs,MessageObserver observer ){
        FirebaseApp.initializeApp(this);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        listMessageIDs.forEach(x->{
            DatabaseReference messageRef = database.getReference("message/"+x);
            messageRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    List<DataSnapshot> children = (List<DataSnapshot>) snapshot.getChildren();
                   if(children.size()> 1){
                       DataSnapshot lastChild = children.get(children.size() - 2);
                       MessageContent messageContent =  lastChild.getValue(MessageContent.class);
                       observer.notify(messageContent);
                   }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });



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
