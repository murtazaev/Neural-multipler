package com.example.user.vkclient.models.VerificationClasses;

public class AccessLongPull {

    private AccessLongPullResponse response;

    public AccessLongPullResponse getResponse() {
        return response;
    }


    public class AccessLongPullResponse {
        private String key;
        private String server;
        private int ts;

        public int getTs() {
            return ts;
        }

        public String getKey() {
            return key;
        }

        public String getServer() {
            return server;
        }
    }
}
