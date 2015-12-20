package com.chaoo.trackloca.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.chaoo.trackloca.SubmitResult;
import com.chaoo.trackloca.proc.TrackRouteProc;

@RestController
public class AdjustLocationController {
	
	
	@RequestMapping("/adjLocation")
	public SubmitResult getMovie(@RequestParam String locaId,
			@RequestParam String flag) {
		// flag=del&userId={userId}&locaId={locaId}
		SubmitResult result = new SubmitResult();
		String message;
		if("del".equals(flag)){
			TrackRouteProc proc = new TrackRouteProc();
			proc.delTrackRoute(locaId);
			message = "Delete: " + locaId + " is done";
			result.setDesc(message);
		} else {
			message = "Empty flag param";
			result.setDesc(message);
			result.setRetCode(-1);
		}
		return result;
	}

}
