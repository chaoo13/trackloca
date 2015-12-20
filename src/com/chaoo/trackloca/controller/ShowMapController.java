package com.chaoo.trackloca.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.chaoo.trackloca.proc.TrackRouteProc;
import com.chaoo.trackloca.util.DateUtil;



@Controller
public class ShowMapController {
	private final String returnView ="showMap";
	private final String returnModel ="route";
	@RequestMapping("/showMap")
	public ModelAndView getMovie(@RequestParam String userId, 
			@RequestParam(value="getflag", required = false) String flag) {
		
		if(flag != null && "today".equals(flag)){
			return getToday(userId);
		} else {
			return getAll(userId);
		}
	}



	private ModelAndView getToday(String userId) {
		return new ModelAndView(returnView, returnModel, new TrackRouteProc().getTrackRoute(DateUtil.koTodayTrim(), DateUtil.koTommorowTrim(), userId));
	}

	private ModelAndView getAll(String userId) {
		return new ModelAndView(returnView, returnModel, new TrackRouteProc().getTrackRoute(DateUtil.getFromDate(), DateUtil.getEndDate(), userId));
	}
}
