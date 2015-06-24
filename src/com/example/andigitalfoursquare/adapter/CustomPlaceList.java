package com.example.andigitalfoursquare.adapter;


import java.util.ArrayList;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.example.andigitalfoursquare.MainActivity.PlaceholderFragment;
import com.example.andigitalfoursquare.R;
import com.example.andigitalfoursquare.common.AppController;
import com.example.andigitalfoursquare.model.Place;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomPlaceList extends BaseAdapter implements OnClickListener{
	private Context context;
	private Fragment fragment;
	private ArrayList<Place> dataItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private LayoutInflater mInflater;
	
	
	public CustomPlaceList(Context context, Fragment fragment, ArrayList<Place> dataItems) {
		this.context = context;
		this.fragment = fragment;
		this.dataItems = dataItems;
		mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		
	}
	
	public void CustomPlaceListUpdate(ArrayList<Place> dataItems) {
		this.dataItems = dataItems;
	}

	@Override
	public int getCount() {
		return dataItems.size();
	}

	@Override
	public Object getItem(int location) {
		return dataItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if (imageLoader == null)
			imageLoader = AppController.getInstance().getImageLoader();
		
		ViewHolder vHolder = null;
		
		if (convertView == null) {
			vHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.place_row, null);
			
			vHolder.icon_picture = (NetworkImageView) convertView.findViewById(R.id.i_picture);
			vHolder.rankLabel = (TextView) convertView.findViewById(R.id.no_label); 
			vHolder.NameLabel = (TextView) convertView.findViewById(R.id.name_label); 
			
			convertView.setTag(vHolder);
        } else {
        	vHolder = (ViewHolder) convertView.getTag();
		}
		
		
		
		Place mPlace= dataItems.get(position);
		
		vHolder.icon_picture.setImageUrl(mPlace.getIconURL(), imageLoader);
		vHolder.rankLabel.setText(Integer.toString(position+1));
		vHolder.NameLabel.setText(mPlace.getName());
		
		convertView.setOnClickListener(new OnItemClickListener( position ));

		return convertView;
	}
	
	public static class ViewHolder {
		TextView rankLabel;
		TextView NameLabel;
		public NetworkImageView icon_picture;
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		

	}
	
	/********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements OnClickListener{           
        private int mPosition;
         
        OnItemClickListener(int position){
             mPosition = position;
             
             
        }
         
        @Override
        public void onClick(View arg0) {

   
        	PlaceholderFragment sct = (PlaceholderFragment)fragment;
        	
        		sct.onItemClick(mPosition);
            
            
        }               
    }   


}


