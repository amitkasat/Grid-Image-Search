package com.example.gridimagesearch.activities;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager.OnActivityResultListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.gridimagesearch.R;
import com.example.gridimagesearch.adapters.ImageResultsAdapter;
import com.example.gridimagesearch.models.ImageResult;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	private EditText etQuery;
	private GridView gvResults;
	private ArrayList<ImageResult> imageResults;
	private ImageResultsAdapter aImageResults;
	protected String website, imageFilter, colorFilter, imageType;
	private Settings settings;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		settings = new Settings();
		// Creates the data source
		imageResults = new ArrayList<ImageResult>();
		// Attaches the data source to an adapter
		aImageResults = new ImageResultsAdapter(this, imageResults);
		// Link the adapter to adapterview (gridview)
		gvResults.setAdapter(aImageResults);
		gvResults.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				// Triggered only when new data needs to be appended to the list
				// Add whatever code is needed to append new items to your
				// AdapterView
				customLoadMoreDataFromApi(page);
				// or customLoadMoreDataFromApi(totalItemsCount);
			}
		});
	}

	public void customLoadMoreDataFromApi(int page) {
		// This method probably sends out a network request and appends new data
		// items to your adapter.
		// Use the offset value and add it as a parameter to your API request to
		// retrieve paginated data.
		// Deserialize API response and then construct new objects to append to
		// the adapter
		
		onImageSearch(gvResults);
	}

	private void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Launch the image display activity
				// Creating an intent
				Intent i = new Intent(SearchActivity.this,
						ImageDisplayActivity.class);
				// Get the image result to display
				ImageResult result = imageResults.get(position);
				// Pass Image Result into the intent
				i.putExtra("result", result);
				// Launch the new activity
				startActivity(i);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_settings, menu);
		return true;
	}

	
	public void onActivityResult(int requestcode, int resultcode, Intent data) {
		if (requestcode == 5) {
			if (resultcode == RESULT_OK) {
               this.settings = (Settings) data.getExtras().getSerializable("settings");
			}
		}
	}
	
	public void onSettings(MenuItem menu) {
		Intent data = new Intent(SearchActivity.this, SettingsActivity.class);
		data.putExtra("settings", this.settings);
		startActivityForResult(data,5);
	}


	// Fired when the button is pressed (android:onClick property)
	public void onImageSearch(View v) {
		
		if(v.getId() == R.id.btnSearch)
		{
		// Clears the array
		imageResults.clear();
		// Notifies the adapter
		aImageResults.notifyDataSetChanged();
		}
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Search for: " + query, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q="
				+ query
				+ "&rsz=8"
				+ "&imgsz="
				+  this.settings.imageSize
				+ "&imgcolor="
				+  this.settings.colorFilter
				+ "&imgtype="
				+  this.settings.imageType
				+ "&as_sitesearch="
				+  this.settings.siteFilter;
		client.get(searchUrl, new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(int statusCode,
					org.apache.http.Header[] headers,
					org.json.JSONObject response) {
				Log.d("DEBUG", response.toString());
				JSONArray imageResultsJson = null;
				try {
					imageResultsJson = response.getJSONObject("responseData")
							.getJSONArray("results");
					//imageResults.clear(); // clear the existing images from the
											// array in cases where its a new
											// search
					// When you make changes to the adapter, it does modify the
					// underline data
					aImageResults.addAll(ImageResult
							.fromJSONArray(imageResultsJson));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Log.i("INFO", imageResults.toString());
			}
		});
	}

	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		if(id == R.id.miSettings)
		{
			return true;
	}	
	return super.onOptionsItemSelected(item);
		
	}

}
