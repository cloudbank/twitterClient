package com.example.basictwitter;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.codepath.oauth.OAuthBaseClient;
import com.example.basictwitter.fragments.HomeTimelineFragment;
import com.example.basictwitter.fragments.MentionsTimelineFragment;
import com.example.basictwitter.fragments.UserTimelineFragment;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	private static final String STATUSES_UPDATE_JSON = "statuses/update.json";
	private static final String STATUSES_HOME_TIMELINE_JSON = "statuses/home_timeline.json";
	private static final String STATUSES_MENTIONS_TIMELINE_JSON = "statuses/mentions_timeline.json";
	private static final String STATUSES_USER_TIMELINE_JSON = "statuses/user_timeline.json";
	private static final String ACCOUNT_VERIFY_CRED = "account/verify_credentials.json";
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change
																				// this
	public static final String REST_URL = "https://api.twitter.com/1.1"; // Change
																			// this,
																			// base
																			// API
																			// URL
	public static final String REST_CONSUMER_KEY = "oBxMvKBeD3siHs8SBrOduJdYv"; // Change
																				// this
	public static final String REST_CONSUMER_SECRET = "5q8F9rNLIPFqb5jhuVnN0XwTBincwFQ30gz6WEUuakwRsn2eoP"; // Change
																											// this
	public static final String REST_CALLBACK_URL = "oauth://cpbasictweets"; // Change
																			// this
																			// (here
																			// and
																			// manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY,
				REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	public void getTimeline(AsyncHttpResponseHandler handler,
			RequestParams params, Fragment type) {
		String apiUrl = "";
		if (type instanceof HomeTimelineFragment) {
			apiUrl = getApiUrl(STATUSES_HOME_TIMELINE_JSON);
		} else if (type instanceof MentionsTimelineFragment) {
			apiUrl = getApiUrl(STATUSES_MENTIONS_TIMELINE_JSON);
		} else if (type instanceof UserTimelineFragment) {
			apiUrl = getApiUrl(STATUSES_USER_TIMELINE_JSON);
		} else if (type instanceof UserTimelineFragment) {
			apiUrl = getApiUrl(STATUSES_USER_TIMELINE_JSON);
		} else {
			throw new UnsupportedOperationException();
		}
		// params.put("since_id", "1");
		params.put("count", "13");
		//Log.d("DEBUG", "&&&" + params.toString());
		client.get(apiUrl, params, handler);
	}

	
	public void sendTweet(AsyncHttpResponseHandler handler, RequestParams params) {
		String apiUrl = getApiUrl(STATUSES_UPDATE_JSON);
		client.post(apiUrl, params, handler);
	}

	public void getMyInfo(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl(ACCOUNT_VERIFY_CRED);
		client.get(apiUrl, handler);
	}
	

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	/*
	 * public void getInterestingnessList(AsyncHttpResponseHandler handler) {
	 * String apiUrl =
	 * getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList"); //
	 * Can specify query string params directly or through RequestParams.
	 * RequestParams params = new RequestParams(); params.put("format", "json");
	 * client.get(apiUrl, params, handler); }
	 */

	/*
	 * 1. Define the endpoint URL with getApiUrl and pass a relative path to the
	 * endpoint i.e getApiUrl("statuses/home_timeline.json"); 2. Define the
	 * parameters to pass to the request (query or body) i.e RequestParams
	 * params = new RequestParams("foo", "bar"); 3. Define the request method
	 * and make a call to the client i.e client.get(apiUrl, params, handler);
	 * i.e client.post(apiUrl, params, handler);
	 */
}