package com.chaoo.trackloca.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chaoo.onetouch.vo.AdFreeUrl;
import com.chaoo.trackloca.TrackLocation;
import com.chaoo.trackloca.proc.TrackRouteProc;
import com.chaoo.trackloca.util.Contraints;
import com.chaoo.trackloca.util.DateUtil;
import com.chaoo.trackloca.util.StringUtil;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;



@Controller
public class ShowUrlController {
	private final String returnView ="showUrl";
	private final String returnModel ="urls";
	@RequestMapping("/showUrl")
	public ModelAndView showUrl(@RequestParam String requestType) {
		
		if("add".equals(requestType)){
			return getUrl("A");
		} else if("adfree".equals(requestType)){
			return getUrl("F");
		} else {
			return null;
		}
		
	}



	private ModelAndView getUrl(String type) {
		
		List <AdFreeUrl> adus = new ArrayList<AdFreeUrl>();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Filter typeFilter = new FilterPredicate("requestType", FilterOperator.EQUAL,type);
		Filter fromFilter = new FilterPredicate("regAton", FilterOperator.GREATER_THAN_OR_EQUAL, DateUtil.koTodayTrim());
		Filter endFilter = new FilterPredicate("regAton", FilterOperator.LESS_THAN, DateUtil.koTommorowTrim());
		Filter rangeFilter = CompositeFilterOperator.and(typeFilter, fromFilter, endFilter);
		
		Query q = new Query("AdFreeUrl").setFilter(rangeFilter)
				.addSort(Contraints.REGATON_DATE, SortDirection.DESCENDING);

		PreparedQuery pq = datastore.prepare(q);
		
		for (Entity result : pq.asIterable()){
//			String urlId = KeyFactory.keyToString(result.getKey());
			String url = (String) result.getProperty("url");
			String countryCode = (String) result.getProperty("countryCode");
			
			adus.add(new AdFreeUrl(url, countryCode, type, null)); 
		}
		return new ModelAndView(returnView, returnModel, adus);
		
	}

}
