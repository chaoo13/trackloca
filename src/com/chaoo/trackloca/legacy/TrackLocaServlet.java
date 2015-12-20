package com.chaoo.trackloca.legacy;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.chaoo.trackloca.util.Contraints;
import com.chaoo.trackloca.util.StringUtil;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.GeoPt;

@SuppressWarnings("serial")
public class TrackLocaServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String lat = StringUtil.nvl((String) req.getParameter("lat"));
		String lng = StringUtil.nvl((String) req.getParameter("lng"));
		String userId = StringUtil.nvl((String) req.getParameter("userId"));
		String sRegAton = StringUtil.nvl((String) req.getParameter("regAton"));
		String sCurDate ="";
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMddHHmmss");
		
		if(StringUtil.emptyString(lat)|| StringUtil.emptyString(lng) || StringUtil.emptyString(userId) || StringUtil.emptyString(sRegAton) ){
			resp.setContentType("text/plain");
			resp.getWriter().println("Empty");
		} else {
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
				datastore.put(location);
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			resp.setContentType("text/plain");
			resp.getWriter().println("lat : "+ lat);
			resp.getWriter().println("lng : "+ lng);
			resp.getWriter().println("userId: "+ userId);
			resp.getWriter().println("sRegAton: "+ sRegAton);
			resp.getWriter().println("sCurDate: "+ sCurDate);
						
		}
	}
}
