package com.example.messenger;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth auth;

    private EditText editTextPassword;
    private EditText editTextLogin;
    private Button buttonRegister;
    private Button buttonLogout;
    private Button buttonCHECK;
    private Button buttonLogin;
    private Button buttonResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        auth = FirebaseAuth.getInstance();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = editTextLogin.getText().toString();
                String password = editTextPassword.getText().toString();
                auth.createUserWithEmailAndPassword(login, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "SUCCESSFUL REGISTER");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "FAILED TO REGISTER"+e.getMessage());
                    }
                });
            }
        });
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Log.d(TAG,"LOGOUT");
            }
        });
        buttonCHECK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this,""+auth.getCurrentUser(),Toast.LENGTH_SHORT).show();
            }
        });
        auth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser()==null){
                    Toast.makeText(MainActivity.this,"Logout",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this,"Logined",Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = editTextLogin.getText().toString();
                String password = editTextPassword.getText().toString();
                auth.signInWithEmailAndPassword(login,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG,"SUCCESSFUL LOGIN");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"FAILED LOGIN");
                    }
                });
            }
        });
        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = editTextLogin.getText().toString();
                auth.sendPasswordResetEmail(login).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG,"GOT EMAIL? SOME SUCCESS");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"FAILURE TO RESET PASS");
                    }
                });
            }
        });
    }


    private void initViews() {
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextLogin = findViewById(R.id.editTextLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        buttonLogout = findViewById(R.id.buttonLogout);
        buttonCHECK = findViewById(R.id.buttonCHECK);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonResetPassword = findViewById(R.id.buttonResetPassword);
    }
}