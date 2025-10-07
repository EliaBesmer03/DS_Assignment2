package com.example.searchengine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;



@RestController
public class SearchEngine {

	public final String indexFileName = "./src/main/resources/index.csv";

	public final String invertedIndexFileName = "./src/main/resources/inverted_index.csv";

	public final String startUrl = "https://api.interactions.ics.unisg.ch/hypermedia-environment/cc2247b79ac48af0";

	@Autowired
	Searcher searcher;

	@Autowired
	IndexInverter indexInverter;

	@Autowired
	SearchEngineProperties properties;

	Crawler crawler;

    protected final Logger logger = LoggerFactory.getLogger(SearchEngine.class);

	@PostConstruct
	public void initialize(){
		if (properties.getCrawler().equals("multithread")){
			this.crawler = new MultithreadCrawler(indexFileName);
		} else {
			this.crawler = new SimpleCrawler(indexFileName);
		}
		if (properties.getCrawl()) {
			crawler.crawl(startUrl);
			indexInverter.invertIndex(indexFileName, invertedIndexFileName);
		}
	}

    @GetMapping("/search")
    public List<String> search(@RequestParam("q") String keyword) {
        logger.info("Received search request for keyword: {}", keyword);
        return searcher.search(keyword, invertedIndexFileName);
    }

    @GetMapping("/lucky")
    public void lucky(@RequestParam("q") String keyword, javax.servlet.http.HttpServletResponse response) {
        logger.info("Received lucky request for keyword: {}", keyword);
        List<String> results = searcher.search(keyword, invertedIndexFileName);
        if (results.isEmpty()) {
            response.setStatus(404);
            return;
        }
        try {
            response.setStatus(302);
            response.setHeader("Location", results.get(0));
        } catch (Exception e) {
            logger.error("Error while redirecting: {}", e.getMessage());
        }
    }

}
