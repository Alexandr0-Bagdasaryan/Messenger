package com.example.messenger.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.messenger.util.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG="MainViewModel";

    public MainViewModel(@NonNull Application application) {
        super(application);
        firebaseAuth=FirebaseAuth.getInstance();
    }
    private MutableLiveData<Boolean> correctData = new MutableLiveData<Boolean>(true);

    public MutableLiveData<Boolean> getCorrectData() {
        return correctData;
    }

    private MutableLiveData<Boolean> successAuth = new MutableLiveData<Boolean>();

    public MutableLiveData<Boolean> getSuccessAuth() {
        return successAuth;
    }

    private FirebaseAuth firebaseAuth;

    public void signInPassword(String email,String password){
        if (Util.dataCheck(email,password)){
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Log.d(TAG,"OK IN AUTH IN VIEWMODEL");
                    successAuth.setValue(true);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG,"FAIL IN AUTH IN VIEWMODEL");
                    successAuth.setValue(false);
                }
            });
        }
        else {
            correctData.setValue(false);
        }
    }


}
