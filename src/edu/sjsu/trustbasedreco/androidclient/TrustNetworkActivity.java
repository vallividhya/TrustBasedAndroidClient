package edu.sjsu.trustbasedreco.androidclient;

import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import edu.sjsu.trustbasedreco.R;
import edu.sjsu.trustbasedreco.R.id;
import edu.sjsu.trustbasedreco.R.layout;
import edu.sjsu.trustbasedreco.R.menu;
import edu.sjsu.trustbasedreco.library.ConnectionDetector;
import edu.sjsu.trustbasedreco.library.DatabaseHandler;
import edu.sjsu.trustbasedreco.library.JSONUtil;
import edu.sjsu.trustbasedreco.library.StringUtil;
import edu.sjsu.trustbasedreco.library.UserFunctions;
import edu.sjsu.trustbasedreco.library.UserLookUp;
import edu.sjsu.trustbasedreco.library.WebServiceUtil;
import edu.sjsu.trustbasedreco.pojo.Category;
import edu.sjsu.trustbasedreco.pojo.Friend;
import edu.sjsu.trustbasedreco.pojo.Useremail;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.annotation.SuppressLint;
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

public class TrustNetworkActivity extends ListFragment {

	private ArrayList<String> friends = new ArrayList<String>();
	private ArrayList<String> names = new ArrayList<String>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("FriendsFragment", "Here");
		View v = inflater.inflate(R.layout.fragment_trust_network, null);
		ListView lv = (ListView) v.findViewById(android.R.id.list);

		Context context = getActivity().getApplicationContext();
		//DatabaseHandler db = new DatabaseHandler(context);
		//HashMap<String, String> user = db.getUserDetails();

		try {
			// if (userSignedIn) {
			if (new ConnectionDetector(context).isConnectingToInternet()) {
				String friendsUrl = "http://54.187.117.106:8080/TrustBasedRecommendation/bookmark/getallfriendsofuser?email=";
				friendsUrl += StringUtil.USER_EMAIL;
				new RequestFriendsServiceTask().execute(friendsUrl);
			}
		} catch (Exception e) {

		}
		return v;
	}

	private class RequestFriendsServiceTask extends
			AsyncTask<String, Void, Void> {

		@SuppressLint("NewApi")
		@Override
		protected Void doInBackground(String... params) {
			String allFriendsUrl = params[0];
			Log.i("RequestFriendsService", allFriendsUrl);
			Log.i("RequestFriendsService", allFriendsUrl);
			JSONObject friendsJSON = WebServiceUtil
					.requestService(allFriendsUrl);
			try {
				if (friendsJSON != null) {
					Log.i("FriendsResult", friendsJSON.toString());
						JSONArray friendsJson = new JSONArray(friendsJSON.getString("friends"));
						for (int j = 0; j < friendsJson.length(); j++) {
							JSONObject friendJson = friendsJson.getJSONObject(j);

							Friend friend = new Friend();

							String email = friendJson.getString("user");
							friend.setName(friendJson.getString("name"));
							friend.setFriendEmail(email);
							
							JSONArray categoriesJson = friendJson.getJSONArray("categories");
							for (int i = 0; i < categoriesJson.length(); i++) {
								JSONObject categoryJson = categoriesJson.getJSONObject(i);
								friend.addScore(categoryJson.getString("category"), categoryJson.getInt("score"));
							}

							Log.i("Friend", email + " - " + friend.getName());

							names.add(friend.getName());
							friends.add(JSONUtil.toJson(friend));
					}
				} else {
					Log.i("FRIENDSJson", "friendsJSON is emapty");
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void unused) {
			// setListAdapter must not be called at doInBackground()
			// since it would be executed in separate Thread
			setListAdapter(new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_list_item_1, names));
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.i("friends-onItemCLick", position + " - " + id);
		Intent intent = new Intent(getActivity(), FriendActivity.class);
		intent.putExtra("friend", friends.get(position));
		startActivity(intent);
	}

}
