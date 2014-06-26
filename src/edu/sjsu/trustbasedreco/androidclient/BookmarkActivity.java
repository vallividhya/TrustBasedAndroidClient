package edu.sjsu.trustbasedreco.androidclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import edu.sjsu.trustbasedreco.R;
import edu.sjsu.trustbasedreco.R.id;
import edu.sjsu.trustbasedreco.R.layout;
import edu.sjsu.trustbasedreco.R.menu;
import edu.sjsu.trustbasedreco.androidclient.SignInActivity.PlaceholderFragment;
import edu.sjsu.trustbasedreco.library.DatabaseHandler;
import edu.sjsu.trustbasedreco.library.JSONUtil;
import edu.sjsu.trustbasedreco.library.StringUtil;
import edu.sjsu.trustbasedreco.pojo.Bookmarkcollection;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BookmarkActivity extends ActionBarActivity {

	EditText bName;
	EditText bCategory;
	EditText bLoc;
	EditText bTried;
	EditText bPopular;
	EditText bLikeIt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_bookmark);

		Bundle extras = getIntent().getExtras();
		String bookmark = "Oops! No popcorn for you.";
		if (extras != null) {
			bookmark = extras.getString("bookmark");
		}
		JSONObject jobj;
		try {
			bName = (EditText) findViewById(R.id.BName);
			bCategory = (EditText) findViewById(R.id.BCategory);
			bLoc = (EditText) findViewById(R.id.BLocation);
			bTried = (EditText) findViewById(R.id.BTried);
			bPopular = (EditText) findViewById(R.id.BPopular);
			bLikeIt = (EditText) findViewById(R.id.BStatus);
			
			jobj = new JSONObject(bookmark);
			
			String txt = jobj.getString("name");
			bName.setText(txt);
			bCategory.setText(jobj.getString("category"));
			bLoc.setText(jobj.getString("location"));
			boolean tried = jobj.getBoolean("tried");
			String triedtxt = "Nope! not yet";
			if (tried == true) {
				triedtxt = "Yes";
			}
			bTried.setText(triedtxt);
			bPopular.setText(jobj.getString("stats"));
			bLikeIt.setText(jobj.getString("status"));
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

	public void editBookmark(View view) {
		Log.i("BookmarkActivity", "Edit Bookmark clicked");
		
		bName = (EditText) findViewById(R.id.BName);
		bCategory = (EditText) findViewById(R.id.BCategory);
		bLoc = (EditText) findViewById(R.id.BLocation);
		bTried = (EditText) findViewById(R.id.BTried);
		bPopular = (EditText) findViewById(R.id.BPopular);
		bLikeIt = (EditText) findViewById(R.id.BStatus);
		
		Bookmarkcollection bookmark = new Bookmarkcollection();
		
		String editName = bName.getText().toString();
		bookmark.setName(editName);
		
		String editCategory = bCategory.getText().toString();
		bookmark.setCategory(editCategory);
		
		String editTried = bTried.getText().toString();
		Log.i("Tried? ", editTried); 
		if (editTried.equals("Yes")) {
			bookmark.setTried(true);
		} else {
			bookmark.setTried(false);
		}
		
		String editLoc = bLoc.getText().toString();
		bookmark.setLocation(editLoc);
		
		String editStats = bPopular.getText().toString();
		bookmark.setStats(editStats);
		
		String editStatus = bLikeIt.getText().toString();
		bookmark.setStatus(editStatus);
		
		DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());
		String useremail = StringUtil.USER_EMAIL;
		if (useremail.isEmpty()) {
			useremail = StringUtil.USER_EMAIL;
		}
		bookmark.setEmail(useremail);

		String bookmarkJSON = JSONUtil.toBookmarkJSON(bookmark);
		Log.i("bookmarkJSON", bookmarkJSON);
		try {
			int statusCode = editUserBookmark(bookmarkJSON);
			if (statusCode == 200) {
				Log.i("updateBokkmarkAPI", statusCode + "");
				Toast.makeText(getApplicationContext(), "Saved succesfully", 200).show();
				
			} else {
				Toast.makeText(getApplicationContext(), "Something went wrong. Please try again!", 200).show();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteBookmark(View view) {
		Log.i("BookmarkActivity", "Delete Bookmark clicked");
	}

//	private class BookmarkService extends AsyncTask<Params, Progress, Result> {
		
	//}
	
	public int editUserBookmark (String postParameters) throws IOException {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);

		int statusCode;
		try {
			String editUrl = StringUtil.SERVER_URL + "TrustBasedRecommendation/bookmark/updatebookmark";
			URL url = new URL(editUrl);
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
				Log.e("UpdateBookmark", "500 - Internal Server Error");
			} else {
				Log.i("UpdateBookmark", "Bookmark updated:)");
				Intent i = new Intent(this, LandingActivity.class);
				startActivity(i);
			}
			// Makes sure that the InputStream is closed after the app is
			// finished using it.
		} finally {

		}
		return statusCode;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bookmark, menu);
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
}
