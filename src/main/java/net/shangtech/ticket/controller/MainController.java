package net.shangtech.ticket.controller;

import net.shangtech.ticket.util.HttpUtils;

import org.apache.http.client.HttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	
	private static final String QUERY_URL = "https://kyfw.12306.cn/otn/lcxxcx/query?purpose_codes=ADULT";
	
	@RequestMapping("/query")
	public ModelAndView query(String from, String to, String date){
		ModelAndView mav = new ModelAndView("result");
		HttpClient client = HttpUtils.getClient();
		String result = HttpUtils.get(client, QUERY_URL + "&queryDate=" + date + "&from_station=" + from + "&to_station=" + to);
		mav.addObject("result", result);
		return mav;
	}
	
	@RequestMapping({"", "/index", "/main"})
	public ModelAndView main(){
		ModelAndView mav = new ModelAndView("main");
		return mav;
	}
	
}
