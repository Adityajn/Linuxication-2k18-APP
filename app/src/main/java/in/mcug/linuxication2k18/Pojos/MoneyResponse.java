package in.mcug.linuxication2k18.Pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aditya on 2/2/18.
 */

public class MoneyResponse {

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("money")
    @Expose
    private int money;

    @SerializedName("msg")
    @Expose
    private String message;


    public boolean isStatus() {
        return status;
    }

    public int getMoney() {
        return money;
    }

    public String getMessage() {
        return message;
    }
}
