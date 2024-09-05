package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.messenger.util.Util;
import com.example.messenger.viewModel.LoginViewModel;
import com.google.firebase.auth.FirebaseUser;


public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText editTextPassword;
    private EditText editTextEmail;
    private TextView textViewForgotPassword;
    private TextView textViewRegister;
    private Button buttonLogin;

    private LoginViewModel loginViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        observeViewModel();
        setViewListeners();
    }

    private void initViews() {
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        textViewRegister = findViewById(R.id.textViewRegister);
        buttonLogin = findViewById(R.id.buttonLogin);
    }

    private void observeViewModel() {
        loginViewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser != null) {
                    Toast.makeText(
                                    LoginActivity.this,
                                    "Authorized",
                                    Toast.LENGTH_SHORT)
                            .show();
                    Intent intent = UserActivity.newIntent(LoginActivity.this,firebaseUser.getUid());
                    startActivity(intent);
                    finish();
                }
            }
        });
        loginViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String error) {
                if (error != null) {
                    Toast.makeText(LoginActivity.this, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setViewListeners() {
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Util.getTrimText(editTextEmail);
                String password = Util.getTrimText(editTextPassword);
                loginViewModel.signInPassword(email, password);
            }
        });
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RegistrationActivity.newIntent(LoginActivity.this);
                startActivity(intent);
            }
        });
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Util.getTrimText(editTextEmail);
                Intent intent = ResetPasswordActivity.newIntent(LoginActivity.this, email);
                startActivity(intent);
            }
        });
    }


    public static Intent newIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }


}