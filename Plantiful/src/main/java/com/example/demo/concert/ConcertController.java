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

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/concert")
public class ConcertController {
	
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
		str += "&cpage="+page;
		str += "&rows=2000";
		str += "&prfstate=01";
		str += "&prfstate=02";
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
			System.out.println(element.getElementsByTagName("poster").getLength());
			Node node = element.getElementsByTagName("styurls").item(0);
			System.out.println(node);
 			String startDate = element.getElementsByTagName("prfpdfrom").item(0).getTextContent();
			String endDate = element.getElementsByTagName("prfpdto").item(0).getTextContent();
			
			
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
		return map;
	}
}
