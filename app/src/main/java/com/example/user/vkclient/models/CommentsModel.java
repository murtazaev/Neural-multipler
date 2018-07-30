package com.example.user.vkclient.models;

import java.util.ArrayList;

import io.reactivex.schedulers.Schedulers;

public class CommentsModel {

    private Comments response;

    public Comments getResponse() {
        return response;
    }

    public void setResponse(Comments response) {
        this.response = response;
    }

    public static class Comments {
        private int count;
        private ArrayList<CommentsModel.Comments.Comment> items;
        private ArrayList<CommentsModel.Comments.Profiles> profiles;
        private ArrayList<CommentsModel.Comments.Groups> groups;

        public int getCount() {
            return count;
        }

        public ArrayList<Comment> getItems() {
            return items;
        }

        public ArrayList<Profiles> getProfiles() {
            return profiles;
        }

        public ArrayList<Groups> getGroups() {
            return groups;
        }


        public static class Comment {
            private int id;
            private int from_id;
            private int date;
            private Like likes;
            private String text;
            private String reply_to_user;
            private String reply_to_comment;
            private String user_avatar;
            private String user_name;

            public String getText() {
                return text;
            }

            public int getId() {
                return id;
            }

            public int getFrom_id() {
                return from_id;
            }

            public void setText(String text) {
                this.text = text;
            }

            public void setId(int id) {
                this.id = id;
            }

            public void setFrom_id(int from_id) {
                this.from_id = from_id;
            }

            public void setUser_avatar(String user_avatar) {
                this.user_avatar = user_avatar;
            }

            public String getUser_avatar() {
                return user_avatar;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public Like getLikes() {
                return likes;
            }

            public void setLikes(Like likes) {
                this.likes = likes;
            }

            public static class Like {
                private int count;
                private int user_likes;
                private int can_like;

                public int getCan_like() {
                    return can_like;
                }

                public int getUser_likes() {
                    return user_likes;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public void setUser_likes(int user_likes) {
                    this.user_likes = user_likes;
                }
            }
        }

        public class Profiles {
            private int id;
            private String first_name;
            private String last_name;
            private int sex;
            private String screen_name;
            private String photo_100;
            private int online;
            private int online_mobile;

            public String getPhoto_100() {
                return photo_100;
            }

            public String getName() {
                return first_name + " " + last_name;
            }

            public int getId() {
                return id;
            }
        }

        public class Groups {
            private int id;
            private String name;
            private String screen_name;
            private int is_closed;
            private String type;
            private int is_admin;
            private int is_member;
            private String photo_100;

            public String getPhoto_100() {
                return photo_100;
            }

            public String getName() {
                return name;
            }

            public int getId() {
                return id;
            }
        }
    }
}
