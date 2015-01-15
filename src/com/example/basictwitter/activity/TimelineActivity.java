package com.example.basictwitter.activity;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;

import com.example.basictwitter.R;
import com.example.basictwitter.R.drawable;
import com.example.basictwitter.R.id;
import com.example.basictwitter.R.layout;
import com.example.basictwitter.R.menu;
import com.example.basictwitter.fragments.HomeTimelineFragment;
import com.example.basictwitter.fragments.MentionsTimelineFragment;
import com.example.basictwitter.listeners.FragmentTabListener;
import com.example.basictwitter.model.Tweet;

public class TimelineActivity extends FragmentActivity {
    // tweets that are created when mentions tab is selected
	ArrayList<Tweet> fragmentTweets = new ArrayList<Tweet>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		setupFragmentTabs();
	}

	private void setupFragmentTabs() {

		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
				.newTab()
				.setText("Home")
				.setIcon(R.drawable.home_)
				.setTag("HomeTimelineFragment")
				.setTabListener(
						new FragmentTabListener<HomeTimelineFragment>(
								R.id.flContainer, this, "1",
								HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
				.newTab()
				.setText("Mentions")
				.setIcon(R.drawable.mention_)
				.setTag("MentionsTimelineFragment")
				.setTabListener(
						new FragmentTabListener<MentionsTimelineFragment>(
								R.id.flContainer, this, "2",
								MentionsTimelineFragment.class));

		actionBar.addTab(tab2);
	}

	// check if online
	// read from db if not else do regular
	// add saves to Tweet class to persist or bulk array save

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	public void setFragmentTweets(Tweet tweet) {
		fragmentTweets.add(tweet);
	}

	public void clearFragmentTweets() {
		fragmentTweets.clear();
	}

	public ArrayList<Tweet> getFragmentTweets() {
		return fragmentTweets;
	}

}
