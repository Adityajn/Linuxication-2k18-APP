package in.mcug.linuxication2k18.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.tapadoo.alerter.Alerter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.mcug.linuxication2k18.Http.HttpRequest;
import in.mcug.linuxication2k18.Pojos.CandidatesList;
import in.mcug.linuxication2k18.Pojos.DefaultRequest;
import in.mcug.linuxication2k18.Pojos.MoneyResponse;
import in.mcug.linuxication2k18.Prefs.PrefUtils;
import in.mcug.linuxication2k18.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by aditya on 2/1/18.
 */


public class MyRegistationsList extends AppCompatActivity {

    @BindView(R.id.recyclerView) RecyclerView rv;
    RecyclerView.Adapter radapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_registations_list);
        ButterKnife.bind(this);
        ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));

        String volunteer = PrefUtils.getVolunteerName(getApplicationContext());
        String secret = PrefUtils.getSecret(getApplicationContext());
        DefaultRequest defaultRequest = new DefaultRequest(volunteer,secret);
        HttpRequest.RetrofitInterface retrofitInterface
                = HttpRequest.retrofit.create(HttpRequest.RetrofitInterface.class);

        Call<CandidatesList> responseCall = retrofitInterface.myCandidates(defaultRequest);
        responseCall.enqueue(new Callback<CandidatesList>() {
            @Override
            public void onResponse(Call<CandidatesList> call, Response<CandidatesList> response) {
                int code=response.code();
                Log.v("ResponseCode",""+code);
                CandidatesList docResponse=response.body();
                if(docResponse.getStatus()){
                    Alerter.create(MyRegistationsList.this)
                            .setTitle("Total "+docResponse.size()+" registrations done")
                            .setDuration(5000)
                            .setBackgroundColorRes(R.color.green)
                            .enableSwipeToDismiss()
                            .show();
                    radapter = new RegistrationListAdapter(docResponse,getApplicationContext());
                    rv.setAdapter(radapter);
                }
                else{
                    Alerter.create(MyRegistationsList.this)
                            .setTitle("Some Error Occurred")
                            .setText("Error!! Contact App Administratot")
                            .setDuration(5000)
                            .setBackgroundColorRes(R.color.red)
                            .enableSwipeToDismiss()
                            .show();
                }
            }
            @Override
            public void onFailure(Call<CandidatesList> call, Throwable t) {
                Alerter.create(MyRegistationsList.this)
                        .setTitle("Some Error Occurred")
                        .setText("Check Internet Connection or Contact App Administrator.")
                        .setDuration(10000)
                        .setBackgroundColorRes(R.color.red)
                        .enableSwipeToDismiss()
                        .show();
            }
        });

    }
}
