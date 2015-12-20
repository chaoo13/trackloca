package com.chaoo.trackloca.legacy;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chaoo.trackloca.TrackLocation;
import com.chaoo.trackloca.TrackRoute;
import com.chaoo.trackloca.util.Contraints;
import com.chaoo.trackloca.util.DateUtil;
import com.chaoo.trackloca.util.StringUtil;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.GeoPt;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;





@SuppressWarnings("serial")
public class GetTrackListServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String userId = StringUtil.nvl((String) req.getParameter(Contraints.USERID_STRING));
		String flag = StringUtil.nvl((String) req.getParameter(Contraints.GETFLAG_STRING));
		
		if("today".equals(flag)){
			getToday(userId, req, resp);
		} else if("someday".equals(flag)){
			getSomeday(userId, req, resp);
		} else if("showmap".equals(flag)){
			showMap(userId, req, resp);
		} else {
			getAll(userId, req, resp);
		}
	}

	private void showMap(String userId, HttpServletRequest req,
			HttpServletResponse resp) throws IOException {
		resp.sendRedirect("/view/trackRouteMap.jsp");
		
	}

	private void getSomeday(String userId, HttpServletRequest req,HttpServletResponse resp) throws IOException {
		String sDate = StringUtil.nvl((String) req.getParameter(Contraints.SDATE_STRING));
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMdd");
		Date someDate;
		try {
			someDate = formatter.parse(sDate);
			getTrackRoute(DateUtil.trim(someDate), DateUtil.nextDayTrim(someDate), userId, req, resp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
	}

	private void getToday(String userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		getTrackRoute(DateUtil.todayTrim(), DateUtil.tommorowTrim(), userId, req, resp);
		
	}
	
	private void getAll(String userId, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMddHHmmss");
		try {
			Date fromDate = formatter.parse(StringUtil.nvl((String) req.getParameter(Contraints.FROMDATE_STRING),"20140101000000"));
			Date endDate = formatter.parse(StringUtil.nvl((String) req.getParameter(Contraints.ENDDATE_STRING), "21990101000000"));
			getTrackRoute(fromDate, endDate, userId, req, resp);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	private void getTrackRoute(Date fromDate, Date endDate, String userId, HttpServletRequest req, HttpServletResponse resp) throws IOException{
		Gson gson = new Gson();
		TrackRoute tr = new TrackRoute(fromDate, endDate, userId, null);
		List <TrackLocation> locations = new ArrayList<TrackLocation>();
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Filter userIdFilter = new FilterPredicate("userId", FilterOperator.EQUAL,userId);
		Filter fromFilter = new FilterPredicate("regAton", FilterOperator.GREATER_THAN_OR_EQUAL, fromDate);
		Filter endFilter = new FilterPredicate("regAton", FilterOperator.LESS_THAN, endDate);
		Filter rangeFilter = CompositeFilterOperator.and(userIdFilter, fromFilter, endFilter);
		
		Query q = new Query(Contraints.TRACKLOCATION).setFilter(rangeFilter).addSort(Contraints.REGATON_DATE, SortDirection.ASCENDING);
		
		PreparedQuery pq = datastore.prepare(q);
		
		for (Entity result : pq.asIterable()){
			String rUserId = (String) result.getProperty(Contraints.USERID_STRING);
			GeoPt geoPt = (GeoPt) result.getProperty(Contraints.GEOPT_GEOPT);
			Date regAton = (Date) result.getProperty(Contraints.REGATON_DATE);
			Date inputDate = (Date) result.getProperty(Contraints.INPUTDATE_DATE);
			
			locations.add(new TrackLocation(rUserId, regAton, inputDate, geoPt)); 
		}
		
		tr.setTrackLocations(locations);
		
		String result = gson.toJson(tr);
	
		resp.setContentType("application/json;charset=utf-8");
		try {
			resp.getWriter().println("result : "+ result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		req.setAttribute("route", tr);
		resp.sendRedirect("/view/trackRouteMap.jsp");

	}
}
