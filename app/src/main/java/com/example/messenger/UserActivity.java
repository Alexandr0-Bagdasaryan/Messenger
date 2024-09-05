package com.example.messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.messenger.adapter.UsersAdapter;
import com.example.messenger.model.User;
import com.example.messenger.viewModel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class UserActivity extends AppCompatActivity {

    private static final String TAG = "UserActivity";

    private static String EXTRA_CURRENT_USER_ID = "currentId";

    private RecyclerView recyclerViewUsers;
    private UsersAdapter usersAdapter;
    private UserViewModel userViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initRecyclerView();
        observeViewModel();
        String currId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        usersAdapter.setOnUserClick(new UsersAdapter.OnUserClick() {
            @Override
            public void click(User user) {
                String currId = FirebaseAuth.getInstance().getUid();
                Intent intent = ChatActivity.newIntent(UserActivity.this, currId, user.getId());
                startActivity(intent);
            }
        });
    }


    private void observeViewModel() {
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUser().observe(this, new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser firebaseUser) {
                if (firebaseUser == null) {
                    Intent intent = LoginActivity.newIntent(UserActivity.this);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
        userViewModel.getUserList().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                usersAdapter.setUsers(users);
            }
        });
    }

    private void initRecyclerView() {
        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        usersAdapter = new UsersAdapter();
        recyclerViewUsers.setAdapter(usersAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.item_logout) {
            userViewModel.logout();
        }
        return super.onOptionsItemSelected(item);
    }


    public static Intent newIntent(Context context, String currId) {
        Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID,currId);
        return intent;
    }

    @Override
    protected void onResume() {
        super.onResume();
        userViewModel.setUserOnline(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        userViewModel.setUserOnline(false);
    }
}