package edu.sjsu.trustbasedreco.library;

import edu.sjsu.trustbasedreco.androidclient.RecommendationsActivity;
import edu.sjsu.trustbasedreco.androidclient.SearchNearbyFragment;
import edu.sjsu.trustbasedreco.androidclient.TrustNetworkActivity;
import edu.sjsu.trustbasedreco.androidclient.ViewBookmarksFragment;
import edu.sjsu.trustbasedreco.androidclient.WhatsHotActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {
		switch (index) {
			case 0:
				return new SearchNearbyFragment();
			case 1:
				return new ViewBookmarksFragment();
			case 2:
				return new TrustNetworkActivity();
			case 3:
				return new WhatsHotActivity();
		}
		return null;
	}

	@Override
	public int getCount() {
		return 4;
	}

}
