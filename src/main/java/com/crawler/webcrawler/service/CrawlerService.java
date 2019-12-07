package com.crawler.webcrawler.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crawler.webcrawler.model.Assets;
 
@Service
public class CrawlerService {
	private Map<String,Assets> pagina = new HashMap<String, Assets>() ; 
	
	public ResponseEntity<Map<String, Assets>> crawl(String url) { 
		
		String html = getHTML(url); 
		
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("a");
		
		for (Element e: elements) {
			
			Assets assets = new Assets();
			
			
			
			String href = e.attr("href");
			href = processLink(href, url);
			if(pagina.containsKey(href) ) {
				continue;
			}
			String htmlPaginaAtual = getHTML(href);
			if(htmlPaginaAtual == null) {
				continue;
			}
			
			/*for (Element element : elements) {
				String href2 = element.attr("href");
				assets.getLinks().add(href2);
			}*/
			
			
			Document docAtual = Jsoup.parse(htmlPaginaAtual);
			Elements links = docAtual.select("link");
 
			Elements elementosCss = links.attr("rel", "stylesheet").attr("type", "text/css");  
			for (Element element : elementosCss) {
				String css = element.attr("href");
			    assets.getCss().add(css) ;
			}
			
			/*Elements js = docAtual.select("script");
			for (Element element : js) { 
				if(element.attr("src").contentEquals("")) {
					continue;
				}
			    assets.getJs().add( element.attr("src"));
			}*/
			
			/*Elements img = docAtual.select("img");
			for (Element element : img) { 
				if(element.attr("src").contentEquals("")) {
					continue;
				}
			    assets.getImages().add( element.attr("src")) ;
			}*/
			pagina.put(href, assets);
			System.out.println(href);
		}
		System.out.println(processLink("../", url));
		
		return ResponseEntity.ok(pagina);
	}
	
	private String processLink(String link, String base) {
		
		try {
			URL u = new URL(base);
			if (link.startsWith("./")) {
				link = link.substring(2, link.length());
				link = u.getProtocol() + "://" + u.getAuthority() + stripFilename(u.getPath()) + link;
			} else if (link.startsWith("#")) {
				link = base + link;
			} else if (link.startsWith("javascript:") || link.toLowerCase().contains("twitter") || link.toLowerCase().contains("forum")) {
				link = null;
			} else if (link.startsWith("../") || (!link.startsWith("http://") && !link.startsWith("https://"))) {
				link = u.getProtocol() + "://" + u.getAuthority() + stripFilename(u.getPath()) + link;
			}
			return link;
		} catch (Exception e) { 
			return null;
		}
		
	}
	
	private String stripFilename(String path) {
		int pos = path.lastIndexOf("/");
		return pos <= -1 ? path : path.substring(0, pos+1);
	}
	
	private String getHTML(String url) {
		
		URL u;
		try {
			u = new URL(url);
			
			URLConnection conn = u.openConnection();
			conn.setRequestProperty("User-Agent", "BBot/1.0");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			
			InputStream is = conn.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			
			String line;
			String html = "";
			while ((line = reader.readLine()) != null) {
				html += line + "\n";
			}
			html = html.trim();
			
			return html;
		} catch (Exception e) { 
			return null;
		}
		
	}
}
