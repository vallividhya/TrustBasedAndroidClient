package edu.sjsu.trustbasedreco.androidclient;

import java.io.IOException;

import org.json.JSONObject;

import edu.sjsu.trustbasedreco.R;
import edu.sjsu.trustbasedreco.library.ConnectionDetector;
import edu.sjsu.trustbasedreco.library.DatabaseHandler;
import edu.sjsu.trustbasedreco.library.JSONUtil;
import edu.sjsu.trustbasedreco.library.StringUtil;
import edu.sjsu.trustbasedreco.library.UserFunctions;
import edu.sjsu.trustbasedreco.pojo.User;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class SignUpActivity extends ActionBarActivity {

	String signUpUrl = StringUtil.SERVER_URL + "TrustBasedRecommendation/restservice/adduser";
	Button btnRegister;
	EditText inputFirstName;
	EditText inputEmail;
	EditText inputPassword;
	EditText inputPhone;
	EditText inputZip;
	EditText inputCity;
	TextView registerErrorMsg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_sign_up);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
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

	public void signUpUser(View view) {

		inputFirstName = (EditText) findViewById(R.id.signup_fname);
		inputEmail = (EditText) findViewById(R.id.signup_email);
		inputPassword = (EditText) findViewById(R.id.signup_password);
		inputCity = (EditText) findViewById(R.id.signup_city);
		inputZip = (EditText) findViewById(R.id.signup_zip);
		btnRegister = (Button) findViewById(R.id.btnRegister);

		String fname = inputFirstName.getText().toString();
		String city = inputCity.getText().toString();
		String password = inputPassword.getText().toString();
		String email = inputEmail.getText().toString();
		int zip = Integer.parseInt(inputZip.getText().toString());

		if (fname.isEmpty() || password.isEmpty() || email.isEmpty()) {
			Toast.makeText(getApplicationContext(), "User data missing",
					Toast.LENGTH_LONG).show();
		} else {
			User user = new User();
			user.setName(fname);
			user.setEmail(email);
			user.setPassword(password);
			user.setCity(city);
			user.setZip(zip);
			
			String userJSON = JSONUtil.toUserJSON(user);
			if (new ConnectionDetector(getApplicationContext())
					.isConnectingToInternet()) {
				UserFunctions userfunctions = new UserFunctions();
				try {
					int status = userfunctions.signUp(signUpUrl, userJSON);
					Log.d(ACTIVITY_SERVICE,
							"User signed up. Bookmarks activity loading");
					if (status == 200) {
		            	DatabaseHandler dbHandler = new DatabaseHandler(getApplicationContext());
		            	//dbHandler.addUser(user.getName(), user.getEmail(), user.getCity(), user.getZip());
		            	//StringUtil.IS_LOGGEDIN = true;
		            	StringUtil.USER_EMAIL = email;
						Intent intent = new Intent(this,LandingActivity.class);
						startActivity(intent);
					} else {
						Toast.makeText(getApplicationContext(), "Oops! Something went wrong! Please try again", 100);
						// Alert user to check form data.
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
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
			View rootView = inflater.inflate(R.layout.fragment_sign_up,
					container, false);
			return rootView;
		}
	}

}
