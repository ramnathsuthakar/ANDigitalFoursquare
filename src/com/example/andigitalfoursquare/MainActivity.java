package com.example.andigitalfoursquare;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.andigitalfoursquare.adapter.CustomPlaceList;
import com.example.andigitalfoursquare.common.APICallbackInterface;
import com.example.andigitalfoursquare.common.APICallsManager;
import com.example.andigitalfoursquare.common.AsyncTaskManager;
import com.example.andigitalfoursquare.common.Const;
import com.example.andigitalfoursquare.model.Place;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	
	
	static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context =this;
		
		
		
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
		
		public ListView listView;
		public CustomPlaceList adapter;
		public ArrayList<Place> dataItems;
		AutoCompleteTextView searchbox;
		Button search_button;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			
			dataItems = new ArrayList<Place>();
			fourquareFetchLocation("sushi");
			listView = (ListView) rootView.findViewById(R.id.listview);
			adapter = new CustomPlaceList(getActivity().getApplicationContext(), this,dataItems);
			listView.setAdapter(adapter);
			
			
			searchbox = (AutoCompleteTextView) rootView.findViewById(R.id.search_auto);
			
			searchbox.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				@Override
				public boolean onEditorAction(TextView v, int actionId,
						KeyEvent event) {
					
					if (actionId == EditorInfo.IME_ACTION_SEARCH) {
						
						fourquareFetchLocation(searchbox.getText().toString());
						
			            return true;
			        }
					
				
					
					return false;
					
					
				}
			});
			
			search_button = (Button) rootView.findViewById(R.id.search_button);
			search_button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					fourquareFetchLocation(searchbox.getText().toString());
				}
				
			});

			
			return rootView;
		}
		

		
		
		public void fourquareFetchLocation(String searchQuery) {
			
			// Clear
			if(dataItems != null)
				dataItems.clear();
	    	
			Log.d("Search", "Search String: = " + searchQuery);
			APICallbackInterface apiCallback = new APICallbackInterface() {

				@Override
				public void onRespondRecived(String result) {
					// TODO Auto-generated method stub
					
					Log.d("RESULT", "Result: = " + result);
					
					JSONObject jsonObj;
					try {
						jsonObj = new JSONObject(result);
						
						
							try {
								JSONObject responseObject = jsonObj.getJSONObject("response");
								
								JSONArray venuesArray = responseObject.getJSONArray("venues");
								
								
								for (int i = 0; i < venuesArray.length(); i++) {
									
									JSONArray categoriesArray = venuesArray.getJSONObject(i).getJSONArray("categories");
									
									JSONObject iconObject = categoriesArray.getJSONObject(0).getJSONObject("icon");
									
									Place item = new Place();
									item.setName(categoriesArray.getJSONObject(0).getString("name"));
									
									String iURL = iconObject.getString("prefix")+"88"+iconObject.getString("suffix");
									
									Log.d("ICON", "Icon IMage: - " + iURL);
									item.setIconURL(iURL);
									dataItems.add(item);
									
								}
								
								

								
							} catch (JSONException e) {
								e.printStackTrace();
							}
							
							
						
							adapter.CustomPlaceListUpdate(dataItems);
							adapter.notifyDataSetChanged();
						
						
						
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
	       
	        //new APICallsManager(context, "GET",apiCallback, urlString, paramsHeader);
	        AsyncTaskManager.executeConcurrently(new APICallsManager(getActivity().getApplicationContext(), "GET",apiCallback, urlString, paramsHeader));
	        
	        
	        
		}
		
		public void onItemClick(int pos) {
			
		}

	}
	
	
	


}
