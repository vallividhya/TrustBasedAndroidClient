package edu.sjsu.trustbasedreco.androidclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import edu.sjsu.trustbasedreco.R;
import edu.sjsu.trustbasedreco.R.id;
import edu.sjsu.trustbasedreco.R.layout;
import edu.sjsu.trustbasedreco.R.menu;
import edu.sjsu.trustbasedreco.library.JSONUtil;
import edu.sjsu.trustbasedreco.library.StringUtil;
import edu.sjsu.trustbasedreco.pojo.Category;
import edu.sjsu.trustbasedreco.pojo.Friend;
import edu.sjsu.trustbasedreco.pojo.TrustScoreCollection;
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
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class FriendActivity extends ActionBarActivity {

	EditText frndName;
	String frndEMail;
	int tvs[] = { R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5,
			R.id.tv_6, R.id.tv_7, R.id.tv_8, R.id.tv_9, R.id.tv_10, R.id.tv_11 };
	int ets[] = { R.id.et_1, R.id.et_2, R.id.et_3, R.id.et_4, R.id.et_5,
			R.id.et_6, R.id.et_7, R.id.et_8, R.id.et_9, R.id.et_10, R.id.et_11 };
	EditText updatedScores[] = new EditText[11];
	TextView categories[] = new TextView[11];
	String categoryNames[] = new String[11];
	String scores[] = new String[11];
	EditText et_travel;
	StringBuffer updateJSON = new StringBuffer();
	String user = StringUtil.USER_EMAIL;
	String name = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_friend);

		Bundle extras = getIntent().getExtras();
		String friendJson = "All is well!";
		if (extras != null) {
			friendJson = extras.getString("friend");
		}
		Friend friend = JSONUtil.fromJson(friendJson, Friend.class);

		name = friend.getName();
		frndEMail = friend.getFriendEmail();
		Log.i("Friend Name + Email:", name + " - " + frndEMail);

		List<Category> categories = friend.getCategories();

		frndName = (EditText) findViewById(R.id.FrndName);
		frndName.setText(name);

		LinearLayout layout = (LinearLayout) findViewById(R.id.FriendLayout);
		int tvnum = 0, etnum = 0;
		for (Category category : categories) {
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
			LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(150,
					100);

			TextView tv = new TextView(getApplicationContext());
			tv.setLayoutParams(lp);
			tv.setId(tvs[tvnum]);
			tv.setText(category.getName());
			layout.addView(tv);

			EditText et = new EditText(getApplicationContext());
			et.setId(ets[etnum]);
			et.setLayoutParams(lp1);
			et.setText("" + category.getScore());
			layout.addView(et);

			tvnum++;
			etnum++;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.friend, menu);
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

	public void updateFriendData(View v) {
		Log.i("FRIEND_ACTIVITY", "update Score/category");
		frndName = (EditText) findViewById(R.id.FrndName);
		updateJSON.append("[");
		for (int k = 0; k < 10; k++) {

			TrustScoreCollection trustScoreObj = new TrustScoreCollection();
			trustScoreObj.setUser(user);
			trustScoreObj.setFriend(frndEMail);
			trustScoreObj.setExplicit("true");

			updatedScores[k] = (EditText) findViewById(ets[k]);
			scores[k] = updatedScores[k].getText().toString();
			trustScoreObj.setTrustscore(Double.parseDouble(scores[k]));

			categories[k] = (TextView) findViewById(tvs[k]);
			categoryNames[k] = categories[k].getText().toString();
			trustScoreObj.setCategory(categoryNames[k]);
			Log.i(categoryNames[k], scores[k]);

			String partialJSON = JSONUtil.toJson(trustScoreObj);
			updateJSON.append(partialJSON);
			if (k != 9) {
				updateJSON.append(",");
			}
		}

		updateJSON.append("]");
		Log.i("UpdateJSON", updateJSON.toString());

		// Make HTTP Call
		try {
			int responseCode = updateScores(updateJSON.toString());
			if (responseCode == 200) {
				Log.i("updateScoresAPI", responseCode + "");
				Toast.makeText(getApplicationContext(), "Score Updated", 250).show();
				Intent i = new Intent(this, LandingActivity.class);
				startActivity(i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int updateScores(String postParameters) throws IOException {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
		.permitAll().build();

		StrictMode.setThreadPolicy(policy);

		int statusCode;
		try {
			String updateScoreUrl = StringUtil.SERVER_URL + "TrustBasedRecommendation/friends/updateTrustscoretofriend";
				URL url = new URL(updateScoreUrl);
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
					Log.e("UpdateTrustScore", "500 - Internal Server Error");
				} else {
					Log.i("UpdateTrustScore", "Scores updated:)");
				}
				// Makes sure that the InputStream is closed after the app is
				// finished using it.
			} finally {

			}
			return statusCode;
		}

	public void deleteFriend(View v) {
		Log.i("FRIEND_ACTIVITY", "delete friend");
	}
}
