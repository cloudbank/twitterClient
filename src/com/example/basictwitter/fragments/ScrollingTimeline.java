package com.example.basictwitter.fragments;

import com.loopj.android.http.RequestParams;

public interface ScrollingTimeline {
	
	
	//cannot make the base class abstract
		  void populateTimeline(RequestParams p, boolean b);

		  void customLoadMoreDataFromApi(int p);
			


}
