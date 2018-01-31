package in.mcug.linuxication2k18.Pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aditya on 2/1/18.
 */

public class RegisterResponse {
    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("msg")
    @Expose
    private String message;

    public boolean isStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
}
