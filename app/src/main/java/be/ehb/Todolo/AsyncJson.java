package be.ehb.Todolo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import be.ehb.Todolo.interfaces.AsyncJsonHttpResponse;

public class AsyncJson extends AppCompatActivity implements AsyncJsonHttpResponse {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_json);
        final AsyncJsonHttpResponse listener = this;
        final Intent intent = getIntent();

        Button button = findViewById(R.id.async_btn_get);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DownloadJsonAsynck downloadJsonAsynck = new DownloadJsonAsynck();
                downloadJsonAsynck.listener = listener;
                String url = intent.getStringExtra("URL");
                if ( url != null) {
                    downloadJsonAsynck.execute(url);
                }
            }
        });
    }

    @Override
    public void processFinish(String output) {

        TextView textView = findViewById(R.id.async_textview);
        textView.setText(output);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}