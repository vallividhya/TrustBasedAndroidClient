package edu.sjsu.trustbasedreco.androidclient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
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
import edu.sjsu.trustbasedreco.library.WebServiceUtil;
import edu.sjsu.trustbasedreco.pojo.FourSquareVenue;
import edu.sjsu.trustbasedreco.pojo.Useremail;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class SearchByLocationActivity  {
	
}
