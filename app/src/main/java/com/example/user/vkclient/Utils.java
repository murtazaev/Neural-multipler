package com.example.user.vkclient;

import com.example.user.vkclient.models.LastCommentModel;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Utils {

    public static String shareCode(StringBuffer ids, StringBuffer types, String post, String message){
        return "var conversations = "+ids+";\n" +
                "var types = "+types+";\n" +
                "\n" +
                "var count = 0;\n" +
                "while(count != conversations.length){\n" +
                "if(types[count] == \"user\"){\n" +
                "API.messages.send({\n" +
                "\"user_id\": conversations[count], \"attachment\": \""+post+"\",\n" +
                "\"message\":\""+message+"\" });\n" +
                "}else{\n" +
                "API.messages.send({\n" +
                "\"chat_id\": conversations[count], \"attachment\": \""+post+"\",\n" +
                "\"message\":\""+message+"\" });\n" +
                "}\n" +
                "count = count + 1;\n" +
                "};\n" +
                "\n" +
                "return conversations[0];";
    }

    public static String getLastCommCode(String startFrom) {
        return "var userFeed =API.newsfeed.get({\"filters\":\"post\",\"count\":20, \"start_from\": \""+startFrom+"\"});\n" +
                "\n" +
                "var f = 0;\n" +
                "var profileArray = [];\n" +
                "var profile = [];\n" +
                "\n" +
                "while(f != userFeed.items.length){\n" +
                " if(userFeed.items[f].comments.can_post == 1){\n" +
                "    var a = API.wall.getComments({  \n" +
                "                    \"owner_id\": userFeed.items[f].source_id,\n" +
                "                    \"post_id\": userFeed.items[f].post_id,\n" +
                "                    \"count\": 1,\n" +
                "                    \"sort\": \"desc\",\n" +
                "                      \"extended\": 1\n" +
                "});\n" +
                "if(a.items[0].text == null){\n" +
                "  profileArray.push(\"nonComm\");\n" +
                "}else{\n" +
                "if(a.profiles[0].first_name == null){\n" +
                "   profile.push(a.groups[0].photo_50);\n" +
                "   profile.push(a.groups[0].name);\n" +
                "   profile.push(a.items[0].text);\n" +
                "   profileArray.push(profile);\n" +
                "   profile = [];\n" +
                "}else{\n" +
                "   profile.push(a.profiles[0].photo_50);\n" +
                "   profile.push(a.profiles[0].first_name + \" \" + a.profiles[0].last_name );\n" +
                "   profile.push(a.items[0].text);\n" +
                "   profileArray.push(profile);\n" +
                "   profile = [];} } }\n" +
                "else{\n" +
                "profileArray.push(\"cannotAddComm\");\n" +
                "};\n" +
                "f = f + 1;\n" +
                "};\n" +
                "\n" +
                "return {\"user_feed\": userFeed, \"lasts_comm_in_feed\":profileArray};";
    }


    public static ArrayList<LastCommentModel> parse(JSONArray array) {
        ArrayList<LastCommentModel> lastComm;
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
        return lastComm;
    }



    public static String allFieldsProfle = "photo_id, verified, sex, bdate, city, country, home_town," +
            " has_photo, photo_50, photo_100, photo_200_orig, photo_200, photo_400_orig, photo_max, photo_max_orig," +
            " online, domain, has_mobile, contacts, site, education, universities, schools, status, last_seen, followers_count," +
            " common_count, occupation, nickname, relatives, relation, personal, connections, exports, wall_comments, activities," +
            " interests, music, movies, tv, books, games, about, quotes, can_post, can_see_all_posts, can_see_audio," +
            " can_write_private_message, can_send_friend_request, is_favorite, is_hidden_from_feed, timezone, screen_name," +
            " maiden_name, crop_photo, is_friend, friend_status, career, military, blacklisted, blacklisted_by_me";

    public static Double countLines(String str){
        if(str == null || str.isEmpty()){
            return 0d;
        }
        Double count = (double) str.split("\r\n|\r|\n").length;

        return count;
    }

}
