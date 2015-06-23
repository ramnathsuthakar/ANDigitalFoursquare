package com.example.andigitalfoursquare.common;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class APICallsManager extends AsyncTask<String,Void,String> {
	
	private APICallbackInterface mAPICallback;
    private Context act;
    private String method;
    RequestQueue queue;
    
    Map<String, String>  paramsPostData;
    Map<String, String>  paramsHeader;
    String APIURL;


    public APICallsManager(Context ctx, String api_method,APICallbackInterface mAPICallback, String APIURL, Map<String, String>  paramsPostData, Map<String, String>  paramsHeader) {
        act=ctx;
        method=api_method;
        this.mAPICallback = mAPICallback;
        queue = Volley.newRequestQueue(ctx);
        this.paramsPostData = paramsPostData;
        this.paramsHeader = paramsHeader;
        this.APIURL = APIURL;

    }
    
    public APICallsManager(Context ctx, String api_method,APICallbackInterface mAPICallback, String APIURL, Map<String, String>  paramsHeader) {
        act=ctx;
        method=api_method;
        this.mAPICallback = mAPICallback;
        queue = Volley.newRequestQueue(ctx);
        this.paramsHeader = paramsHeader;
        this.APIURL = APIURL;

    }


	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		if(method.equalsIgnoreCase("GET")) {
			APIGet();
		} else {
			APIPost();
		}
		
		return null;
	}
	
	@Override
    protected void onPostExecute(String results) {
		
        mAPICallback.onRespondRecived(results);

    }
	
	
	
	protected void APIPost() {
		

		StringRequest postRequest = new StringRequest(Request.Method.POST, APIURL, new Response.Listener<String>() 
		    {
		        @Override
		        public void onResponse(String response) {
		            // response
		            Log.d("Response", response);
		            mAPICallback.onRespondRecived(response);
		        }
		    }, 
		    new Response.ErrorListener() 
		    {
		         @Override
		         public void onErrorResponse(VolleyError error) {
		             // error
		             Log.d("Error.Response", error.toString());
		       }
		    }
		) {     
		    @Override
		    protected Map<String, String> getParams() 
		    { 
		            return paramsPostData;  
		    }
		    
		    @Override
		    public Map<String, String> getHeaders() throws AuthFailureError {
		        return paramsHeader;
		    }
		};
		
		queue.add(postRequest);
		
		
	}
	
	protected void APIGet() {
		 
		// prepare the Request
		JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, APIURL, null,
		    new Response.Listener<JSONObject>() 
		    {
		        @Override
		        public void onResponse(JSONObject response) {   
		             // display response     
		            Log.d("Response", response.toString());
		            mAPICallback.onRespondRecived(response.toString());
		        }
		    }, 
		    new Response.ErrorListener() 
		    {
		         @Override
		         public void onErrorResponse(VolleyError error) {            
		            Log.d("Error.Response", error.toString());
		         }
		    }
		) {
			@Override
		    public Map<String, String> getHeaders() throws AuthFailureError {
		        return paramsHeader;
		    }
		};
		 
		// add it to the RequestQueue   
		queue.add(getRequest);
	}

}
