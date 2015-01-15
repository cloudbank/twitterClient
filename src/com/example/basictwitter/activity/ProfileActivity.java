package com.example.basictwitter.activity;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.basictwitter.R;
import com.example.basictwitter.TwitterApp;
import com.example.basictwitter.R.id;
import com.example.basictwitter.R.layout;
import com.example.basictwitter.model.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		loadInfo();

	}

	private void loadInfo() {
		TwitterApp.getRestClient().getMyInfo(new JsonHttpResponseHandler() {

			@Override
			public void onFailure(Throwable e, JSONObject json) {
				Log.d("DEBUG", "loadInfo() failed" + e + json);
			}

			@Override
			public void onSuccess(JSONObject json) {
				User u = User.fromJson(json);
				Log.d("DEBUG", "&&&" + json.toString());
				getActionBar().setTitle("@" + u.getScreen_name());
				populateProfileHeader(u);
			}

			private void populateProfileHeader(User u) {
				TextView name = (TextView) findViewById(R.id.name);
				TextView tagline = (TextView) findViewById(R.id.screen);
				TextView followers =  (TextView) findViewById(R.id.followers);
				TextView following = (TextView) findViewById(R.id.following);
				ImageView image = (ImageView) findViewById(R.id.profImage);
				name.setText(u.getName());
				tagline.setText(u.getTagline());
				followers.setText(u.getFollowers() + " Followers");
				following.setText(u.getFollowing()+" Following");
				ImageLoader.getInstance().displayImage(u.getProfileImageUrl(),image);
				
			}

		});
	}
}
