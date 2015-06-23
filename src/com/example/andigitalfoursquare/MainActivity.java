package com.example.andigitalfoursquare;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.andigitalfoursquare.common.APICallbackInterface;
import com.example.andigitalfoursquare.common.APICallsManager;
import com.example.andigitalfoursquare.common.Const;
import com.example.andigitalfoursquare.model.Place;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {
	
	static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		context = this;
		
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			fourquareFetchLocation("");
			
			return rootView;
		}
	}
	
	
	protected static void fourquareFetchLocation(String searchQuery) {
    	
		
		APICallbackInterface apiCallback = new APICallbackInterface() {

			@Override
			public void onRespondRecived(String result) {
				// TODO Auto-generated method stub
				
				JSONObject jsonObj;
				try {
					jsonObj = new JSONObject(result);
					
					
						try {
							JSONArray venuesArray = jsonObj.getJSONArray("venues");
							
				

							for (int i = 0; i < venuesArray.length(); i++) {
								
								JSONArray categoriesArray = venuesArray.getJSONObject(i).getJSONArray("categories");
								
								Place item = new Place();
								item.setName(categoriesArray.getJSONObject(0).getString("name"));
								
				
							}

							
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
						
					
					
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
			}

			@Override
			public void startAPIGetTask() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void startAPIPostTask() {
				// TODO Auto-generated method stub
				

			}
			
		};
	
        
        Map<String,String> paramsHeader = new HashMap<String, String>();
        paramsHeader.put("Content-Type","application/x-www-form-urlencoded");
        
        
        String urlString = "https://api.foursquare.com/v2/venues/search?client_id="
				+ Const.CLIENT_ID
				+ "&client_secret="
				+ Const.CLIENT_SECRET
				+ "&v=20130815"
				+ "&near=london"
				+ "&query="
				+ Uri.encode(searchQuery);
       
        new APICallsManager(context, "GET",apiCallback, urlString, paramsHeader);
        
	}
	
	public void onItemClick(int pos) {
		
	}



}
