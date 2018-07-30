package com.example.user.vkclient.models;

import java.util.ArrayList;

public class UserPostModel {

    private UserPosts response;

    public UserPosts getResponse() {
        return response;
    }

    public class UserPosts{
        private int count;
        private ArrayList<Item> items;
        private ArrayList<CommentsModel.Comments.Profiles> profiles;
        private ArrayList<CommentsModel.Comments.Groups> groups;

        public ArrayList<Item> getItems() {
            return items;
        }

        public int getCount() {
            return count;
        }

        public ArrayList<CommentsModel.Comments.Profiles> getProfiles() {
            return profiles;
        }

        public ArrayList<CommentsModel.Comments.Groups> getGroups() {
            return groups;
        }

        public class Item{
            private int id;
            private String type;
            private int source_id;
            private int from_id;
            private int date;
            private int post_id;
            private String post_type;
            private String text;
            private int can_delete;
            private int can_pin;
            private ArrayList<VKFeedResponse.Response.VKFeedObject.Attachments> attachments;
            private VKFeedResponse.Response.VKFeedObject.PostSource post_source;
            private VKFeedResponse.Response.VKFeedObject.Comments comments;
            private VKFeedResponse.Response.VKFeedObject.Likes likes;
            private VKFeedResponse.Response.VKFeedObject.Reposts reposts;
            private VKFeedResponse.Response.VKFeedObject.Views views;

            public int getId() {
                return id;
            }

            public int getSource_id() {
                return source_id;
            }

            public int getFrom_id() {
                return from_id;
            }

            public ArrayList<VKFeedResponse.Response.VKFeedObject.Attachments> getAttachments() {
                return attachments;
            }

            public VKFeedResponse.Response.VKFeedObject.Comments getComments() {
                return comments;
            }

            public VKFeedResponse.Response.VKFeedObject.Likes getLikes() {
                return likes;
            }

            public VKFeedResponse.Response.VKFeedObject.Reposts getReposts() {
                return reposts;
            }

            public VKFeedResponse.Response.VKFeedObject.Views getViews() {
                return views;
            }

            public String getText() {
                return text;
            }

            public int getPost_id() {
                return post_id;
            }

            public int getDate() {
                return date;
            }
        }
    }
}
