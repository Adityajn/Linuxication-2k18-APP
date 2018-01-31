package in.mcug.linuxication2k18.Activities;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

import com.tapadoo.alerter.Alerter;

import in.mcug.linuxication2k18.Http.HttpRequest;
import in.mcug.linuxication2k18.Pojos.RegisterResponse;
import in.mcug.linuxication2k18.Pojos.RegistrationForm;
import in.mcug.linuxication2k18.Prefs.PrefUtils;
import in.mcug.linuxication2k18.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Home extends AppCompatActivity {

    @BindView(R.id.candidate_name) TextInputEditText candidate_name;
    @BindView(R.id.mobile_number) TextInputEditText mobile_number;
    @BindView(R.id.email_id) TextInputEditText email_id;
    @BindView(R.id.clg_name) TextInputEditText clg_name;
    @BindView(R.id.amount_paid) TextInputEditText amount_paid;
    @BindView(R.id.amount_pending) TextInputEditText amount_pending;
    @BindView(R.id.amount_total) TextInputEditText amount_total;
    @BindView(R.id.mainLayout) LinearLayout mainLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.register)
    public void registerCandidate(){
        String name = candidate_name.getText().toString();
        String mobile = mobile_number.getText().toString();
        String email = email_id.getText().toString();
        String paid = amount_paid.getText().toString();
        String pending = amount_pending.getText().toString();
        String total = amount_total.getText().toString();

        if(name.equals("") || mobile.equals("") || paid.equals("")){
            Alerter.create(this)
                    .setTitle("Please Fill All Mandatory Fields!!")
                    .setText("Name, Mobile No, Amount Paid are Mandatory(*) fields")
                    .setDuration(5000)
                    .setBackgroundColorRes(R.color.orange)
                    .enableSwipeToDismiss()
                    .show();
        }
        else{
            String volunteer = PrefUtils.getVolunteerName(getApplicationContext());
            String secret = PrefUtils.getSecret(getApplicationContext());
            RegistrationForm form  =new RegistrationForm(name,mobile,email,paid,pending,total,volunteer,secret);

            HttpRequest.RetrofitInterface retrofitInterface
                    = HttpRequest.retrofit.create(HttpRequest.RetrofitInterface.class);


            Call<RegisterResponse> responseCall = retrofitInterface.registerCandidate(form);
            responseCall.enqueue(new Callback<RegisterResponse>() {
                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    int code=response.code();
                    Log.v("ResponseCode",""+code);
                    RegisterResponse docResponse=response.body();
                    if(docResponse.isStatus()){
                        Snackbar.make(findViewById(R.id.mainLayout),
                                docResponse.getMessage()+". Redirecting to Home.",Snackbar.LENGTH_SHORT).show();
                    }
                    else{
                        Snackbar.make(findViewById(R.id.mainLayout),docResponse.getMessage(),Snackbar.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {

                }
            });

        }
    }


}
