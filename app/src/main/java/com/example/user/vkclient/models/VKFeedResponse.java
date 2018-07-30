package com.example.user.vkclient.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class VKFeedResponse implements Parcelable {

    private Response user_feed;

    protected VKFeedResponse(Parcel in) {
        user_feed = in.readParcelable(Response.class.getClassLoader());
    }

    public static final Creator<VKFeedResponse> CREATOR = new Creator<VKFeedResponse>() {
        @Override
        public VKFeedResponse createFromParcel(Parcel in) {
            return new VKFeedResponse(in);
        }

        @Override
        public VKFeedResponse[] newArray(int size) {
            return new VKFeedResponse[size];
        }
    };

    public Response getUser_feed() {
        return user_feed;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(user_feed, flags);
    }


    public static class Response implements Parcelable{

        private String next_from;
        private ArrayList<VKFeedObject> items;
        private ArrayList<CommentsModel.Comments.Groups> groups;

        protected Response(Parcel in) {
            next_from = in.readString();
            items = in.createTypedArrayList(VKFeedObject.CREATOR);
        }

        public String getNext_from() {
            return next_from;
        }

        public ArrayList<VKFeedObject> getItems() {
            return items;
        }

        public ArrayList<CommentsModel.Comments.Groups> getGroups() {
            return groups;
        }

        public static final Creator<Response> CREATOR = new Creator<Response>() {
            @Override
            public Response createFromParcel(Parcel in) {
                return new Response(in);
            }

            @Override
            public Response[] newArray(int size) {
                return new Response[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(next_from);
            dest.writeTypedList(items);
        }


        public static class VKFeedObject implements Parcelable {
            private String type;
            private int source_id;
            private int date;
            private int post_id;
            private String post_type;
            private String text;
            private int market_as_ads;
            private ArrayList<Attachments> attachments;
            private PostSource post_source;
            private Comments comments;
            private Likes likes;
            private Reposts reposts;
            private Views views;
            private String parentGroupIcon;
            private String parentGroupName;

            protected VKFeedObject(Parcel in) {
                type = in.readString();
                source_id = in.readInt();
                date = in.readInt();
                post_id = in.readInt();
                post_type = in.readString();
                text = in.readString();
                market_as_ads = in.readInt();
                attachments = in.createTypedArrayList(Attachments.CREATOR);
                post_source = in.readParcelable(PostSource.class.getClassLoader());
                comments = in.readParcelable(Comments.class.getClassLoader());
                likes = in.readParcelable(Likes.class.getClassLoader());
                reposts = in.readParcelable(Reposts.class.getClassLoader());
                views = in.readParcelable(Views.class.getClassLoader());
                parentGroupIcon = in.readString();
                parentGroupName = in.readString();
            }

            public String getText() {
                return text;
            }

            public ArrayList<Attachments> getAttachments() {
                return attachments;
            }

            public int getPost_id() {
                return post_id;
            }

            public String getType() {
                return type;
            }

            public Likes getLikes() {
                return likes;
            }

            public int getSource_id() {
                return source_id;
            }

            public Comments getComments() {
                return comments;
            }

            public String getParentGroupIcon() {
                return parentGroupIcon;
            }

            public void setParentGroupIcon(String parentGroupIcon) {
                this.parentGroupIcon = parentGroupIcon;
            }

            public String getParentGroupName() {
                return parentGroupName;
            }

            public void setParentGroupName(String parentGroupName) {
                this.parentGroupName = parentGroupName;
            }

            public int getDate() {
                return date;
            }

            public Reposts getReposts() {
                return reposts;
            }

            public static final Creator<VKFeedObject> CREATOR = new Creator<VKFeedObject>() {
                @Override
                public VKFeedObject createFromParcel(Parcel in) {
                    return new VKFeedObject(in);
                }

                @Override
                public VKFeedObject[] newArray(int size) {
                    return new VKFeedObject[size];
                }
            };

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(type);
                dest.writeInt(source_id);
                dest.writeInt(date);
                dest.writeInt(post_id);
                dest.writeString(post_type);
                dest.writeString(text);
                dest.writeInt(market_as_ads);
                dest.writeTypedList(attachments);
                dest.writeParcelable(post_source, flags);
                dest.writeParcelable(comments, flags);
                dest.writeParcelable(likes, flags);
                dest.writeParcelable(reposts, flags);
                dest.writeParcelable(views, flags);
                dest.writeString(parentGroupIcon);
                dest.writeString(parentGroupName);
            }


            public static class Attachments implements Parcelable{
                private String type;
                private VKPhoto photo;
                private VKLink link;

                protected Attachments(Parcel in) {
                    type = in.readString();
                    photo = in.readParcelable(VKPhoto.class.getClassLoader());
                    link = in.readParcelable(VKLink.class.getClassLoader());
                }

                public VKPhoto getPhoto() {
                    return photo;
                }

                public VKLink getLink() {
                    return link;
                }

                public String getType() {
                    return type;
                }

                public static final Creator<Attachments> CREATOR = new Creator<Attachments>() {
                    @Override
                    public Attachments createFromParcel(Parcel in) {
                        return new Attachments(in);
                    }

                    @Override
                    public Attachments[] newArray(int size) {
                        return new Attachments[size];
                    }
                };

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(type);
                    dest.writeParcelable(photo, flags);
                    dest.writeParcelable(link, flags);
                }


                public static class VKLink implements Parcelable {
                    private String url;
                    private String title;
                    private String caption;
                    private String description;
                    private VKLinkPhoto photo;

                    protected VKLink(Parcel in) {
                        url = in.readString();
                        title = in.readString();
                        caption = in.readString();
                        description = in.readString();
                        photo = in.readParcelable(VKLinkPhoto.class.getClassLoader());
                    }

                    public VKLinkPhoto getPhoto() {
                        return photo;
                    }

                    public static final Creator<VKLink> CREATOR = new Creator<VKLink>() {
                        @Override
                        public VKLink createFromParcel(Parcel in) {
                            return new VKLink(in);
                        }

                        @Override
                        public VKLink[] newArray(int size) {
                            return new VKLink[size];
                        }
                    };


                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeString(url);
                        dest.writeString(title);
                        dest.writeString(caption);
                        dest.writeString(description);
                        dest.writeParcelable(photo, flags);
                    }


                    public static class VKLinkPhoto implements Parcelable{
                        private int id;
                        private int album_id;
                        private int owner_id;
                        private String text;
                        private int date;
                        private ArrayList<VKLinkArrayPhoto> sizes;

                        protected VKLinkPhoto(Parcel in) {
                            id = in.readInt();
                            album_id = in.readInt();
                            owner_id = in.readInt();
                            text = in.readString();
                            date = in.readInt();
                            sizes = in.createTypedArrayList(VKLinkArrayPhoto.CREATOR);
                        }

                        public int getOwner_id() {
                            return owner_id;
                        }

                        public ArrayList<VKLinkArrayPhoto> getSizes() {
                            return sizes;
                        }

                        public static final Creator<VKLinkPhoto> CREATOR = new Creator<VKLinkPhoto>() {
                            @Override
                            public VKLinkPhoto createFromParcel(Parcel in) {
                                return new VKLinkPhoto(in);
                            }

                            @Override
                            public VKLinkPhoto[] newArray(int size) {
                                return new VKLinkPhoto[size];
                            }
                        };

                        @Override
                        public int describeContents() {
                            return 0;
                        }

                        @Override
                        public void writeToParcel(Parcel dest, int flags) {
                            dest.writeInt(id);
                            dest.writeInt(album_id);
                            dest.writeInt(owner_id);
                            dest.writeString(text);
                            dest.writeInt(date);
                            dest.writeTypedList(sizes);
                        }

                        public static class VKLinkArrayPhoto implements Parcelable {
                            String type;
                            private String url;
                            int width;
                            private int height;

                            protected VKLinkArrayPhoto(Parcel in) {
                                type = in.readString();
                                url = in.readString();
                                width = in.readInt();
                                height = in.readInt();
                            }

                            public String getUrl() {
                                return url;
                            }

                            public int getHeight() {
                                return height;
                            }

                            @Override
                            public int describeContents() {
                                return 0;
                            }

                            @Override
                            public void writeToParcel(Parcel dest, int flags) {
                                dest.writeString(type);
                                dest.writeString(url);
                                dest.writeInt(width);
                                dest.writeInt(height);
                            }

                            public static final Creator<VKLinkArrayPhoto> CREATOR = new Creator<VKLinkArrayPhoto>() {
                                @Override
                                public VKLinkArrayPhoto createFromParcel(Parcel in) {
                                    return new VKLinkArrayPhoto(in);
                                }

                                @Override
                                public VKLinkArrayPhoto[] newArray(int size) {
                                    return new VKLinkArrayPhoto[size];
                                }
                            };
                        }

                    }
                }


                public static class VKPhoto implements Parcelable{
                    private int id;
                    private int album_id;
                    private int owner_id;
                    private int use_id;
                    private String text;
                    private int date;
                    private int post_id;
                    private String access_key;
                    private ArrayList<VKArrayPhoto> sizes;

                    protected VKPhoto(Parcel in) {
                        id = in.readInt();
                        album_id = in.readInt();
                        owner_id = in.readInt();
                        use_id = in.readInt();
                        text = in.readString();
                        date = in.readInt();
                        post_id = in.readInt();
                        access_key = in.readString();
                        sizes = in.createTypedArrayList(VKArrayPhoto.CREATOR);
                    }



                    public ArrayList<VKArrayPhoto> getSizes() {
                        return sizes;
                    }

                    public int getOwner_id() {
                        return owner_id;
                    }

                    public String getAccess_key() {
                        return access_key;
                    }


                    public static final Creator<VKPhoto> CREATOR = new Creator<VKPhoto>() {
                        @Override
                        public VKPhoto createFromParcel(Parcel in) {
                            return new VKPhoto(in);
                        }

                        @Override
                        public VKPhoto[] newArray(int size) {
                            return new VKPhoto[size];
                        }
                    };

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeInt(id);
                        dest.writeInt(album_id);
                        dest.writeInt(owner_id);
                        dest.writeInt(use_id);
                        dest.writeString(text);
                        dest.writeInt(date);
                        dest.writeInt(post_id);
                        dest.writeString(access_key);
                        dest.writeTypedList(sizes);
                    }




                    public static class VKArrayPhoto implements Parcelable {
                        String type;
                        private String url;
                        int width;
                        private int height;

                        public String getUrl() {
                            return url;
                        }

                        public int getHeight() {
                            return height;
                        }

                        VKArrayPhoto(Parcel in){
                            type = in.readString();
                            url = in.readString();
                            width = in.readInt();
                            height = in.readInt();
                        }

                        public static final Creator<VKArrayPhoto> CREATOR = new Creator<VKArrayPhoto>() {
                            @Override
                            public VKArrayPhoto createFromParcel(Parcel in) {
                                return new VKArrayPhoto(in);
                            }

                            @Override
                            public VKArrayPhoto[] newArray(int size) {
                                return new VKArrayPhoto[size];
                            }
                        };

                        @Override
                        public int describeContents() {
                            return 0;
                        }

                        @Override
                        public void writeToParcel(Parcel dest, int flags) {
                            dest.writeString(type);
                            dest.writeString(url);
                            dest.writeInt(width);
                            dest.writeInt(height);
                        }
                    }
                }
            }

            public static class PostSource implements Parcelable {
                String type;

                PostSource(Parcel in){
                    type = in.readString();
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeString(type);
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                public static final Creator<PostSource> CREATOR = new Creator<PostSource>() {
                    @Override
                    public PostSource createFromParcel(Parcel in) {
                        return new PostSource(in);
                    }

                    @Override
                    public PostSource[] newArray(int size) {
                        return new PostSource[size];
                    }
                };
            }

            public static class Comments implements Parcelable {
                private int count;
                private boolean groups_can_post;
                private int can_post;

                public int getCount() {
                    return count;
                }

                public int getCan_post() {
                    return can_post;
                }

                Comments(Parcel in){
                    count = in.readInt();
                    groups_can_post = in.readByte() != 0;
                    can_post = in.readInt();
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(count);
                    dest.writeInt(groups_can_post ? 1 : 0);
                    dest.writeInt(can_post);
                }

                public static final Creator<Comments> CREATOR = new Creator<Comments>() {
                    @Override
                    public Comments createFromParcel(Parcel in) {
                        return new Comments(in);
                    }

                    @Override
                    public Comments[] newArray(int size) {
                        return new Comments[size];
                    }
                };
            }

            public static class Likes implements Parcelable {
                private int count;
                private int user_likes;
                private int can_like;
                private int can_publish;

                public int getUser_likes() {
                    return user_likes;
                }

                public int getCan_like() {
                    return can_like;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count){
                    this.count = count;
                }

                public void setUser_likes(int user_likes){
                    this.user_likes = user_likes;
                }

                Likes(Parcel in){
                    count = in.readInt();
                    user_likes = in.readInt();
                    can_like = in.readInt();
                    can_publish = in.readInt();
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(count);
                    dest.writeInt(user_likes);
                    dest.writeInt(can_like);
                    dest.writeInt(can_publish);
                }

                public static final Creator<Likes> CREATOR = new Creator<Likes>() {
                    @Override
                    public Likes createFromParcel(Parcel in) {
                        return new Likes(in);
                    }

                    @Override
                    public Likes[] newArray(int size) {
                        return new Likes[size];
                    }
                };
            }

            public static class Reposts implements Parcelable{
                private int count;
                int user_reposted;

                public int getCount() {
                    return count;
                }

                Reposts(Parcel in){
                    count = in.readInt();
                    user_reposted = in.readInt();
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(count);
                    dest.writeInt(user_reposted);
                }

                public static final Creator<Reposts> CREATOR = new Creator<Reposts>() {
                    @Override
                    public Reposts createFromParcel(Parcel in) {
                        return new Reposts(in);
                    }

                    @Override
                    public Reposts[] newArray(int size) {
                        return new Reposts[size];
                    }
                };
            }

            public static class Views implements Parcelable {
                int count;

                Views(Parcel in){
                    count = in.readInt();
                }

                void writrToParcel(Parcel out){
                    out.writeInt(count);
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(count);
                }

                public static final Creator<Views> CREATOR = new Creator<Views>() {
                    @Override
                    public Views createFromParcel(Parcel in) {
                        return new Views(in);
                    }

                    @Override
                    public Views[] newArray(int size) {
                        return new Views[size];
                    }
                };
            }
        }
    }
}
