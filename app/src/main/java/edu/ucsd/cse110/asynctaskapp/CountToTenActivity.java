package edu.ucsd.cse110.asynctaskapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CountToTenActivity extends AppCompatActivity {

    private boolean isCancelled = false;
    private Button btnCount;
    private Button btnCancel;
    private Button btnGoBack;
    private TextView textResult;

    private class CountToTenAsyncTask extends AsyncTask<String, String, String> {
        private String resp;

        @Override
        protected String doInBackground(String... params){
            try{
                isCancelled = false;
                int time = Integer.parseInt(params[0]) * 1000;
                for(int i=1; i<=10; i++){
                    if(isCancelled){
                        publishProgress("Cancelled");
                        resp = "Cancelled";
                        break;
                    }
                    publishProgress(String.valueOf(i));
                    Thread.sleep(time);
                }
                resp = "10";
            } catch (Exception e){
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onProgressUpdate(String... result) {
            textResult.setText(result[0]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_to_ten);

        btnCount = findViewById(R.id.buttonCount);
        btnCancel = findViewById(R.id.buttonCancel);
        btnGoBack = findViewById(R.id.buttonGoBack);
        textResult = findViewById(R.id.textResult);

        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountToTenAsyncTask runner = new CountToTenAsyncTask();
                runner.execute("1");
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              https://developer.android.com/reference/android/os/AsyncTask#cancel(boolean)
                isCancelled = true;
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isCancelled = true;
                Intent goBack = new Intent(CountToTenActivity.this, MainActivity.class);
                startActivity(goBack);
            }
        });
    }
}
