package edu.sjsu.trustbasedreco.androidclient;

import java.util.ArrayList;
import java.util.HashMap;

import edu.sjsu.trustbasedreco.R;
import edu.sjsu.trustbasedreco.library.ConnectionDetector;
import edu.sjsu.trustbasedreco.library.DatabaseHandler;
import edu.sjsu.trustbasedreco.library.StringUtil;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.os.Build;

public class WhatsHotActivity extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_whats_hot, null);
		return v;
	}

}
