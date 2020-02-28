package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.util.Log;

import android.view.View;

import android.widget.Button;

import android.widget.EditText;

import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;



import okhttp3.Call;

import okhttp3.OkHttpClient;

import okhttp3.Request;

import okhttp3.Response;

import okhttp3.*;


public class Login_page_activity extends AppCompatActivity implements View.OnClickListener{
    public static final String Tag="Main_activity";

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_page_layout);

        Button button1=(Button)findViewById(R.id.log_in);

        Button button2=(Button)findViewById(R.id.register);

        button1.setOnClickListener(this);

        button2.setOnClickListener(this);



    }
    public void show_user_name_warn(String warning)

    {

        TextView warn=(TextView) findViewById(R.id.user_name_warning);

        warn.setText(warning);

    }

    public void show_password_warn(String warning)

    {

        TextView warn=(TextView) findViewById(R.id.Password_warning);

        warn.setText(warning);

    }
    public void showWarnSweetDialog(String warning)

    {

        TextView warn=(TextView) findViewById(R.id.warning);

        warn.setText(warning);

    }



    @Override

    public void onClick(View v)

    {

        EditText user_name=(EditText) findViewById(R.id.User_name);

        EditText pass_word=(EditText) findViewById(R.id.Pass_word);

        String userName = user_name.getText().toString();

        String passWord = pass_word.getText().toString();

        if(userName.equals("")||passWord.equals(""))

        {

            showWarnSweetDialog("账号密码不能为空");

            return;

        }

        switch (v.getId())

        {

            case R.id.log_in:

                String login_url = "http://nightmaremlp.pythonanywhere.com/appnet/login";
                String login_mode= "log_in";
                show_user_name_warn("");
                show_password_warn("");
                registeNameWordToServer(login_url,userName,passWord,login_mode);

                break;

            case R.id.register:
                String register_mode= "register";
                String register_url = "http://nightmaremlp.pythonanywhere.com/appnet/register";
                show_user_name_warn("");
                show_password_warn("");
                registeNameWordToServer(register_url,userName,passWord,register_mode);

                break;

        }

    }



    private void registeNameWordToServer(String url,final String userName,String passWord,final String mode){

        OkHttpClient client = new OkHttpClient();

        FormBody.Builder formBuilder = new FormBody.Builder();

        formBuilder.add("username", userName);

        formBuilder.add("password", passWord);

        Request request = new Request.Builder().url(url).post(formBuilder.build()).build();

        final Call call = client.newCall(request);
        showWarnSweetDialog("等待服务器响应");
        call.enqueue(new Callback()

        {

            @Override

            public void onFailure(Call call, final IOException e)

            {

                runOnUiThread(new Runnable()

                {

                    @Override

                    public void run()

                    {

                        Log.d("okhttp_error",e.getMessage());

                        showWarnSweetDialog("服务器错误");

                    }

                });

            }



            @Override

            public void onResponse(Call call, final Response response) throws IOException

            {
                final String res = response.body().string();

                runOnUiThread(new Runnable()

                {

                    @Override

                    public void run()

                    {
                        try {
                            JSONObject res_inform = new JSONObject(res);
                            String message = res_inform.getString("message");
                            String error_code = res_inform.getString("error_code");
                            if(mode=="log_in") {
                                if (error_code.equals("0")) {

                                    show_user_name_warn(message);
                                    showWarnSweetDialog("");
                                } else if (error_code.equals("1")) {

                                    show_password_warn(message);
                                    showWarnSweetDialog("");
                                }else if (error_code.equals("2")) {

                                    showWarnSweetDialog(message);
                                }

                            }
                            else if(mode=="register")
                            {
                                if (error_code.equals("0")) {

                                    show_user_name_warn(message);
                                    showWarnSweetDialog("");
                                }
                                else if (error_code.equals("2")) {

                                    showWarnSweetDialog(message);
                                }

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                });

            }

        });



    }



    @Override

    protected void onStart()

    {

        super.onStart();

        Log.d(Tag,"onStart");

    }

    @Override

    protected void onResume()

    {

        super.onResume();

        Log.d(Tag,"onResume");

    }

    @Override

    protected void onPause()

    {

        super.onPause();

        Log.d(Tag,"onPause");

    }

    @Override

    protected void onStop()

    {

        super.onStop();

        Log.d(Tag,"onStop");

    }

    @Override

    protected void onDestroy()

    {

        super.onDestroy();

        Log.d(Tag,"onDestroy");

    }

    @Override

    protected void onRestart()

    {

        super.onRestart();

        Log.d(Tag,"onRestart");

    }
}

