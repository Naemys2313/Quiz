package ru.naemys.quiz.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ru.naemys.quiz.R;
import ru.naemys.quiz.models.User;

public class SignActivity extends AppCompatActivity {

    public static final String TAG = SignActivity.class.getSimpleName();

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReferenceUsers;

    private EditText emailEditText,
            loginEditText,
            passwordEditText,
            repeatPasswordEditText;

    private Button signButton;

    private TextView toggleSignTextView;

    private boolean isSignIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        databaseReferenceUsers = database.getReference().child("users");

        initFields();
    }

    private void initFields() {
        emailEditText = findViewById(R.id.emailEditText);
        loginEditText = findViewById(R.id.loginEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);

        signButton = findViewById(R.id.signButton);

        toggleSignTextView = findViewById(R.id.toggleSignTextView);
    }

    private void signUp() {
        final String email = emailEditText.getText().toString().trim();
        final String login = loginEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String repeatPassword = repeatPasswordEditText.getText().toString().trim();

        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = auth.getCurrentUser();

                            User newUser = new User(user.getUid(), login, email, User.user);
                            databaseReferenceUsers.child(user.getUid()).setValue(newUser);

                            Toast.makeText(SignActivity.this, "Sign up success",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignActivity.this, "Sign up failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn() {
        final String email = emailEditText.getText().toString().trim();
        final String password = passwordEditText.getText().toString().trim();

        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            Toast.makeText(SignActivity.this, "Sign in success",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(SignActivity.this, "Sign in failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void sign(View view) {
        if (isSignIn) {
            signIn();
        } else {
            signUp();
        }
    }


    public void toggleSign(View view) {
        if (isSignIn) {
            signButton.setText(R.string.sign_up_button);
            toggleSignTextView.setText(R.string.sign_in_toggle_sign_text_view);

            loginEditText.setVisibility(View.VISIBLE);
            repeatPasswordEditText.setVisibility(View.VISIBLE);

            isSignIn = false;
        } else {
            signButton.setText(R.string.sign_in_button);
            toggleSignTextView.setText(R.string.sign_up_toggle_sign_text_view);

            loginEditText.setVisibility(View.GONE);
            repeatPasswordEditText.setVisibility(View.GONE);

            isSignIn = true;
        }
    }
}