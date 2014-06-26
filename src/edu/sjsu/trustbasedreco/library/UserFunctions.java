package edu.sjsu.trustbasedreco.library;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import edu.sjsu.trustbasedreco.pojo.Bookmark;
import edu.sjsu.trustbasedreco.pojo.Bookmarkcollection;
import edu.sjsu.trustbasedreco.pojo.User;
import android.content.Context;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

public class UserFunctions {

	private static final String DEBUG_TAG = "SignUpUser";
	private static String register_tag = "register";
	private JSONParser jsonParser;
	
	public UserFunctions() {
		// jsonParser = new JSONParser();
	}

	
	/*
	 * @Override protected String doInBackground(String... urls) { try { return
	 * signUpUrl(urls[0]); } catch (IOException e) { // TODO Auto-generated
	 * catch block e.printStackTrace(); } return null;
	 * 
	 * }
	 */

	public int signUp(String signUpUrl, String postParameters)
			throws IOException {

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);

		int statusCode;
		try {
			URL url = new URL(signUpUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			PrintWriter out = new PrintWriter(conn.getOutputStream());
			out.print(postParameters);
			out.close();
			// handle issues
			statusCode = conn.getResponseCode();
			if (statusCode != HttpURLConnection.HTTP_OK) {
				Log.e(DEBUG_TAG, "500 - Internal Server Error");
			} else {
				Log.i(DEBUG_TAG, "Sign Up - Success :)");
			}
			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} finally {

		}
		return statusCode;

	}

	public int signIn(String signInUrl, String postParameters)
			throws IOException {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);

		int statusCode;
		try {
			URL url = new URL(signInUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			PrintWriter out = new PrintWriter(conn.getOutputStream());
			out.print(postParameters);
			out.close();
			// handle issues
			statusCode = conn.getResponseCode();
			if (statusCode != HttpURLConnection.HTTP_OK) {
				Log.e(DEBUG_TAG, "500 - Internal Server Error");
			} else {
				Log.i(DEBUG_TAG, "Ooo hooo");
			}
			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} finally {

		}
		return statusCode;
	}

	public boolean signOut(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		db.resetTables();
		return true;
	}

	public boolean isUserLoggedIn(Context context) {
		DatabaseHandler db = new DatabaseHandler(context);
		int count = db.getRowCount();
		if (count > 0) {
			// user logged in
			return true;
		}
		return false;
	}

}
