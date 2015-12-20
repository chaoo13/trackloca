package com.chaoo.trackloca.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class WebSubmitController {
	@RequestMapping("/webSubmit")
	public ModelAndView getMovie() {
		HashMap<String, String> result = new HashMap<String, String>();
		SimpleDateFormat formatter = new SimpleDateFormat ( "yyyyMMddHHmmss");
		Date regAton = new Date();
		String sCurAton = formatter.format(regAton);
		result.put("regAton", sCurAton);
		result.put("seqNo", "1");
		return new ModelAndView("webSubmit", "result", result); 
	}
}
