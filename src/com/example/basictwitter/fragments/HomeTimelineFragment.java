package com.example.basictwitter.fragments;

import java.util.ArrayList;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.basictwitter.R;
import com.example.basictwitter.TweetArrayAdapter;
import com.example.basictwitter.activity.TimelineActivity;
import com.example.basictwitter.listeners.EndlessScrollListener;
import com.example.basictwitter.model.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class HomeTimelineFragment extends TweetsListFragment {

	
	@Override
	public void onResume() {
		super.onResume();
		FragmentActivity tla = this.getActivity();
		ArrayList<Tweet> fragTweets = (ArrayList<Tweet>) ((TimelineActivity) tla)
				.getFragmentTweets();
		if (fragTweets.size() > 0) {
			for (Tweet t : fragTweets) {
				tweets.add(0, t);
			}
		}
		aTweets.notifyDataSetChanged();
		((TimelineActivity) getActivity()).clearFragmentTweets();

		Log.d("DEBUG", "resuming the frag" + tweets);
	}

	

	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == getActivity().RESULT_OK
				&& requestCode == REQUEST_CODE) {
			Tweet tweet = (Tweet) data.getSerializableExtra("tweet");
			Log.d("DEBUG", "onActivityResult" + getClass()
					+ tweets.get(0).toString());
			tweets.add(0, tweet);
			aTweets.notifyDataSetChanged();
		}

	}

	

}
