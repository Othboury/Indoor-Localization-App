package com.example.userindoorapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.userindoorapp.R;
import com.example.userindoorapp.UserLoginTask;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername;
    EditText edtPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(login() != null){
                        redirect(login());
                    };
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private boolean validateLogin(String username, String password){
        if(username == null || username.trim().length() == 0){
            Toast.makeText(this, "Login manquant!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(password == null || password.trim().length() == 0){
            Toast.makeText(this, "Mot de passe manquant!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void redirect(String Token){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.putExtra("Token", Token);
        startActivity(intent);
    }


    private String login() throws ExecutionException, InterruptedException {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        if(validateLogin(username, password)) {
            String Token = new UserLoginTask().execute(username, password).get();
            if (Token != null) {
                return Token;
            } else {
                Toast.makeText(this, "Utilisateur inexistant!", Toast.LENGTH_SHORT).show();
                return null;
            }
        }

        return null;
    }

}