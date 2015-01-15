package com.example.basictwitter;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.basictwitter.model.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class ComposeActivity extends Activity {

	EditText etCompose;
	TwitterClient client;
	ArrayAdapter<Tweet> aTweets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		client = TwitterApp.getRestClient();
		etCompose = (EditText) findViewById(R.id.etCompose);
		etCompose.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				TextView tv = (TextView) findViewById(R.id.tvCount);
				tv.setText(String.valueOf(s.length()));

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

	}

	private boolean checkTweet(CharSequence s) {
		boolean val;
		if (val = (s.toString().length() > 140)) {
			Toast.makeText(this, "140 chars or less", Toast.LENGTH_SHORT)
					.show();
		}
		return !val;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	public void composeTweet(View v) {
		String text = etCompose.getText().toString();
		if (!checkTweet(text)) {
			return;
		}
		// post the tweet to twitter and open timeline
		postTweet(text);

	}

	// texthandler for 140 chars
	//
	private void postTweet(String s) {
		// POST statuses/update
		RequestParams params = new RequestParams();
		params.put("status", s);
		client.sendTweet(new JsonHttpResponseHandler() {

			@Override
			public void onFailure(Throwable e, JSONObject arg1) {
				Log.d("DEBUG", "failed to tweet");
				e.printStackTrace();
			}

			@Override
			public void onSuccess(JSONObject tweet) {
				Log.d("DEBUG","composeonsuccess"+ tweet);
				
				Intent data = new Intent();
				data.putExtra("tweet", Tweet.fromJson(tweet));
				setResult(RESULT_OK, data);
				finish();

			}

		}, params);
	}

}
