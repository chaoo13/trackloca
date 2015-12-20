package com.chaoo.trackloca.proc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.chaoo.trackloca.SubmitResult;
import com.chaoo.trackloca.TrackLocation;
import com.chaoo.trackloca.TrackRoute;
import com.chaoo.trackloca.util.Contraints;
import com.chaoo.trackloca.util.StringUtil;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

public class TrackRouteProc {

	public TrackRoute getTrackRoute(Date fromDate, Date endDate, String userId) {
		
		TrackRoute tr = new TrackRoute(fromDate, endDate, userId, null);
		List <TrackLocation> locations = new ArrayList<TrackLocation>();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Filter userIdFilter = new FilterPredicate("userId", FilterOperator.EQUAL,userId);
		Filter fromFilter = new FilterPredicate("regAton", FilterOperator.GREATER_THAN_OR_EQUAL, fromDate);
		Filter endFilter = new FilterPredicate("regAton", FilterOperator.LESS_THAN, endDate);
		Filter rangeFilter = CompositeFilterOperator.and(userIdFilter, fromFilter, endFilter);
		
		Query q = new Query(Contraints.TRACKLOCATION).setFilter(rangeFilter)
				.addSort(Contraints.REGATON_DATE, SortDirection.ASCENDING)
				.addSort(Contraints.SEQNO_STRING, SortDirection.ASCENDING);
		
		PreparedQuery pq = datastore.prepare(q);
		
		int count=0;
		for (Entity result : pq.asIterable()){
			count++;
			String locaId = KeyFactory.keyToString(result.getKey());
			String rUserId = (String) result.getProperty(Contraints.USERID_STRING);
			GeoPt geoPt = (GeoPt) result.getProperty(Contraints.GEOPT_GEOPT);
			Date regAton = (Date) result.getProperty(Contraints.REGATON_DATE);
			Date inputDate = (Date) result.getProperty(Contraints.INPUTDATE_DATE);
			int seqNo = Integer.parseInt(StringUtil.nvl(result.getProperty(Contraints.SEQNO_STRING).toString(),"0"));
			
			locations.add(new TrackLocation(rUserId, regAton, inputDate, geoPt, seqNo, locaId)); 
		}
		
		if(count == 0){
			GeoPt geoPt = new GeoPt((float)37.475658,(float)127.0303281);
			locations.add(new TrackLocation(userId, new Date(), new Date(), geoPt, 0));
		}
		
		tr.setTrackLocations(locations);
		
		return tr;

	}
	
	public void delTrackRoute(String locaId) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key locaKey = KeyFactory.stringToKey(locaId);	
		datastore.delete(locaKey);
	}
	
	
	public SubmitResult addTrackLocation(String userId, String lat, String lng, String sRegAton, String seqNo ){
		SubmitResult result = new SubmitResult();
		String sCurDate ="";
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMddHHmmss");
		String message;
		try {
			Date regAton = formatter.parse(sRegAton);
			GeoPt geoPt = new GeoPt(Float.parseFloat(lat), Float.parseFloat(lng));
			Entity location = new Entity(Contraints.TRACKLOCATION);
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Date curDate = new Date();
			sCurDate = curDate.toString();
			
			location.setProperty(Contraints.GEOPT_GEOPT, geoPt);
			location.setProperty(Contraints.USERID_STRING, userId);
			location.setProperty(Contraints.REGATON_DATE, regAton);
			location.setProperty(Contraints.INPUTDATE_DATE, curDate);
			location.setProperty(Contraints.SEQNO_STRING, seqNo);
			datastore.put(location);
		
		} catch (ParseException e) {
			e.printStackTrace();
			message = e.toString();
			result.setDesc(message);
			result.setRetCode(-1);
			return result;
		} 
		
		message = "submit success (";
		message += "lat : "+ lat;
		message += ",lng : "+ lng;
		message += ",userId : "+ userId;
		message += ",sRegAton : "+ sRegAton;
		message += ",sCurDate : "+ sCurDate;
		message += ",seqNo : "+ seqNo;
		result.setDesc(message);
		
		return result;
	}
}
