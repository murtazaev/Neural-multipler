package com.example.user.vkclient.models;

import android.provider.ContactsContract;

import java.util.ArrayList;

public class UserProfile {
    private ArrayList<ProfileInfo> response;

    public ArrayList<ProfileInfo> getResponse() {
        return response;
    }

    public class ProfileInfo {
        private int id;
        private String first_name;
        private String last_name;
        private int sex;
        String nickname;
        String domain;
        String screen_name;
        String bdate;
        private City city;
        Country country;
        int timezone;
        String photo_100;
        String photo_max;
        String photo_400_orig;
        private String photo_max_orig;
        String photo_id;
        int has_photo;
        int has_mobile;
        int is_friend;
        int friend_status;
        int online;
        int wall_comments;
        int can_post;
        int can_see_all_posts;
        int can_see_audio;
        int can_write_private_message;
        int can_send_friend_request;
        String mobile_phone;
        String home_phone;
        String site;
        private int followers_count;
        private String status;
        private LastSeen last_seen;
        private CropPhoto crop_photo;



        public int getId() {
            return id;
        }

        public String getFirst_name() {
            return first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public String getPhoto_max_orig() {
            return photo_max_orig;
        }

        public CropPhoto getCrop_photo() {
            return crop_photo;
        }

        public LastSeen getLast_seen() {
            return last_seen;
        }

        public int getSex() {
            return sex;
        }

        public String getStatus() {
            return status;
        }

        public int getFollowers_count() {
            return followers_count;
        }

        public City getCity() {
            return city;
        }


        public class City{
            int id;
            private String title;

            public String getTitle() {
                return title;
            }
        }
        public class Country{
            int id;
            private String title;

            public String getTitle() {
                return title;
            }
        }
        public class LastSeen{
            private int time;
            int platform;

            public int getTime() {
                return time;
            }
        }
        public class CropPhoto{

            private Photo photo;

            public Photo getPhoto() {
                return photo;
            }

            public class Photo{
                int id;
                int album_id;
                int owner_id;
                private ArrayList<VKFeedResponse.Response.VKFeedObject.Attachments.VKPhoto.VKArrayPhoto> sizes;
                String text;
                int date;
                int post_id;

                public ArrayList<VKFeedResponse.Response.VKFeedObject.Attachments.VKPhoto.VKArrayPhoto> getSizes() {
                    return sizes;
                }
            }
        }

    }
}
