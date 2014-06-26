package edu.sjsu.trustbasedreco.androidclient;

import java.io.IOException;

import edu.sjsu.trustbasedreco.R;
import edu.sjsu.trustbasedreco.library.ConnectionDetector;
import edu.sjsu.trustbasedreco.library.JSONUtil;
import edu.sjsu.trustbasedreco.library.StringUtil;
import edu.sjsu.trustbasedreco.library.UserFunctions;
import edu.sjsu.trustbasedreco.pojo.User;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class SignInActivity extends ActionBarActivity {

	String signInUrl = StringUtil.SERVER_URL +"TrustBasedRecommendation/login/signin";
	private Button btnLogin;
	private Button btnLinkToRegister;
	private EditText inputEmail;
	private EditText inputPassword;
	private TextView loginErrorMsg;
	private ConnectionDetector cd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_sign_in);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * Sign in user - checks session and navigates to bookmarks activity.
	 */
	public void signInUser(View view) {
		
		// Importing all assets like buttons, text fields
 		inputEmail = (EditText) findViewById(R.id.login_email);
 		inputPassword = (EditText) findViewById(R.id.login_password);
 		btnLogin = (Button) findViewById(R.id.btnLogin);
 		btnLinkToRegister = (Button) findViewById(R.id.btnSignUp);

 		cd = new ConnectionDetector(getApplicationContext());
		
		String email = inputEmail.getText().toString();
		String password = inputPassword.getText().toString();
		
		if (email.isEmpty() || password.isEmpty()) {
			Log.d(ACTIVITY_SERVICE, "Fields empty");
			Toast.makeText(getApplicationContext(),
					"Email & password fields cannot be empty",
					Toast.LENGTH_LONG).show();
		} else if (email.length() < 2 || password.length() < 3) {
			Log.d(ACTIVITY_SERVICE, "Email length Error");
			Toast.makeText(getApplicationContext(),
					"Password must be minimum 8 characters", Toast.LENGTH_LONG)
					.show();
		} else {
			User user = new User();
			user.setEmail(email);
			user.setPassword(password);
			String userJSON = JSONUtil.toUserJSON(user);
			
			if (new ConnectionDetector(getApplicationContext())
			.isConnectingToInternet()) {
				UserFunctions userfunctions = new UserFunctions();
				try {
					int status = userfunctions.signIn(signInUrl, userJSON);
					Log.d(ACTIVITY_SERVICE,
							"User signed up. Bookmarks activity loading");
					if (status == 200) {
						StringUtil.IS_LOGGEDIN = true;
						Toast.makeText(getApplicationContext(), "Happy entrusting :)", 250).show();
						Intent i = new Intent(this, LandingActivity.class);
						startActivity(i);
					} else {
						Toast.makeText(getApplicationContext(), "Something went wrong. Please try again", 250).show();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		   }
		}
	}

	public void loadSignUpActivity(View view) {
		Log.d(ACTIVITY_SERVICE, "Sign Up activity loading");
		Intent intent = new Intent(this, SignUpActivity.class);
		startActivity(intent);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_sign_in, container,
					false);
			return rootView;
		}
	}

}
