package com.example.basictwitter.model;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.text.Html;

import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Column.ForeignKeyAction;
import com.activeandroid.annotation.Table;

@Table(name = "Tweets")
public class Tweet implements Serializable{
	@Column
	private String body;
	@Column(name="remoteid", unique = true)
	private long id;
	@Column
	private String timestamp;
	@Column(name="User", onUpdate = ForeignKeyAction.CASCADE, onDelete = ForeignKeyAction.CASCADE)
	private User user;

	
	public Tweet() {
		super();
	}
	
	public static Tweet fromJson(JSONObject jo) {
		Tweet tweet = new Tweet();
		try {
			tweet.body = Html.fromHtml(jo.getString("text")).toString();
			
			tweet.id = jo.getLong("id");
			tweet.timestamp = jo.getString("created_at");
			
			tweet.user = User.fromJson(jo.getJSONObject("user"));

		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		return tweet;

	}

	public String getBody() {
		return body;
	}

	public long getId() {
		return id;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public User getUser() {
		return user;
	}

	public static ArrayList<Tweet> fromJsonArray(JSONArray response) {
		ArrayList<Tweet> twts = new ArrayList<Tweet>();
		for (int i = 0; i < response.length(); i++) {
			JSONObject jo = null;
			try {
				jo = response.getJSONObject(i);

			} catch (JSONException e) {
				e.printStackTrace();
				continue;
			}
			Tweet t = Tweet.fromJson(jo);
			
			if (null != t) {
				twts.add(t);
			}
		}
	
		return twts;
	}

	@Override
	public String toString() {
        return this.body  + "   --"+this.user.getName()+" :"+this.user.getScreen_name();
	}
}
