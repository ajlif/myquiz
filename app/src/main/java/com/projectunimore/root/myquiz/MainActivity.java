package com.projectunimore.root.myquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // UI references.
    private EditText mPasswordView;
    private EditText mEmailView;

    //focus
    private View focusView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailView = findViewById(R.id.login_email);
        mPasswordView = findViewById(R.id.login_password);
        Intent intent = getIntent();
        if(intent.hasExtra("email"))
            {
            Bundle extras = getIntent().getExtras();
            String email = extras.getString("email");
            mEmailView.setText(email);
            focusView = mPasswordView;
            focusView.requestFocus();
            }

        // TODO: Grab an instance of FirebaseAuth

    }

    // Executed when Sign in button pressed
    public void signInExistingUser(View v)   {
        Intent intent = new Intent(this, com.projectunimore.root.myquiz.QuizActivity.class);
        finish();
        startActivity(intent);
        // TODO: Call attemptLogin() here
        //Toast.makeText(this, "SignIN", Toast.LENGTH_SHORT).show();
    }

    // Executed when Register button pressed
    public void registerNewUser(View v) {
        Intent intent = new Intent(this, com.projectunimore.root.myquiz.RegisterActivity.class);
        finish();
        startActivity(intent);
        //Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show();
    }

    // TODO: Complete the attemptLogin() method
    private void attemptLogin() {


        // TODO: Use FirebaseAuth to sign in with email & password


    }

    // TODO: Show error on screen with an alert dialog


}
