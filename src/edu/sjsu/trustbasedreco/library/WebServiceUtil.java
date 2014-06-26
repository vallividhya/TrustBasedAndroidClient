package edu.sjsu.trustbasedreco.library;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class WebServiceUtil {
	private static final int CONNECTION_TIMEOUT = 2000;
	private static final int DATARETRIEVAL_TIMEOUT = 5000;
	private static JSONObject jo;
	static JSONArray respArray;
	public static JSONArray requestWebService(String serviceUrl) {
		// disableConnectionReuseIfNecessary();

		HttpURLConnection urlConnection = null;
		try {
			// create connection
			URL urlToRequest = new URL(serviceUrl);
			urlConnection = (HttpURLConnection) urlToRequest.openConnection();
			urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
			urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);
			urlConnection.setRequestMethod("GET");
			Log.i("WebServiceUtil", "Going good so far");
			// handle issues
			int statusCode = urlConnection.getResponseCode();
			Log.i("HTTPRequest", "SCode" + statusCode);
			if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
				// handle unauthorized (if service requires user login)
				Log.e("HTTP_ERROR", "Unauthorized");
			} else if (statusCode != HttpURLConnection.HTTP_OK) {
				// handle any other errors, like 404, 500,..
				Log.e("HTTP_ERROR", "400/500");
			}

			// create JSON object from content
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			 try {
				respArray = new JSONArray(getResponseText(in));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			Log.e("WEBSERVICEUtil", "URL malformed");
			// URL is invalid
		} catch (SocketTimeoutException e) {
			// data retrieval or connection timed out
			Log.e("WEBSERVICEUtil", "Time out connection");
		} catch (IOException e) {
			// could not read response body
			// (could not create input stream)
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return respArray;
	}

	private static String getResponseText(InputStream inStream) {
		String resp = new Scanner(inStream).useDelimiter("\\A").next();
		Log.i("ViewBookmarksResp", resp);
		return resp;
	}
	
	public static JSONObject requestService(String serviceUrl) {
		// disableConnectionReuseIfNecessary();

		HttpURLConnection urlConnection = null;
		try {
			// create connection
			URL urlToRequest = new URL(serviceUrl);
			urlConnection = (HttpURLConnection) urlToRequest.openConnection();
			urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
			urlConnection.setReadTimeout(DATARETRIEVAL_TIMEOUT);
			urlConnection.setRequestMethod("GET");
			Log.i("WebServiceUtil", "Going good so far");
			// handle issues
			int statusCode = urlConnection.getResponseCode();
			Log.i("HTTPRequest", "SCode" + statusCode);
			if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
				// handle unauthorized (if service requires user login)
				Log.e("HTTP_ERROR", "Unauthorized");
			} else if (statusCode != HttpURLConnection.HTTP_OK) {
				// handle any other errors, like 404, 500,..
				Log.e("HTTP_ERROR", "400/500");
			}

			// create JSON object from content
			InputStream in = new BufferedInputStream(
					urlConnection.getInputStream());
			 try {
				 jo = new JSONObject(getResponseText(in));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} catch (MalformedURLException e) {
			Log.e("WEBSERVICEUtil", "URL malformed");
			// URL is invalid
		} catch (SocketTimeoutException e) {
			// data retrieval or connection timed out
			Log.e("WEBSERVICEUtil", "Time out connection");
		} catch (IOException e) {
			// could not read response body
			// (could not create input stream)
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return jo;
	}
}
