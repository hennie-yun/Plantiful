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
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/concert")
public class ConcertController {
	
	@GetMapping("")
	public Map findAll(int page) {
		System.out.println("findAll");
		System.out.println("page : "+page);
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
		str += "&rows=10";
		
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
				
//				Concert con = new Con
//				list.add();
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
}
