package in.mcug.linuxication2k18.Pojos;

/**
 * Created by aditya on 2/1/18.
 */

public class RegistrationForm {
    String name;
    String mobile;
    String email;
    int paid;
    String pending;
    String total;
    String comment;
    String datetime;
    String volunteer;
    String secret;
    String college;

    public RegistrationForm(String name, String mobile, String email, String college, int paid, String pending, String total,String comment,String datetime,String volunteer, String secret) {
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.paid = paid;
        this.pending = pending;
        this.total = total;
        this.volunteer = volunteer;
        this.secret = secret;
        this.comment=comment;
        this.datetime=datetime;
        this.college=college;
    }
}
