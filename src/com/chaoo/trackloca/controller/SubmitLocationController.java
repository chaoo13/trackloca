package com.chaoo.trackloca.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chaoo.trackloca.SubmitResult;
import com.chaoo.trackloca.proc.TrackRouteProc;

@RestController
public class SubmitLocationController {

	@RequestMapping("/submit")
	public SubmitResult submitLocation(@RequestParam String userId, 
			@RequestParam String lat,
			@RequestParam String lng,
			@RequestParam(value="regAton") String sRegAton,
			@RequestParam String seqNo) {
		return new TrackRouteProc().addTrackLocation(userId, lat, lng, sRegAton, seqNo);
	}
	
	@RequestMapping("/uSubmit")
	public SubmitResult userSubmitLocation(@RequestParam String userId, 
			@RequestParam String latLng,
			@RequestParam String seqNo) {
			String [] result = latLng.split(",");
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmss");
			return new TrackRouteProc().addTrackLocation(userId, result[0], result[1],formatter.format(new Date()), seqNo);
	}
	
}
