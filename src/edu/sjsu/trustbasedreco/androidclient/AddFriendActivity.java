package edu.sjsu.trustbasedreco.androidclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.sjsu.trustbasedreco.R;
import edu.sjsu.trustbasedreco.R.id;
import edu.sjsu.trustbasedreco.R.layout;
import edu.sjsu.trustbasedreco.R.menu;
import edu.sjsu.trustbasedreco.library.ConnectionDetector;
import edu.sjsu.trustbasedreco.library.DatabaseHandler;
import edu.sjsu.trustbasedreco.library.JSONUtil;
import edu.sjsu.trustbasedreco.library.StringUtil;
import edu.sjsu.trustbasedreco.pojo.Inviteemails;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class AddFriendActivity extends ActionBarActivity {

	EditText friendEmailTxt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_add_friend);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_friend, menu);
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

	public void inviteFriend(View v) {
		Log.i("AddFriendActivity", "Invite Friend");
		friendEmailTxt = (EditText) findViewById(R.id.friend_email);
		String friendEmail = friendEmailTxt.getText().toString();

		Inviteemails iemails = new Inviteemails();
		iemails.addEmail(friendEmail);
		//DatabaseHandler dbhandler = new DatabaseHandler(getApplicationContext());
		String useremail = StringUtil.USER_EMAIL;
		if(useremail.isEmpty()) {
			useremail = StringUtil.USER_EMAIL;
		}
		iemails.setSenderemail(useremail);
		String emailsJSON = JSONUtil.toInviteemailsJSON(iemails);
		Log.i("friends emails JSON", emailsJSON);
		if (new ConnectionDetector(getApplicationContext())
				.isConnectingToInternet()) {
			try {
				int status = sendEmailsToFriends(emailsJSON);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public int sendEmailsToFriends(String postParams) throws IOException {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);
		Log.i("PostParams- inviteFrnds", postParams);
		int statusCode;
		try {
			URL url = new URL(
					"http://192.168.0.104:8080/TrustBasedRecommendation/friends/invitefriends");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			PrintWriter out = new PrintWriter(conn.getOutputStream());
			out.print(postParams);
			out.close();
			// handle issues
			statusCode = conn.getResponseCode();
			if (statusCode == 400) {
				Log.e("InviteFriend", "400 Bad Request");
			} else if(statusCode == 500) {
				Log.e("InviteFriend","500 - Internal Server Error"); 
			} else if (statusCode != 200) {
				Log.e("InviteFriend", "Server Problem");
			} else if (statusCode == 200){
				Log.i("InviteFriend", "Invited friend :)");
				Toast.makeText(getApplicationContext(), "Friend invited! Make sure to give your friend a trust score", 200).show();
				Intent intent = new Intent(this,LandingActivity.class);
				startActivity(intent);
			}
			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} finally {

		}
		return statusCode;
	}
}
