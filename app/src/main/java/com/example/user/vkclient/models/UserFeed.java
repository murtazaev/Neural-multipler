package com.example.user.vkclient.models;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class UserFeed {
    private ArrayList<LastCommentModel> lastComm;
    private VKFeedResponse vkFeedResponse;


    public ArrayList<LastCommentModel> getLastComm() {
        return lastComm;
    }

    public void setLastComm(ArrayList<LastCommentModel> lastComm) {
        this.lastComm = lastComm;
    }

    public VKFeedResponse getVkFeedResponse() {
        return vkFeedResponse;
    }

    public void setVkFeedResponse(VKFeedResponse vkFeedResponse) {
        this.vkFeedResponse = vkFeedResponse;
    }


    public void parse(JSONArray array) {
        lastComm = new ArrayList<>();
        try {
            lastComm = new ArrayList<>(array.length());
            for (int i = 0; i < array.length(); i++) {
                LastCommentModel lastCommentModel = new LastCommentModel();
                try {
                    JSONArray comment = new JSONArray(array.get(i).toString());
                    lastCommentModel.setPhotoURL(comment.get(0).toString());
                    lastCommentModel.setName(comment.get(1).toString());
                    lastCommentModel.setText(comment.get(2).toString());
                    lastComm.add(lastCommentModel);
                } catch (JSONException q) {
                    lastCommentModel.setText(array.get(i).toString());
                    lastComm.add(lastCommentModel);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}
