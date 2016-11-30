package in.ac.iifm.devadevan.mydbase;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent i=getIntent();
        String str=i.getStringExtra("RECORDS");
        WebView  rs= (WebView) findViewById(R.id.dbResult);
        rs.loadData(str,"text/html",null);
    }
}
