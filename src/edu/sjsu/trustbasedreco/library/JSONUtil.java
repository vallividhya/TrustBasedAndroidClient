package edu.sjsu.trustbasedreco.library;

import android.util.Log;

import com.google.gson.Gson;

import edu.sjsu.trustbasedreco.pojo.Bookmark;
import edu.sjsu.trustbasedreco.pojo.Bookmarkcollection;
import edu.sjsu.trustbasedreco.pojo.FourSquareVenue;
import edu.sjsu.trustbasedreco.pojo.Inviteemails;
import edu.sjsu.trustbasedreco.pojo.User;
import edu.sjsu.trustbasedreco.pojo.Useremail;

public class JSONUtil {
	
	public static String toUserJSON(User user) {
		Gson gson = new Gson();
		String json = gson.toJson(user);
		Log.i("USER_JSON",json);
		return json;
	}
	
	public static String toBookmarkJSON(Bookmarkcollection bookmark) {
		Gson gson = new Gson();
		String json = gson.toJson(bookmark);
		Log.i("BOOKMARK_JSON",json);
		return json;
	}
	
	public static String toInviteemailsJSON(Inviteemails inviteemails) {
		Gson gson = new Gson();
		String json = gson.toJson(inviteemails);
		Log.i("FRIENDSEMAIL_JSON",json);
		return json;
	}
	
	public static String toFourSquareVenueJSON(FourSquareVenue fsv) {
		Gson gson = new Gson();
		String json = gson.toJson(fsv);
		Log.i("SEARCHBYLOCATION_JSON",json);
		return json;
	}
	
	public static FourSquareVenue fromJSONtoVenue(String json) {
		FourSquareVenue fsvenue = new FourSquareVenue();
		Gson gson = new Gson();
		fsvenue = gson.fromJson(json, FourSquareVenue.class);
		return fsvenue;
	}

	public static <T> String toJson(T obj) {
		Gson gson = new Gson();
		return gson.toJson(obj);
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}
}
