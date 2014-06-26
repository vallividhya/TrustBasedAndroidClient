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
import edu.sjsu.trustbasedreco.library.ConnectionDetector;
import edu.sjsu.trustbasedreco.library.DatabaseHandler;
import edu.sjsu.trustbasedreco.library.JSONUtil;
import edu.sjsu.trustbasedreco.library.StringUtil;
import edu.sjsu.trustbasedreco.library.UserFunctions;
import edu.sjsu.trustbasedreco.pojo.FourSquareVenue;
import edu.sjsu.trustbasedreco.pojo.Venue;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SearchByNameActivity extends ListActivity {

	private List<String> venueNames = new ArrayList<String>();
	private List<String> venues = new ArrayList<String>();
	
	JSONObject respObj;
	
	
	ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		 setTheme(android.R.style.Theme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_search_by_name);
		Log.i("SearchByLocationFragment", "Here");
		lv = (ListView) findViewById(android.R.id.list);
		
		Bundle extras = getIntent().getExtras();
		String findNearVenueCity = "Palo Alto";
		if (extras != null) {
			findNearVenueCity = extras.getString("location");
		} else {
			findNearVenueCity = "Sunnyvale";
		}
		
		Context context = getApplicationContext();
		try {
			if (new ConnectionDetector(context).isConnectingToInternet()) {
				StringBuilder sb = new StringBuilder();
				sb.append(StringUtil.SERVER_URL);
				sb.append("TrustBasedRecommendation/foursquareservice/getVenueByNearLocation");
				String getVenuesUrl = sb.toString();
				Log.i("VENUES_URL",getVenuesUrl);
				
				FourSquareVenue fsv = new FourSquareVenue();
				fsv.setFindNearVenueCity(findNearVenueCity);
				String parameters = JSONUtil.toFourSquareVenueJSON(fsv);
				
				new RequestLocationServiceTask().execute(getVenuesUrl, parameters);
			}

		} catch (Exception e) {
			
		}
	}

	private class RequestLocationServiceTask extends AsyncTask<String, Void, Void> {
		@Override
		protected Void doInBackground(String... params) {
			String venuesUrl = params[0];
			String postParams = params[1];
			Log.i("RequestLocationService", venuesUrl);
			int statusCode; 
			try {
				URL url = null;
				try {
					url = new URL(venuesUrl);
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
					Log.i("HTTP_OK", "Getting venues Success :)");
					InputStream in = new BufferedInputStream(
							conn.getInputStream());
					 try {
						respObj = new JSONObject(getResponseText(in));
						if (respObj != null) {
							JSONArray names = new JSONArray(respObj.getString("venueNameList"));
							JSONArray addresses = new JSONArray(respObj.getString("venueAddressList"));
							JSONArray cities = new JSONArray(respObj.getString("venueCityList"));
							JSONArray categories = new JSONArray(respObj.getString("venueCategoryList"));
							JSONArray stats = new JSONArray(respObj.getString("statsList"));
							
							for (int i = 0; i < names.length(); i++) {
								Venue venue = new Venue();
								venue.setName(names.getString(i));
								venue.setAddress(addresses.getString(i));
								venue.setCity(cities.getString(i));
								venue.setCategory(categories.getString(i));
								venue.setStats(stats.getString(i));
								venueNames.add(venue.getName());
								venues.add(JSONUtil.toJson(venue));
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
			setListAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, venueNames));
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
	    Intent intent = new Intent(this, VenueActivity.class);
	    intent.putExtra("venue", venues.get(position));
	    startActivity(intent);
	}
}

