package com.example.user.vkclient.models;

import java.util.ArrayList;

public class FriendsModel {

    private Friends response;

    public Friends getResponse() {
        return response;
    }

    public class Friends{
        private int count;
        private ArrayList<Friend> items;

        public int getCount() {
            return count;
        }

        public ArrayList<Friend> getItems() {
            return items;
        }

        public class Friend {
            private int id;
            private String first_name;
            private String last_name;
            private String photo;
            int online;

            public int getId() {
                return id;
            }

            public String getFirst_name() {
                return first_name;
            }

            public String getLast_name() {
                return last_name;
            }
        }
    }
}
