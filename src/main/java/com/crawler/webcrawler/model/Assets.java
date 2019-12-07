package com.crawler.webcrawler.model;
 
	import java.util.ArrayList;
	import java.util.List;

public class Assets {
	
	private List<String> links;
	private List<String> css;
	private List<String> js;
	private List<String> images;
 
	public Assets() {
		super();
		this.links = new ArrayList<String>();
		this.css = new ArrayList<String>();
		this.js = new ArrayList<String>();
		this.images = new ArrayList<String>();
	}
	
	public List<String> getLinks() {
		return links;
	}
	public void setLinks(List<String> links) {
		this.links = links;
	}
	public List<String> getCss() {
		return css;
	}
	public void setCss(List<String> css) {
		this.css = css;
	}
	public List<String> getJs() {
		return js;
	}
	public void setJs(List<String> js) {
		this.js = js;
	}
	public List<String> getImages() {
		return images;
	}
	public void setImages(List<String> images) {
		this.images = images;
	}
}
