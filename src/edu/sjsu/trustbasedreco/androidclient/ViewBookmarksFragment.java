package edu.sjsu.trustbasedreco.androidclient;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.sjsu.trustbasedreco.R;
import edu.sjsu.trustbasedreco.library.ConnectionDetector;
import edu.sjsu.trustbasedreco.library.StringUtil;
import edu.sjsu.trustbasedreco.library.UserFunctions;
import edu.sjsu.trustbasedreco.library.WebServiceUtil;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewBookmarksFragment extends ListFragment {
	
	private ArrayList<String> names = new ArrayList<String>();
	private ArrayList<String> bookmarks = new ArrayList<String>();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		Log.i("VIEWBookmarksFrag", "Here");
		View v = inflater.inflate(R.layout.fragment_view_bookmarks, null);
		ListView lv = (ListView) v.findViewById(android.R.id.list);
		UserFunctions uf = new UserFunctions();
		Context context = getActivity().getApplicationContext();
	//	boolean userSignedIn = uf.isUserLoggedIn(context);
		//DatabaseHandler db = new DatabaseHandler(context);
		//HashMap<String, String> user = db.getUserDetails();
		//if (StringUtil.IS_LOGGEDIN) {
			try {
				if (new ConnectionDetector(context).isConnectingToInternet()) {
					Log.i("User email", StringUtil.USER_EMAIL);
					String bookmarksUrl = StringUtil.SERVER_URL + "TrustBasedRecommendation/bookmark/getauserbookmark?email=";
					bookmarksUrl += StringUtil.USER_EMAIL;
					new RequestBookmarksServiceTask().execute(bookmarksUrl);
				}
			} catch (Exception e) {
				
			//}
		//} else {
				// Show Sign in activity
				// Intent intent = new Intent(getActivity(), SignInActivity.class);
				// startActivity(intent);
		}
		return v;
	}

	private class RequestBookmarksServiceTask extends
			AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... params) {
			String allBookmarksUrl = params[0];
			Log.i("RequestBookmarksService", allBookmarksUrl);
			JSONArray bookmarksJSON = WebServiceUtil.requestWebService(allBookmarksUrl);
			try {
				if (bookmarksJSON != null) {
					Log.i("bookmarksResult", bookmarksJSON.toString());
					for (int j = 0; j < bookmarksJSON.length(); j++) {
						JSONObject bookmark = bookmarksJSON.getJSONObject(j);
						names.add(bookmark.getString("name"));
						bookmarks.add(bookmark.toString());
					}
				} else {
					Toast.makeText(getActivity(), "No bookmarks yet", 200).show();
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
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, names);
			int count = adapter.getCount();
			
			setListAdapter(adapter);
		}
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Log.i("bookmarks-onItemCLick", position + " - " + id);
		String item = (String) getListAdapter().getItem(position);
	    Intent intent = new Intent(getActivity(), BookmarkActivity.class);
	    intent.putExtra("bookmark", bookmarks.get(position));
	    startActivity(intent);
	}
}
