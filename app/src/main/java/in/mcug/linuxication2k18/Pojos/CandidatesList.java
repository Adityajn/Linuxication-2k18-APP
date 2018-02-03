package in.mcug.linuxication2k18.Pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by aditya on 2/3/18.
 */

public class CandidatesList {

    @SerializedName("status")
    @Expose
    private boolean status;


    @SerializedName("list")
    @Expose
    private ArrayList<HashMap<String,String>> list;

    public String getMessage() {
        return message;
    }

    @SerializedName("msg")
    @Expose
    private String message;

    public boolean getStatus() {
        return status;
    }

    public String getRegID(int index){
        return list.get(index).get("regid");
    }

    public String getMobile(int index){
        return list.get(index).get("mobile");
    }

    public String getPending(int index){
        return list.get(index).get("pending");
    }

    public String getPaid(int index){
        return list.get(index).get("paid");
    }

    public String getName(int index){
        return list.get(index).get("name");
    }

    public String getEmail(int index){
        return list.get(index).get("email");
    }

    public String getDateTime(int index){
        return list.get(index).get("datetime");
    }

    public String getComment(int index){
        return list.get(index).get("comment");
    }

    public String getCollege(int index){
        return list.get(index).get("college");
    }


    public int size(){
        return list.size();
    }



}
