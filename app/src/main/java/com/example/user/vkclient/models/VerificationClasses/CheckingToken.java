package com.example.user.vkclient.models.VerificationClasses;

import com.google.gson.annotations.SerializedName;

public class CheckingToken {
    @SerializedName("response")
    private Response response = new Response();

    public Response getResponse(){
        return response;
    }

    public class Response{
        @SerializedName("success")
        private int success;

        public int getSuccess() {
            return success;
        }
    }
}
