package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.messenger.viewModel.ResetPasswordViewModel;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonResetPassword;

    private ResetPasswordViewModel resetPasswordViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        initViews();
        resetPasswordViewModel= new ViewModelProvider(this).get(ResetPasswordViewModel.class);
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                resetPasswordViewModel.resetPassword(email);
            }
        });
        resetPasswordViewModel.getEmailSend().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean send) {
                if(send){
                    Toast.makeText(
                            ResetPasswordActivity.this,
                            "Mail with reset password link send",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                else{
                    Toast.makeText(
                            ResetPasswordActivity.this,
                            "Failed to send mail",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        resetPasswordViewModel.getEmailCorrect().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean correct) {
                if(!correct){
                    Toast.makeText(
                            ResetPasswordActivity.this,
                            "Please enter your email",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initViews(){
        editTextEmail=findViewById(R.id.editTextEmail);
        buttonResetPassword=findViewById(R.id.buttonResetPassword);
    }

    public static Intent newIntent(Context context){
        return new Intent(context, ResetPasswordActivity.class);
    }
}