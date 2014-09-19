package com.example.gridimagesearch.activities;

import java.io.Serializable;

public class Settings implements Serializable {

	private static final long serialVersionUID = 5321260817218224347L;
	
	public String imageType;
	public String colorFilter;
	public String imageSize;
	public String siteFilter;

	public Settings() {
		this.imageType = "";
		this.colorFilter = "";
		this.imageSize = "";
		this.siteFilter = "";
	}

	public void setValues(String type, String filter, String size, String site) {
		this.imageType = type;
		this.colorFilter = filter;
		this.imageSize = size;
		this.siteFilter = site;
	}

	public String getImageType() {
		return this.imageType;
	}

	public String getColorFilter() {
		return this.colorFilter;
	}

	public String getImageSize() {
		return this.imageSize;
	}

	public String getSiteFilter() {
		return this.siteFilter;
	}

}
