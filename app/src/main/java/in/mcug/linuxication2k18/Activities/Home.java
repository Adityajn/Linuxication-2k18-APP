package in.mcug.linuxication2k18.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;
import com.tapadoo.alerter.Alerter;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import in.mcug.linuxication2k18.Http.HttpRequest;
import in.mcug.linuxication2k18.Pojos.DefaultRequest;
import in.mcug.linuxication2k18.Pojos.MoneyResponse;
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

/**
 * Created by aditya on 2/1/18.
 */

public class Home extends AppCompatActivity {

    @BindView(R.id.candidate_name) TextInputEditText candidate_name;
    @BindView(R.id.mobile_number) TextInputEditText mobile_number;
    @BindView(R.id.email_id) TextInputEditText email_id;
    @BindView(R.id.clg_name) TextInputEditText clg_name;
    @BindView(R.id.amount_paid) TextInputEditText amount_paid;
    @BindView(R.id.amount_pending) TextInputEditText amount_pending;
    @BindView(R.id.amount_total) TextInputEditText amount_total;
    @BindView(R.id.comment) TextInputEditText comment;
    @BindView(R.id.spinner) ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register)
    public void registerCandidate(){
        spinner.setVisibility(View.VISIBLE);
        String name = candidate_name.getText().toString();
        String mobile = mobile_number.getText().toString();
        String email = email_id.getText().toString();
        String paid = amount_paid.getText().toString();
        String pending = amount_pending.getText().toString();
        String total = amount_total.getText().toString();
        String comments = comment.getText().toString();
        String college = clg_name.getText().toString();
        String datetime = getCurrentDateTime();

        if(name.equals("") || mobile.length()!=10 || paid.equals("")){
            spinner.setVisibility(View.GONE);
            Alerter.create(this)
                    .setTitle("Please Fill All Mandatory Fields!!")
                    .setText("Name, 10 digit Mobile No, Amount Paid are Mandatory(*) fields")
                    .setDuration(5000)
                    .setBackgroundColorRes(R.color.orange)
                    .enableSwipeToDismiss()
                    .show();
        }
        else{
            String volunteer = PrefUtils.getVolunteerName(getApplicationContext());
            String secret = PrefUtils.getSecret(getApplicationContext());
            RegistrationForm form  =new RegistrationForm(name,mobile,email,college,Integer.parseInt(paid),pending,total,comments,datetime,volunteer,secret);

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
                        spinner.setVisibility(View.GONE);
                        Alerter.create(Home.this)
                                .setTitle("Registration Done")
                                .setText(docResponse.getMessage())
                                .setDuration(10000)
                                .setBackgroundColorRes(R.color.green)
                                .enableSwipeToDismiss()
                                .show();
                        candidate_name.setText("");
                        mobile_number.setText("");
                        email_id.setText("");
                        clg_name.setText("");
                        amount_paid.setText("");
                        amount_pending.setText("");
                        amount_total.setText("");
                        comment.setText("");
                    }
                    else{
                        spinner.setVisibility(View.GONE);
                        Alerter.create(Home.this)
                                .setTitle("Registration Failed")
                                .setText(docResponse.getMessage())
                                .setDuration(10000)
                                .setBackgroundColorRes(R.color.red)
                                .enableSwipeToDismiss()
                                .show();
                    }
                }
                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    spinner.setVisibility(View.GONE);
                    Alerter.create(Home.this)
                            .setTitle("Registration Failed")
                            .setText("Check Internet Connection or Contact App Administrator.")
                            .setDuration(10000)
                            .setBackgroundColorRes(R.color.red)
                            .enableSwipeToDismiss()
                            .show();
                }
            });
        }
    }

    public String getCurrentDateTime(){
        DateTime now = DateTime.now();
        DateTimeFormatter fmt = DateTimeFormat.forPattern("dd-MM-yyyy hh:mm:SSa");
        return fmt.print(now);
    }

    @Override
    public void onBackPressed() {}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.list:
                startActivity(new Intent(getApplicationContext(),MyRegistationsList.class));
                break;
            case R.id.logout:
                new SweetAlertDialog(this,SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are You Sure?")
                        .setContentText("You will lose your API secret key and you will need to contact Administrator Again for it!!")
                        .setConfirmText("Confirm")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                PrefUtils.logOut(getApplicationContext());
                                startActivity(new Intent(getApplicationContext(),Splash.class));
                            }
                        })
                        .setCancelText("Cancel")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();
                            }
                        })
                        .show();
                break;
            case R.id.collection:
                spinner.setVisibility(View.VISIBLE);
                String volunteer = PrefUtils.getVolunteerName(getApplicationContext());
                String secret = PrefUtils.getSecret(getApplicationContext());
                DefaultRequest defaultRequest = new DefaultRequest(volunteer,secret);

                HttpRequest.RetrofitInterface retrofitInterface
                        = HttpRequest.retrofit.create(HttpRequest.RetrofitInterface.class);
                Call<MoneyResponse> responseCall = retrofitInterface.moneyCollected(defaultRequest);
                responseCall.enqueue(new Callback<MoneyResponse>() {
                    @Override
                    public void onResponse(Call<MoneyResponse> call, Response<MoneyResponse> response) {
                        int code=response.code();
                        Log.v("ResponseCode",""+code);
                        MoneyResponse docResponse=response.body();
                        if(docResponse.isStatus()){
                            spinner.setVisibility(View.GONE);
                            Alerter.create(Home.this)
                                    .setTitle("Rs "+docResponse.getMoney())
                                    .setText(docResponse.getMessage())
                                    .setDuration(10000)
                                    .setBackgroundColorRes(R.color.green)
                                    .enableSwipeToDismiss()
                                    .show();
                        }
                        else{
                            spinner.setVisibility(View.GONE);
                            Alerter.create(Home.this)
                                    .setTitle("Some Error Occurred")
                                    .setText("Error!! "+docResponse.getMessage())
                                    .setDuration(10000)
                                    .setBackgroundColorRes(R.color.red)
                                    .enableSwipeToDismiss()
                                    .show();
                        }
                    }
                    @Override
                    public void onFailure(Call<MoneyResponse> call, Throwable t) {
                        spinner.setVisibility(View.GONE);
                        Alerter.create(Home.this)
                                .setTitle("Some Error Occurred")
                                .setText("Check Internet Connection or Contact App Administrator.")
                                .setDuration(10000)
                                .setBackgroundColorRes(R.color.red)
                                .enableSwipeToDismiss()
                                .show();
                    }
                });
                break;
            case R.id.about:
                startActivity(new Intent(getApplicationContext(),About.class));
                break;
            case R.id.help:
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"9373717357")));
                }
                else{
                    ActivityCompat.requestPermissions(Home.this,new String[]{Manifest.permission.CALL_PHONE},1);
                    if(ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
                        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"9373717357")));
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
