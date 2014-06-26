package edu.sjsu.trustbasedreco.androidclient;

import edu.sjsu.trustbasedreco.R;
import edu.sjsu.trustbasedreco.R.id;
import edu.sjsu.trustbasedreco.R.layout;
import edu.sjsu.trustbasedreco.R.menu;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class MenusActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menus);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menus, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_user_profile:
			Intent intent = new Intent(this, UserProfileFragment.class);
			startActivity(intent);

		case R.id.menu_settings:
			Intent intent1 = new Intent(this, SettingsFragment.class);
			startActivity(intent1);

		case R.id.menu_trust_network:
			Intent intent2 = new Intent(this, TrustNetworkActivity.class);
			startActivity(intent2);

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_menus,
					container, false);
			return rootView;
		}
	}

}
