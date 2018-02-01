package in.mcug.linuxication2k18.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.mcug.linuxication2k18.R;

public class About extends AppCompatActivity {

    @BindView(R.id.credits) TextView credits;
    @BindView(R.id.website) TextView website;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        credits.setText("Credits:\nHussain Ali Bohra");
        website.setText("Visit:\nwww.mcug.in");
        ActionBar toolbar = getSupportActionBar();
        toolbar.setDisplayHomeAsUpEnabled(true);
    }

    @OnClick(R.id.website)
    public void visitWebsite(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("http://www.mcug.in"));
        startActivity(intent);
    }

}
