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


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.example.messenger.viewModel.MainViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private EditText editTextPassword;
    private EditText editTextEmail;
    private TextView textViewForgotPassword;
    private TextView textViewRegister;
    private Button buttonLogin;

    private MainViewModel mainViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mainViewModel= new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getCorrectData().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean dataCorrect) {
                if(!dataCorrect){
                    Toast.makeText(MainActivity.this,"WRONG DATA",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mainViewModel.getSuccessAuth().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean auth) {
                if(auth){
                    Intent intent = UserActivity.newIntent(MainActivity.this);
                    startActivity(intent);
                    Log.d(TAG,"SUCCES AUTH");
                }
                else{
                    Toast.makeText(MainActivity.this,"Failed to login",Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"FAILED AUTH");
                }
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();
                mainViewModel.signInPassword(email,password);
            }
        });
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RegistrationActivity.newIntent(MainActivity.this);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = ResetPasswordActivity.newIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private void initViews() {
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail = findViewById(R.id.editTextEmail);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);
        textViewRegister = findViewById(R.id.textViewRegister);
        buttonLogin = findViewById(R.id.buttonLogin);
    }

    public static Intent newIntent(Context context){
        return new Intent(context,MainActivity.class);
    }


}