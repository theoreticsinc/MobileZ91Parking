package com.theoretics.mobilepos.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.theoretics.mobilepos.R;
import com.theoretics.mobilepos.util.DBHelper;

import java.util.Date;

public class LoginActivity extends AppCompatActivity{

    private ImageButton loginBtn;

    private EditText loginUsername;
    private EditText loginPassword;

    private DBHelper dbh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbh = new DBHelper(this);
        initView();
    }

    @Override
    public void onBackPressed() {

    }

    private void initView() {
        loginUsername = (EditText) findViewById(R.id.loginUsername);
        loginPassword = (EditText) findViewById(R.id.loginPassword);

        loginBtn = (ImageButton) findViewById(R.id.logINBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //isExists();
                //start_autoRead();
                System.out.println("ANGELO : CLICKED [" + new Date().toString() + "]" );
                if(dbh.validateLogin(loginUsername.getText().toString(), loginPassword.getText().toString())) {
                    loginUsername.setText("success");
                    loginPassword.setText("success");
                    //dbh.saveLogin();
                    finish();
                } else {
                    loginUsername.setText("");
                    loginPassword.setText("");

                }
            }
        });
    }
}