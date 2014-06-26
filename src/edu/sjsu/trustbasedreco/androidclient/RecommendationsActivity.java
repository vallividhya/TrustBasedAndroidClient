package edu.sjsu.trustbasedreco.androidclient;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.sjsu.trustbasedreco.R;
import edu.sjsu.trustbasedreco.R.layout;
import edu.sjsu.trustbasedreco.library.ConnectionDetector;
import edu.sjsu.trustbasedreco.library.DatabaseHandler;
import edu.sjsu.trustbasedreco.library.JSONUtil;
import edu.sjsu.trustbasedreco.library.StringUtil;
import edu.sjsu.trustbasedreco.library.UserFunctions;
import edu.sjsu.trustbasedreco.library.WebServiceUtil;
import edu.sjsu.trustbasedreco.pojo.Bookmark;
import edu.sjsu.trustbasedreco.pojo.Bookmarkcollection;
import edu.sjsu.trustbasedreco.pojo.FourSquareVenue;
import edu.sjsu.trustbasedreco.pojo.User;
import edu.sjsu.trustbasedreco.pojo.Venue;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class RecommendationsActivity extends ListActivity {

	private List<String> names = new ArrayList<String>();
	private List<String> bookmarks = new ArrayList<String>();
	
	JSONArray respArray;
	
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 setTheme(android.R.style.Theme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_recommendations);
		Log.i("Recommendations", "Here");
		lv = (ListView) findViewById(android.R.id.list);
		String findNearVenueCity = "Santa Clara";//city.getText().toString();
		
		UserFunctions uf = new UserFunctions();
		Context context = getApplicationContext();
		//boolean userSignedIn = uf.isUserLoggedIn(context);
		//DatabaseHandler db = new DatabaseHandler(context);
		//HashMap<String, String> user = db.getUserDetails();
		
		String userEmail = StringUtil.USER_EMAIL;
		if (userEmail.isEmpty()) {
			userEmail = StringUtil.USER_EMAIL;
		}
		
		try {
			if (new ConnectionDetector(context).isConnectingToInternet()) {
				StringBuilder sb = new StringBuilder();
				sb.append(StringUtil.SERVER_URL);
				sb.append("TrustBasedRecommendation/trustbasedtecommendationservice/gettrustrecommendation");
				String recoUrl = sb.toString();
				Log.i("RECO_URL",recoUrl);
				
				User u = new User();
				u.setEmail(userEmail);
				String parameters = JSONUtil.toUserJSON(u);
				
				new RequestRecommendationServiceTask().execute(recoUrl, parameters);
			}

		} catch (Exception e) {
			
		}
	}
	
	private class RequestRecommendationServiceTask extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			String recoUrl = params[0];
			String postParams = params[1];
			Log.i("RequestRecoService", recoUrl);
			int statusCode; 
			try {
				URL url = null;
				try {
					url = new URL(recoUrl);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/json");

				PrintWriter out = new PrintWriter(conn.getOutputStream());
				out.print(postParams);
				out.close();
				// handle issues
				statusCode = conn.getResponseCode();
				if (statusCode != HttpURLConnection.HTTP_OK) {
					Log.e("HTTP_ERROR", "500 - Internal Server Error");
				} else {
					Log.i("HTTP_OK", "Getting recos Success :)");
					InputStream in = new BufferedInputStream(
							conn.getInputStream());
					 try {
						respArray = new JSONArray(getResponseText(in));
						if (respArray != null) {
							for (int j = 0; j < respArray.length(); j++) {
								JSONObject bookmark = respArray.getJSONObject(j);
								names.add(bookmark.getString("name"));

								Bookmark bm = new Bookmark();
								bm.setName(bookmark.getString("name"));
								bm.setLocation(bookmark.getString("location"));
								bm.setCategory(bookmark.getString("category"));
								bm.setStats(bookmark.getString("stats"));
								bm.setTried(bookmark.getBoolean("tried"));
								bm.setStatus(bookmark.getString("status"));

								bookmarks.add(JSONUtil.toJson(bm));
							}
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//FourSquareVenue fs = JSONUtil.fromJSONtoVenue(venuesResp);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}

		private String getResponseText(InputStream inStream) {
			String resp = new Scanner(inStream).useDelimiter("\\A").next();
			Log.i("VenuesResponse X0x0", resp);
			return resp;
		}

		@Override
		protected void onPostExecute(Void unused) {
			setListAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, names));
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	    Intent intent = new Intent(this, RecoActivity.class);
	    intent.putExtra("bookmark", bookmarks.get(position));
	    startActivity(intent);
	}

}