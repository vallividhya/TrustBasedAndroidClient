package edu.sjsu.trustbasedreco.androidclient;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.sjsu.trustbasedreco.R;
import edu.sjsu.trustbasedreco.library.JSONParser;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.os.Build;

public class ShowBookmarksFragment extends ListActivity {
	
	private Context context;
	// Replace with hosted url
	private static String getBookmarksUrl = "http:localhost:8000/getUserBookmarks?userid=";
	
	private static final String BOOKMARKNAME = "BName";
	private static final String BOOKMARKID = "BId";
	private static final String BOOKMARKTRIED = "BTried";
	private static final String BOOKMARKSTATUS = "BStatus";
	
	ArrayList<HashMap<String,String>> bookmarksJsonList = new ArrayList<HashMap<String,String>>();
	
	ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// How to remove this stub { 
			// Uncomment setContentView.
			// Remove the rest. 
		//}
		//setContentView(R.layout.fragment_show_bookmarks);
		String[] values = new String[] { "Amore Cafe", "Curry Pudits", "Ikes",
		        "Power Bowl" };
		    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		        android.R.layout.simple_list_item_1, values);
		    setListAdapter(adapter);
		  }
	
	@Override
	  protected void onListItemClick(ListView l, View v, int position, long id) {
	    String item = (String) getListAdapter().getItem(position);
	    Toast.makeText(this, item + " ", Toast.LENGTH_LONG).show();
	    Intent intent = new Intent(this, BookmarkActivity.class);
    	startActivity(intent);
	  //  setContentView(R.layout.fragment_edit_bookmark);
	  }
	
	private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog dialog;
 
        private ListActivity activity;
 
        // private List<Message> messages;
        public ProgressTask(ListActivity activity) {
            this.activity = activity;
            context = activity;
            dialog = new ProgressDialog(context);
        }


        private Context context;
        
        protected void onPreExecute() {
            this.dialog.setMessage("Loading bookmarks..");
            this.dialog.show();
        }
 
        @Override
        protected void onPostExecute(final Boolean success) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            ListAdapter adapter = new SimpleAdapter(context, bookmarksJsonList,
                    R.layout.fragment_bookmark, new String[] { BOOKMARKNAME, BOOKMARKTRIED,
            		BOOKMARKSTATUS}, new int[] {
                            R.id.BName, R.id.BTried, R.id.BStatus });
 
            setListAdapter(adapter);
 
            // select single ListView item
             lv = getListView();
        }



	@Override
	protected Boolean doInBackground(String... arg0) {
		// Parse JSON in background
		/*JSONParser jParser = new JSONParser();
		 
        // get JSON data from URL
        JSONArray json = jParser.getJSONFromUrl(getBookmarksUrl, );
        for (int i = 0; i < json.length(); i++) {
        	 
            try {
                JSONObject c = json.getJSONObject(i);
                
                String bookmarkName = c.getString(BOOKMARKNAME);
                String bookMarkTried = c.getString(BOOKMARKTRIED);
                String bookMarkStatus = c.getString(BOOKMARKSTATUS);

                HashMap<String, String> map = new HashMap<String, String>();

                // Add child node to HashMap key & value
                map.put(BOOKMARKNAME, bookmarkName);
                map.put(BOOKMARKTRIED, bookMarkTried);
                map.put(BOOKMARKSTATUS, bookMarkStatus);
                bookmarksJsonList.add(map);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
*/
		return null;
	}
	
	}
}


