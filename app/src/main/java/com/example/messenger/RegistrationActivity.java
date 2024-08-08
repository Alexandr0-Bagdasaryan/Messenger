package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.messenger.viewModel.RegistrationViewModel;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {


    private static final String TAG = "RegistrationActivity";

    private EditText editTextPassword;
    private EditText editTextEmail;
    private TextView textViewForgotPassword;
    private TextView textViewLogin;
    private Button buttonRegister;

    private RegistrationViewModel registrationViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews();
        registrationViewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);
        registrationViewModel.getCorrectData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean dataCorrect) {
                if(!dataCorrect){
                    Toast.makeText(RegistrationActivity.this,"WRONG DATA",Toast.LENGTH_SHORT).show();
                }
            }
        });
        registrationViewModel.getSuccessAuth().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean auth) {
                if(auth){
                    Intent intent = UserActivity.newIntent(RegistrationActivity.this);
                    startActivity(intent);
                }
                else{
                    Log.d(TAG,"FAILED AUTH");
                }
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                registrationViewModel.signUpPassword(email,password);
            }
        });
        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.newIntent(RegistrationActivity.this);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ResetPasswordActivity.newIntent(RegistrationActivity.this);
                startActivity(intent);
            }
        });
    }

    private void initViews(){
         editTextPassword=findViewById(R.id.editTextPassword);
         editTextEmail=findViewById(R.id.editTextEmail);
         textViewForgotPassword=findViewById(R.id.textViewForgotPassword);
         textViewLogin=findViewById(R.id.textViewLogin);
         buttonRegister=findViewById(R.id.buttonRegister);
    }

    public static Intent newIntent(Context context){
        return new Intent(context,RegistrationActivity.class);
    }
}