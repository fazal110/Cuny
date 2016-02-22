package com.example.asif.cuny;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asif.cuny.JavaAPI_Classes.GMailSender;

import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


public class RegisterActivity extends ActionBarActivity {

    EditText email,full_name,pass,re_pass;
    ImageView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        registerClick();

    }

    public void init(){
        email = (EditText) findViewById(R.id.emailet);
        full_name = (EditText) findViewById(R.id.full_name);
        pass = (EditText) findViewById(R.id.pass);
        re_pass = (EditText) findViewById(R.id.re_pass);
        register = (ImageView) findViewById(R.id.register);

    }

    public void registerClick(){
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
    }

    public void register(){
        Thread thread = new Thread(new Runnable(){


            @Override
            public void run() {
                try {

                    HttpClient httpclient = new DefaultHttpClient();

                    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                    Date date = new Date();
                    int activation_code = randInt(10,1000);
                    String link = "https://celeritas-solutions.com/cds/hivelet/registerUser.php?EmailAddress="+email.getText().toString()+"@&UserName=" +
                            "%@&AgencyID=1&AddedDateTime="+dateFormat.format(date)+"@&UserCurrentPassword="+pass.getText().toString()+"@&ActivationCode="+activation_code+"@";
                    HttpPost httppost = new HttpPost(link);

                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    final String response = httpclient.execute(httppost,
                            responseHandler);

                    if(response.equalsIgnoreCase("Records Added.Passwords Added.")){
                        Toast.makeText(getApplicationContext(),"You Have Registered Successfully",Toast.LENGTH_LONG).show();
                        try{
                            GMailSender gMailSender = new GMailSender("fazalcs13@gmail.com","syedfazal");
                            gMailSender.sendMail("Cuny Activation Code","Thank you for joining the Cuny community. " +
                                    "Please see the activation code below which should be" +
                                    " entered into the Join In screen.","fazalcs13@gmail.com",email.getText().toString());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                   // Log.i(TAG, "Response : " + response);




                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public static int randInt(int min, int max) {


        Random rand = new Random();

        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
