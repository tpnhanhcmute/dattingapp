package com.example.dattingapp.observerpattern;

public interface Observer {
    //method to update the observer, used by subject
    public void update(Object object);
    //attach with subject to observe
    public void setSubject(Subject sub);
}
