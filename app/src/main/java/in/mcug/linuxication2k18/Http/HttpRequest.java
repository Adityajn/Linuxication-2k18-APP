package in.mcug.linuxication2k18.Http;

import in.mcug.linuxication2k18.Pojos.CandidatesList;
import in.mcug.linuxication2k18.Pojos.DefaultRequest;
import in.mcug.linuxication2k18.Pojos.MoneyResponse;
import in.mcug.linuxication2k18.Pojos.RegisterResponse;
import in.mcug.linuxication2k18.Pojos.RegistrationForm;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by aditya on 2/1/18.
 */

public class HttpRequest {

    public static final String API_URL = "http://13.127.162.188:3000/";
    public static Retrofit retrofit= new Retrofit.Builder()
            .baseUrl(API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public interface RetrofitInterface {
        @POST("/app/register")
        Call<RegisterResponse> registerCandidate(
                @Body RegistrationForm request);

        @POST("/app/money")
        Call<MoneyResponse> moneyCollected(
                @Body DefaultRequest defaultRequest);

        @POST("/app/mycandidates")
        Call<CandidatesList> myCandidates(
                @Body DefaultRequest defaultRequest);
    }

}
