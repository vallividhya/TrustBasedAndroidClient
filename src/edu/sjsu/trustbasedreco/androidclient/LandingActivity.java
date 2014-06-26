package edu.sjsu.trustbasedreco.androidclient;

import java.security.KeyStore.TrustedCertificateEntry;
import java.util.ArrayList;

import edu.sjsu.trustbasedreco.R;
import edu.sjsu.trustbasedreco.library.NavDrawerListAdapter;
import edu.sjsu.trustbasedreco.library.TabsPagerAdapter;
import android.support.v4.app.ActionBarDrawerToggle;
//import android.support.v7.app.ActionBar.Tab;
//import android.support.v7.app.ActionBar.TabListener;
//import android.support.v7.app.ActionBarActivity;
//import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.os.Build;
import edu.sjsu.trustbasedreco.pojo.NavDrawerItem;



@SuppressLint("NewApi")
public class LandingActivity extends FragmentActivity implements ActionBar.TabListener {

	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	private ActionBar actionBar;
	private String[] tabs = {"Search Nearby", "My Bookmarks", "Trust Network", "What's Hot"};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

       /* if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
        
        viewPager = (ViewPager) findViewById(R.id.pager);
        actionBar = getActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);        
 
        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }
        
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    }
    
   public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.layout.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
        switch (item.getItemId()) {
        case R.id.menu_user_profile:
        	Intent intent = new Intent(this,
					UserProfileFragment.class);
			startActivity(intent);
        	return true;
        case R.id.menu_settings:
        	Intent intent2 = new Intent(this,
					SettingsFragment.class);
			startActivity(intent2);
            return true;
        case R.id.menu_trust_network:
        	Intent intent3 = new Intent(this,
					TrustNetworkActivity.class);
			startActivity(intent3);
        	return true;
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
            View rootView = inflater.inflate(R.layout.fragment_landing, container, false);
            return rootView;
        }
    }

	@Override
	public void onTabReselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onTabSelected(Tab tab, android.app.FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
	}



	@Override
	public void onTabUnselected(Tab tab, android.app.FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	/*End of Tab Navigation*/
	
	// Add Friend for trust network activity 
	public void addNewFriend(View v) {
		Log.i("AddNewFriend", "In landing activity");
		Intent intent = new Intent(this, AddFriendActivity.class);
	 	startActivity(intent);
	}
	
	// Search nearby locations
	
	public void getFourSquareVenues(View v) {
		 Log.i("SEARCH_NEARBY_FRAGMENT", "get4squarevenues");
		 Intent intent = new Intent(this, SearchByLocationActivity.class);
		 startActivity(intent);
	 }
	 
	 public void searchLocationsByName(View v) {
		 Log.i("SEARCH_NEARBY_FRAGMENT", "get4squarevenuesbyname"); 
		 Intent intent = new Intent(this, SearchByNameActivity.class);
		 startActivity(intent);
	 }
	 
	 public void checkWhatsHot(View v) {
		 Intent i = new Intent(this, RecommendationsActivity.class);
			startActivity(i);
	 }
}
