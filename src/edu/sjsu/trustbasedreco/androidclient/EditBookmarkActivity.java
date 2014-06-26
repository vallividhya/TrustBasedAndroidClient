package edu.sjsu.trustbasedreco.androidclient;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.DefaultHttpClient;

import edu.sjsu.trustbasedreco.R;
import edu.sjsu.trustbasedreco.R.id;
import edu.sjsu.trustbasedreco.R.layout;
import edu.sjsu.trustbasedreco.R.menu;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class EditBookmarkActivity extends ActionBarActivity {
	
	String deleteBookmarkurl = "http://localhost:8000/bookmark/delete"; // Set Delete bookmark URL here
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_edit_bookmark);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_bookmark, menu);
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

		
	public void deleteBookmark(String itemId) throws ClientProtocolException, IOException {
		
		/*deleteBookmarkurl += itemId;
		// Delete bookmark, DELETE REST request
		HttpClient httpclient = new DefaultHttpClient();
    	HttpDelete delete = new HttpDelete(deleteBookmarkurl);
    	delete.addHeader("accept", "application/json");
    	httpclient.execute(delete);*/
		//Intent intent = new Intent(this, ViewBookmarksFragment.class);
		//startActivity(intent);
	}

}
