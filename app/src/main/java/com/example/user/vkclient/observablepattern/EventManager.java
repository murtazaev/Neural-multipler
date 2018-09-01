package com.example.user.vkclient.observablepattern;

import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class EventManager {
    private HashMap<String, Subscribers> stringSubscribersHashMap = new HashMap<>();

    public void subscribe(String key, Subscribers subscribers) {
        stringSubscribersHashMap.put(key, subscribers);
    }

    public void unsubscribe(String key) {
        stringSubscribersHashMap.remove(key);
    }

    public void notifyed(View view) {
        for (Map.Entry<String, Subscribers> stringSubscribersHashMap : stringSubscribersHashMap.entrySet()) {
            Subscribers subscribers = stringSubscribersHashMap.getValue();
            subscribers.accept(view);
        }
    }
}
