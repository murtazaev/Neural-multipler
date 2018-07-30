package com.example.user.vkclient.interfaces;

public interface CheckCallback {
    void isSuccessful(int i);
    interface CommCheck{
        void isSuccessfulEdit(int position, String text);
        void isSuccessfulDelete(int position);
    }

}
