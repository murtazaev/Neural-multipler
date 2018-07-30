package com.example.user.vkclient.mvp.global;

public class MvpPresenter<T extends MvpView>{
    private T view;

    public void attachView(T view){
        this.view = view;
    }

    public void detachView(){
        view = null;
    }

    protected T getView(){
        return view;
    }
}
