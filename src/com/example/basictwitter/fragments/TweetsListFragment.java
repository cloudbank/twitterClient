package com.example.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.basictwitter.ComposeActivity;
import com.example.basictwitter.R;
import com.example.basictwitter.TweetArrayAdapter;
import com.example.basictwitter.TwitterApp;
import com.example.basictwitter.TwitterClient;
import com.example.basictwitter.activity.ProfileActivity;
import com.example.basictwitter.listeners.EndlessScrollListener;
import com.example.basictwitter.model.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public  class TweetsListFragment extends Fragment implements ScrollingTimeline{
	final int REQUEST_CODE = 20;
	ArrayAdapter<Tweet> aTweets;
	PullToRefreshListView lvTweets;
	TwitterClient client;
	ArrayList<Tweet> tweets;

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.fragment_menu_timeline, menu);
	}

	private void openCompose() {
		Intent i = new Intent(getActivity(), ComposeActivity.class);
		startActivityForResult(i, REQUEST_CODE);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mi_compose:
			openCompose();
			break;
		case R.id.mi_profile:
			openProfile();
			break;
		default:
			break;
		}
		return true;
	}

	private void openProfile() {
		Intent i = new Intent(getActivity(), ProfileActivity.class);
		startActivity(i);

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		client = TwitterApp.getRestClient();
		tweets = new ArrayList<Tweet>();
		aTweets = new TweetArrayAdapter(getActivity(), tweets);
		setHasOptionsMenu(true);
		populateTimeline(new RequestParams(), true);
	}
	
	
	
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container,
				false);

		lvTweets = (PullToRefreshListView) v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
				// Add whatever code is needed to append new items to your
				// AdapterView
				Log.d("DEBUG", "detected scroll" + page);
				// there is some bug in scroll after I added the pull down
				// refresh library
				if (totalItemsCount > 1) {
					customLoadMoreDataFromApi(page);
				}
			}
		});
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Your code to refresh the list here.
				// Make sure you call listView.onRefreshComplete() when
				// once the network request has completed successfully.
				populateTimeline(new RequestParams(), true);
			}
		});
		return v;

	}
	
	public void populateTimeline(RequestParams params, boolean clear) {

		// clear the adapter
		if (clear) {
			tweets.clear();
			aTweets.clear();
			aTweets.notifyDataSetChanged();
		} else {
			long lowId = tweets.get(tweets.size() - 1).getId();
			lowId--;
			params.put("max_id", String.valueOf(lowId));
		}

		client.getTimeline(new JsonHttpResponseHandler() {

			
			
			@Override
			public void onSuccess(int code, JSONArray response) {
				aTweets.addAll(Tweet.fromJsonArray(response));
				lvTweets.onRefreshComplete();
			}

			@Override
			public void onFailure(Throwable e, String s) {
				Log.d("DEBUG", e.toString());
				Log.d("DEBUG", s);
			}

		}, params, this);
	}


	public void customLoadMoreDataFromApi(int offset) {
		// This method probably sends out a network request and appends new data
		// items to your adapter.
		// Use the offset value and add it as a parameter to your API request to
		// retrieve paginated data.
		// Deserialize API response and then construct new objects to append to
		// the adapter

		populateTimeline(new RequestParams(), false);

	}

}
