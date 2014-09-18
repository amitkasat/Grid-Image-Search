package com.example.gridimagesearch.activities;

//import com.amit.actionbardemo1.R;

import com.example.gridimagesearch.R;
import com.example.gridimagesearch.R.id;
import com.example.gridimagesearch.R.layout;
import com.example.gridimagesearch.R.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingsActivity extends Activity {

	private Settings settings;
	private Spinner imageSize, colorFilter, imageType;
	private EditText website;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		//pull out arguments from intent
		
		this.imageSize = (Spinner) findViewById(R.id.spinner1);
		this.colorFilter = (Spinner) findViewById(R.id.spinner2);
		this.imageType = (Spinner) findViewById(R.id.spinner3);
		this.website = (EditText) findViewById(R.id.etText);
		
		settings = (Settings) getIntent().getExtras().getSerializable("settings");
		
	}
	
	 public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.search, menu);
	        
	        return true;
	    }
	 
	public void onSubmit(View v)
	{
		settings.setValues(this.imageType.getSelectedItem().toString(), 
				this.colorFilter.getSelectedItem().toString(), this.imageSize.getSelectedItem().toString(), 
				this.website.getText().toString());
		//Toast.makeText(this,"Searchfor" + this.settings.imageType, Toast.LENGTH_SHORT).show();
		Intent i = new Intent();
		i.putExtra("settings", settings);
		setResult(RESULT_OK,i);
		finish();
	}
}
