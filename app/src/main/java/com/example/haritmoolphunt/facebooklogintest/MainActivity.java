package com.example.haritmoolphunt.facebooklogintest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initinstance();
    }

    private void initinstance() {
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email","public_profile","user_photos","user_posts");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(MainActivity.this,loginResult.getAccessToken().getUserId(),Toast.LENGTH_SHORT).show();
                String userid = loginResult.getAccessToken().getUserId();

                GraphRequest otherRequest = GraphRequest.newGraphPathRequest(loginResult.getAccessToken(),
                        "?ids=737460709751015&fields=name,picture{url},posts{id,created_time,message,attachments{media{image{src}},title,type,url,subattachments{media{image{src}}}}}",
                        new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        Log.d("Json",response.getJSONObject().toString());
                    }
                });
               /* GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        displayUserInfo(object);
                    }
                }); */
                Bundle parameters = new Bundle();
                //parameters.putString("fields", "name,id,");
                otherRequest.setParameters(parameters);
                otherRequest.executeAsync();

            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(MainActivity.this,"cancle",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    private void displayUserInfo(JSONObject object) {
        Log.d("Json",object.toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}



