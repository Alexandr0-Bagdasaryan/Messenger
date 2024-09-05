package com.example.messenger.viewModel;


import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordViewModel extends ViewModel {

    private MutableLiveData<Boolean> emailSend = new MutableLiveData<>();


    public LiveData<Boolean> getEmailSend() {
        return emailSend;
    }

    private MutableLiveData<String > error = new MutableLiveData<>();

    public LiveData<String> getError() {
        return error;
    }

    private FirebaseAuth auth;

    public ResetPasswordViewModel() {
        auth=FirebaseAuth.getInstance();
    }

    public void resetPassword(String email){
            auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    emailSend.setValue(true);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    error.setValue(e.getMessage());
                }
            });
    }

}
