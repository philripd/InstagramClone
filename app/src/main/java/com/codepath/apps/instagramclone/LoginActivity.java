package com.codepath.apps.instagramclone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

	public static final String TAG = "LoginActivity";
	private EditText etUsername;
	private EditText etPassword;
	private Button btnLogin;
	private Button btnSignUp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		if (ParseUser.getCurrentUser() != null) {
			goMainActivity();
		}

		etUsername = findViewById(R.id.etUsername);
		etPassword = findViewById(R.id.etPassword);
		btnLogin = findViewById(R.id.btnLogin);
		btnSignUp = findViewById(R.id.btnSignUp);

		btnLogin.setOnClickListener(v -> {
			Log.i(TAG, "onClick login button");
			String username = etUsername.getText().toString();
			String password = etPassword.getText().toString();
			loginUser(username, password);
		});

		btnSignUp.setOnClickListener(v -> {
			Log.i(TAG, "onClick sign up button");
			String username = etUsername.getText().toString();
			String password = etPassword.getText().toString();
			signUpUser(username, password);
		});
	}

	private void loginUser(String username, String password) {
		Log.i(TAG, "Attempting to login user " + username);
		ParseUser.logInInBackground(username, password, (user, e) -> {
			if (e != null) {
				Log.e(TAG, "Issue with login", e);
				Toast.makeText(LoginActivity.this, "Issue with login!", Toast.LENGTH_SHORT).show();
				return;
			}
			goMainActivity();
			Toast.makeText(LoginActivity.this, "Login success!", Toast.LENGTH_SHORT).show();
		});
	}

	private void signUpUser(String username, String password) {
		Log.i(TAG, "Attempting to sign up user with username " + username);
		ParseUser user = new ParseUser();
		user.setUsername(username);
		user.setPassword(password);

		user.signUpInBackground(e -> {
			if (e != null) {
				Log.e(TAG, "Issue with sign up", e);
				Toast.makeText(LoginActivity.this, "Issue with sign up!", Toast.LENGTH_SHORT).show();
				return;
			}
			goMainActivity();
			Toast.makeText(LoginActivity.this, "Sign up success!", Toast.LENGTH_SHORT).show();
		});
	}

	private void goMainActivity() {
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
	}
}
