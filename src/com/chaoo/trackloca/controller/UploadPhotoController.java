package com.chaoo.trackloca.controller;

import java.net.URL;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chaoo.trackloca.SubmitResult;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.data.photos.AlbumEntry;
import com.google.gdata.data.photos.UserFeed;

@RestController
public class UploadPhotoController {
	
	 private static final String API_PREFIX
     = "https://picasaweb.google.com/data/feed/api/user/";
	 
	 private PicasawebService service;
	 
	@RequestMapping("/uploadPhoto")
	public SubmitResult submitLocation(@RequestParam String userId
			) {
		SubmitResult result = new SubmitResult();
		
		service = new PicasawebService("TrackLoca");
		try {
			service.setUserCredentials("chaoo.kts@gmail.com", "");
			URL feedUrl = new URL(API_PREFIX+"username?kind=album");
			UserFeed myUserFeed = service.getFeed(feedUrl, UserFeed.class);
			String message="";
			System.out.println("aaaa");
			for (AlbumEntry myAlbum : myUserFeed.getAlbumEntries()) {
			    System.out.println(myAlbum.getTitle().getPlainText());
			    message += myAlbum.getTitle().getPlainText() + ",";
			}
			result.setDesc(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
		
	}
	
}
