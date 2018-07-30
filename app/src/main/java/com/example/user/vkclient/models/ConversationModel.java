package com.example.user.vkclient.models;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

public class ConversationModel {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public static class Response {

        private ArrayList<Item> items;
        private ArrayList<CommentsModel.Comments.Profiles> profiles;
        private ArrayList<CommentsModel.Comments.Groups> groups;

        public ArrayList<Item> getConversations() {
            return items;
        }

        public ArrayList<CommentsModel.Comments.Profiles> getProfiles() {
            return profiles;
        }

        public ArrayList<CommentsModel.Comments.Groups> getGroups() {
            return groups;
        }

        public static class Item {
            private Conversation conversation;
            private String rPhoto;
            private String rName;

            public Conversation getConversation() {
                return conversation;
            }

            public String getrPhoto() {
                return rPhoto;
            }

            public void setrPhoto(String rPhoto) {
                this.rPhoto = rPhoto;
            }

            public String getrName() {
                return rName;
            }

            public void setrName(String rName) {
                this.rName = rName;
            }

            public static class Conversation {
                private Peer peer;
                private ChatSettings chat_settings;
                private int in_read;
                private int out_read;
                private int last_message_id;

                public Peer getPeer() {
                    return peer;
                }

                public ChatSettings getChat_settings() {
                    return chat_settings;
                }


                public static class Peer {
                    private int id;
                    private String type;
                    private int local_id;

                    public int getId() {
                        return id;
                    }

                    public String getType() {
                        return type;
                    }
                }

                public static class ChatSettings {
                    private String title;
                    private Photo photo;

                    public String getTitle() {
                        return title;
                    }

                    public Photo getPhoto() {
                        return photo;
                    }

                    public class Photo{
                        private String photo_100;

                        public String getPhoto_100() {
                            return photo_100;
                        }
                    }
                }
            }
        }
    }
}
