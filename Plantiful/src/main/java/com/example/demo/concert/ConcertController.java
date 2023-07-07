package com.example.demo.concert;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.example.demo.schedule.ScheduleDto;
import com.example.demo.schedule.ScheduleService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/concert")
public class ConcertController {
	
	@Autowired
	ScheduleService service;
	
	@GetMapping("/{page}")
	public Map findAll(@PathVariable(name = "page") int page) {
        LocalDate now = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formatedNow = now.format(formatter);
        LocalDate plusMonth = now.plusMonths(1);
		String formatedPlusMonth = plusMonth.format(formatter);
		ArrayList<Concert> list = new ArrayList<>();
		String str = "http://kopis.or.kr/openApi/restful/pblprfr";
		str += "?service=ce866e84481d4d8cb7883e90889e4a9d";
		str += "&stdate="+formatedNow;
		str += "&eddate="+formatedPlusMonth;
		str += "&cpage=1";
		str += "&rows=2000";
//		str += "&prfstate=01";
//		str += "&prfstate=02";
		try {
			URL url = new URL(str);
			URLConnection connect = url.openConnection();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = (Document) builder.parse(connect.getInputStream());
			Element root = doc.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName("db");
			for(int i=0; i<nodeList.getLength(); i++) {
				Element element = (Element) nodeList.item(i);
				String id = element.getElementsByTagName("mt20id").item(0).getTextContent();
				String name = element.getElementsByTagName("prfnm").item(0).getTextContent();
				String poster = element.getElementsByTagName("poster").item(0).getTextContent();
				String startDate = element.getElementsByTagName("prfpdfrom").item(0).getTextContent();
				String endDate = element.getElementsByTagName("prfpdto").item(0).getTextContent();
				String loc = element.getElementsByTagName("fcltynm").item(0).getTextContent();
				String genre = element.getElementsByTagName("genrenm").item(0).getTextContent();
				String state = element.getElementsByTagName("prfstate").item(0).getTextContent();
				String strOpenrun = element.getElementsByTagName("openrun").item(0).getTextContent();
				boolean openrun = false;
				if(strOpenrun.equals("Y")) {
					openrun = true;
				} 
				
				Concert concert = new Concert(id, name, poster, startDate, endDate, loc, genre, state, openrun);
				list.add(concert);
			}
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		Map map = new HashMap();
		map.put("list", list);
		return map;
	}
	
	@GetMapping("/detail/{id}")
	public Map getDetail(@PathVariable(name = "id") String id) {
		String str = "http://kopis.or.kr/openApi/restful/pblprfr/"+id;
		str += "?service=ce866e84481d4d8cb7883e90889e4a9d";
		
		Concert concert = null;
		try {
			URL url = new URL(str);
			URLConnection connect = url.openConnection();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = (Document) builder.parse(connect.getInputStream());
			Element root = doc.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName("db");
			Element element = (Element) nodeList.item(0); 
			String mtid = element.getElementsByTagName("mt20id").item(0).getTextContent();
			String name = element.getElementsByTagName("prfnm").item(0).getTextContent();
			String poster = element.getElementsByTagName("poster").item(0).getTextContent();
			NodeList node = element.getElementsByTagName("styurls");
			Element elem = (Element) node.item(0);
			NodeList urlList = elem.getElementsByTagName("styurl");
			String[] styurls = new String[urlList.getLength()];
			for(int i=0; i<urlList.getLength(); i++) {
				styurls[i] = urlList.item(i).getTextContent();
			}
			
 			String startDate = element.getElementsByTagName("prfpdfrom").item(0).getTextContent();
			String endDate = element.getElementsByTagName("prfpdto").item(0).getTextContent();
			String cast = element.getElementsByTagName("prfcast").item(0).getTextContent();
			String crew = element.getElementsByTagName("prfcrew").item(0).getTextContent();
			String runTime = element.getElementsByTagName("prfruntime").item(0).getTextContent();
			String age = element.getElementsByTagName("prfage").item(0).getTextContent();
			String loc = element.getElementsByTagName("fcltynm").item(0).getTextContent();
			String producer = element.getElementsByTagName("entrpsnm").item(0).getTextContent();
			String price = element.getElementsByTagName("pcseguidance").item(0).getTextContent();
			String sty = element.getElementsByTagName("sty").item(0).getTextContent();
			String genre = element.getElementsByTagName("genrenm").item(0).getTextContent();
			String state = element.getElementsByTagName("prfstate").item(0).getTextContent();
			String run = element.getElementsByTagName("prfstate").item(0).getTextContent();
			boolean openrun = false;
			if(run.equals("Y")) {
				openrun = true;
			} 
			
			String locId = element.getElementsByTagName("mt10id").item(0).getTextContent();
			String concertTime = element.getElementsByTagName("dtguidance").item(0).getTextContent();
			concert = new Concert(mtid, name, poster, styurls, startDate, endDate, cast, crew, runTime, age, loc, producer, price, sty, genre, state, openrun, locId, concertTime);
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		
		Map map = new HashMap();
		map.put("concert", concert);
		return map;
	}
	
	@GetMapping("/getAdrs/{locId}")
	public Map getAdrs(@PathVariable(name = "locId") String locId) {
		String str = "http://kopis.or.kr/openApi/restful/prfplc/"+locId;
		str += "?service=ce866e84481d4d8cb7883e90889e4a9d";
		String adres = null;
		try {
			URL url = new URL(str);
			URLConnection conn = url.openConnection();
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = (Document) builder.parse(conn.getInputStream());
			Element root = doc.getDocumentElement();
			NodeList nodeList = root.getElementsByTagName("db");
			Element element = (Element) nodeList.item(0); 
			adres = element.getElementsByTagName("adres").item(0).getTextContent();
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		Map map = new HashMap();
		map.put("adres", adres);
		return map;
	}
	
	@PostMapping("")
	public Map add(ScheduleDto dto) {
		Map map = new HashMap();
		if(service.isAnySchedule(dto)) {
			map.put("isJoin", true);
		} else {
			map.put("isJoin", false);
			ScheduleDto d = service.save(dto);
			map.put("msg", d);
		}
		
		return map;
	}
}
