package com.chaoo.trackloca.controller;

import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chaoo.trackloca.SubmitResult;
import com.chaoo.trackloca.util.Contraints;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

@RestController
public class SubmitUrlController {

	@RequestMapping("/adfreereq")
	public SubmitResult adFreeRequestUrl(@RequestParam String countryCode, 
			@RequestParam String url) {
		
		SubmitResult result = new SubmitResult();
		Date retAton = new Date();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity adu = new Entity("AdFreeUrl");
		adu.setProperty("countryCode", countryCode);
		adu.setProperty("url", url);
		adu.setProperty("requestType", "F");
		adu.setProperty("regAton", retAton);
		datastore.put(adu);
			
		return result;
	}
	
	@RequestMapping("/addurl")
	public SubmitResult addWebsiteUrl(@RequestParam String countryCode, 
			@RequestParam String url,
			@RequestParam String type) {
		
		SubmitResult result = new SubmitResult();
		Date retAton = new Date();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Entity adu = new Entity("AdFreeUrl");
		adu.setProperty("countryCode", countryCode);
		adu.setProperty("url", url);
		adu.setProperty("requestType", type);
		adu.setProperty("regAton", retAton);
		datastore.put(adu);
			
		return result;
	}
	
}
