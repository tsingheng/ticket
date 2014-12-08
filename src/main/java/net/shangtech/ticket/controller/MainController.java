package net.shangtech.ticket.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.shangtech.ticket.util.HttpUtils;

import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
public class MainController {
	
	private static final String QUERY_URL = "https://kyfw.12306.cn/otn/lcxxcx/query?purpose_codes=ADULT";
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	@RequestMapping("/query")
	public ModelAndView query(String from, String to, String date){
		if(!StringUtils.hasText(date)){
			date = df.format(new Date());
		}
		ModelAndView mav = new ModelAndView("result");
		HttpClient client = HttpUtils.getClient();
		String result = HttpUtils.get(client, QUERY_URL + "&queryDate=" + date + "&from_station=" + from + "&to_station=" + to);
		mav.addObject("result", result);
		mav.addObject("current", date);
		if(!result.trim().equals("-1")){
			Calendar c = Calendar.getInstance();
			Date current = DateUtils.parseDate(date, new String[]{"yyyy-MM-dd"});
			c.setTime(current);
			c.add(Calendar.DAY_OF_MONTH, 1);
			Calendar max = Calendar.getInstance();
			max.setTime(new Date());
			max.add(Calendar.DAY_OF_MONTH, 60);
			if(max.getTime().after(c.getTime())){
				mav.addObject("next", df.format(c.getTime()));
			}
		}
		return mav;
	}
	
	@RequestMapping({"", "/index", "/main"})
	public ModelAndView main(){
		ModelAndView mav = new ModelAndView("main");
		return mav;
	}
	
	@RequestMapping("/chart-data")
	public String chartData(String code, String from, String to, Model model){
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DAY_OF_MONTH, 1);
		String date = df.format(c.getTime());
		HttpClient client = HttpUtils.getClient();
		String result = HttpUtils.get(client, QUERY_URL + "&queryDate=" + date + "&from_station=" + from + "&to_station=" + to);
		if(!result.trim().equals("-1")){
			JSONObject data = JSON.parseObject(result).getJSONObject("data");
			JSONArray datas = data.getJSONArray("datas");
			for(int i = 0; i < datas.size(); i++){
				data = datas.getJSONObject(i);
				if(data.getString("station_train_code").equals(code)){
					break;
				}
			}
			data.entrySet().forEach(entry -> {
				if(entry.getKey().endsWith("_num")){
					Integer num = 0;
					try{
						num = Integer.parseInt((String) entry.getValue());
					}
					catch (Exception e){
						num = 0;
					}
					entry.setValue(num);
				}
			});
			model.addAttribute("data", data);
		}
		model.addAttribute("date", date);
		return "chart-data";
	}
	
	@RequestMapping("/real-data")
	public String realData(String from, String to, Integer days, Model model){
		if(days == null){
			days = 0;
		}
		Date now = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(now);
		c.add(Calendar.DAY_OF_MONTH, days);
		String date = df.format(c.getTime());
		model.addAttribute("date", date);
		HttpClient client = HttpUtils.getClient();
		String result = HttpUtils.get(client, QUERY_URL + "&queryDate=" + date + "&from_station=" + from + "&to_station=" + to);
		JSONObject object = JSON.parseObject(result);
		JSONArray datas = object.getJSONObject("data").getJSONArray("datas");
		Map<String, String> map = new HashMap<>();
		for(int i = 0; i < datas.size(); i++){
			JSONObject obj = datas.getJSONObject(i);
			obj.entrySet().forEach(entry -> {
				if(entry.getKey().endsWith("_num")){
					Integer num = 0;
					try{
						num = Integer.parseInt((String) entry.getValue());
					}
					catch (Exception e){
						num = 0;
					}
					entry.setValue(num);
				}
			});
			StringBuilder sb = new StringBuilder("&value=");
			String code = obj.getString("station_train_code");
			if(code.startsWith("D") || code.startsWith("G")){
				sb.append(obj.getIntValue("swz_num")).append("|");
				sb.append(obj.getIntValue("tz_num")).append("|");
				sb.append(obj.getIntValue("zy_num")).append("|");
				sb.append(obj.getIntValue("ze_num")).append("|");
			}
			else {
				sb.append(obj.getIntValue("gr_num")).append("|");
				sb.append(obj.getIntValue("rw_num")).append("|");
				sb.append(obj.getIntValue("yw_num")).append("|");
				sb.append(obj.getIntValue("rz_num")).append("|");
				sb.append(obj.getIntValue("yz_num")).append("|");
			}
			sb.append(obj.getIntValue("wz_num")).append("|");
			sb.append(obj.getIntValue("qt_num"));
			map.put(code, sb.toString());
		}
		model.addAttribute("map", map);
		return "real-data";
	}
	
}
