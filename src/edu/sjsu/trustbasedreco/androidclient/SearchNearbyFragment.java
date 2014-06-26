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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.EditText;
import android.os.Build;

public class SearchNearbyFragment extends Fragment {

	EditText et;
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	        Bundle savedInstanceState) {
		    View v = inflater.inflate(R.layout.fragment_search_nearby, null);
		 	et = (EditText) v.findViewById(R.id.searchCity);
		 	String city = et.getText().toString();
		 	Intent in = new Intent(getActivity(), SearchByNameActivity.class);
		 	in.putExtra("location", city);
	        // Inflate the layout for this fragment
	        return v;
	    }
}