package com.example.asif.cuny;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "Cuny";
    ImageView register;
    EditText email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        checkEmail();
        registerClick();


    }

    public void init(){
        View mainview = findViewById(R.id.mainlayout);
        mainview.getBackground().setAlpha(235);
        View view = findViewById(R.id.layout);
        register = (ImageView) view.findViewById(R.id.register);
        email = (EditText) findViewById(R.id.emailet);
        pass = (EditText) findViewById(R.id.pass);
    }

    public void checkEmail(){
        Thread thread = new Thread(new Runnable(){


            @Override
            public void run() {
                try {

                  HttpClient  httpclient = new DefaultHttpClient();

                    String link = "https://celeritas- solutions.com/cds/hivelet/getEmailCuny.php?EmailAddress="+email.getText().toString();
                   HttpPost httppost = new HttpPost(link);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    final String response = httpclient.execute(httppost,
                            responseHandler);

                    Log.i(TAG, "Response : " + response);

                    if(response.equalsIgnoreCase("")){
                        Toast.makeText(getApplicationContext(),"User Already Sign In,Login to Continue",Toast.LENGTH_LONG).show();
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void registerClick(){
       register.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(MainActivity.this,RegisterActivity.class);
               startActivity(i);
           }
       });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
