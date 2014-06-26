package edu.sjsu.trustbasedreco.androidclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import edu.sjsu.trustbasedreco.R;
import edu.sjsu.trustbasedreco.library.DatabaseHandler;
import edu.sjsu.trustbasedreco.library.JSONUtil;
import edu.sjsu.trustbasedreco.library.StringUtil;
import edu.sjsu.trustbasedreco.pojo.Bookmarkcollection;
import edu.sjsu.trustbasedreco.pojo.Venue;
import android.support.v7.app.ActionBarActivity;
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
import android.widget.TextView;
import android.widget.Toast;

public class VenueActivity extends ActionBarActivity {

	EditText vName;
	EditText vAddress;
	EditText vCity;
	EditText vCategory;
	EditText vPopular;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_venue);

		Bundle extras = getIntent().getExtras();
		String venueJson = "Oops! No popcorn for you.";
		if (extras != null) {
			venueJson = extras.getString("venue");
		}

		Venue venue = JSONUtil.fromJson(venueJson, Venue.class);
		Log.i("venue", venue.getName());
		Log.i("venue", venue.getAddress());
		Log.i("venue", venue.getCity());
		Log.i("venue", venue.getCategory());
		
		vName = (EditText) findViewById(R.id.venueName);
		vAddress = (EditText) findViewById(R.id.venueAddress);
		vCity = (EditText) findViewById(R.id.venueCity);
		vCategory = (EditText) findViewById(R.id.venueCategory);
		vPopular = (EditText) findViewById(R.id.venuePopular);
		
		vName.setText(venue.getName());
		vAddress.setText(venue.getAddress());
		vCity.setText(venue.getCity());
		vCategory.setText(venue.getCategory());
		vPopular.setText(venue.getStats());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.venue, menu);
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

		
	public void addBookmark(View v) {
		Log.i("VENUE_ACTIVITY","addbookmark");
		
		vName = (EditText) findViewById(R.id.venueName);
		vAddress = (EditText) findViewById(R.id.venueAddress);
		vCity = (EditText) findViewById(R.id.venueCity);
		vCategory = (EditText) findViewById(R.id.venueCategory);
		vPopular = (EditText) findViewById(R.id.venuePopular);
		
		Bookmarkcollection bookmark = new Bookmarkcollection();
		
		String editName = vName.getText().toString();
		bookmark.setName(editName);
		
		String editCategory = vCategory.getText().toString();
		bookmark.setCategory(editCategory);
		
		bookmark.setTried(false);	
		
		String editLoc = vCity.getText().toString();
		bookmark.setLocation(editLoc);
		
		String editStats = vPopular.getText().toString();
		bookmark.setStats(editStats);
		
		if (StringUtil.IS_LOGGEDIN) {
			DatabaseHandler dbh = new DatabaseHandler(getApplicationContext());
			String useremail = StringUtil.USER_EMAIL;
			if (useremail.isEmpty()) {
				useremail = StringUtil.TEMP_EMAIL;
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
		} else {
			Intent intent = new Intent(this, SignInActivity.class);
			startActivity(intent);
		}
	}
	
	public int editUserBookmark (String postParameters) throws IOException {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();

		StrictMode.setThreadPolicy(policy);

		int statusCode;
		try {
			String urlForAddBookmark = StringUtil.SERVER_URL + "TrustBasedRecommendation/bookmark/updatebookmark";
			URL url = new URL(urlForAddBookmark);
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

}
