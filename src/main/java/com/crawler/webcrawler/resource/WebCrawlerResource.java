package com.crawler.webcrawler.resource;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crawler.webcrawler.model.Assets;
import com.crawler.webcrawler.service.CrawlerService;
 

@RestController
@RequestMapping("/crawler")
public class WebCrawlerResource {

	@Autowired
	CrawlerService crawlerService;
	
	@PostMapping
	public ResponseEntity<Map<String,Assets>> pesquisar(@RequestBody String url) {
 
		return crawlerService.crawl(url);
	}
}
