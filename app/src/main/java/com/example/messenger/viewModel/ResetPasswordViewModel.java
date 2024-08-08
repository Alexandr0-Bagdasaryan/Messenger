package com.example.messenger.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.messenger.util.Util;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> emailSend = new MutableLiveData<>();


    public MutableLiveData<Boolean> getEmailSend() {
        return emailSend;
    }

    private MutableLiveData<Boolean> emailCorrect = new MutableLiveData<>();

    public MutableLiveData<Boolean> getEmailCorrect() {
        return emailCorrect;
    }

    private FirebaseAuth auth;

    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);
        auth=FirebaseAuth.getInstance();
    }

    public void resetPassword(String email){
        if (Util.checkEmail(email)) {
            auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    emailSend.setValue(true);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    emailSend.setValue(false);
                }
            });
        }
        else{
            emailCorrect.setValue(false);
        }
    }

}
