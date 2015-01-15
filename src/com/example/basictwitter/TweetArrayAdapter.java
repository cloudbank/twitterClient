package com.example.basictwitter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basictwitter.model.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {
	Context context;

	public TweetArrayAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet t = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		View v;
		if (convertView == null) {
			v = LayoutInflater.from(getContext()).inflate(R.layout.tweet_item,
					parent, false);
		} else {
			v = convertView;
		}
		// Lookup view for data population
		ImageView iv = (ImageView) v.findViewById(R.id.ivProfileImage);
		TextView screen = (TextView) v.findViewById(R.id.tvScreenName);
		TextView user = (TextView) v.findViewById(R.id.tvUserName);
		TextView timestamp = (TextView) v.findViewById(R.id.tvTimeStamp);
		TextView body = (TextView) v.findViewById(R.id.tvBody);
		iv.setImageResource(android.R.color.transparent); // clear image
		ImageLoader il = ImageLoader.getInstance();
		// Populate the data into the template view using the data object
		try {
			il.displayImage(t.getUser().getProfileImageUrl(), iv);
		} catch (Exception e) {
			e.printStackTrace();
			// no image
		}
		screen.setText(t.getUser().getScreen_name());
		user.setText("@" + t.getUser().getName());
		timestamp.setText(DateUtility.twitterTime(t.getTimestamp(), context));

		body.setText(t.getBody());
		// Return the completed view to render on screen
		return v;
	}

	
}
