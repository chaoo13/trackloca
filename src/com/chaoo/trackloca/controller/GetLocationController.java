package com.chaoo.trackloca.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chaoo.trackloca.TrackRoute;
import com.chaoo.trackloca.proc.TrackRouteProc;
import com.chaoo.trackloca.util.DateUtil;


@RestController
public class GetLocationController {
	
	@RequestMapping("/getLocation")
	public TrackRoute getMovie(@RequestParam String userId, 
			@RequestParam(value="getflag", required = false) String flag) {
		
		if(flag != null && "today".equals(flag)){
			return getToday(userId);
		} else {
			return getAll(userId);
		}
	}

	private TrackRoute getToday(String userId) {
		return new TrackRouteProc().getTrackRoute(DateUtil.koTodayTrim(), DateUtil.koTommorowTrim(), userId);
	}

	private TrackRoute getAll(String userId) {
		return new TrackRouteProc().getTrackRoute(DateUtil.getFromDate(), DateUtil.getEndDate(), userId);
	}
}
