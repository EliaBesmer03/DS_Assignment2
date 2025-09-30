package com.example.searchengine;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;


public abstract class Crawler {

     final String indexFileName;

    private String baseUrl = "https://api.interactions.ics.unisg.ch/hypermedia-environment/";

    protected final Logger logger = LoggerFactory.getLogger(Crawler.class);

    /**
     *
     * @param indexFileName the name of the file that is used as index.
     */
    protected Crawler(String indexFileName) {
        this.indexFileName = indexFileName;
    }

    /**
     *
     * @param url the url where the crawling starts
     */
    public abstract void crawl(String url);


    /**
     *
     * @param urlString
     * @return A list containing two sublists: the first one is the list of keywords present the Web page and the second one is a list of hyperlinks present in the Web page.
     */
    public  List<List<String>> getInfo(String urlString){
        List<String> keywords = new ArrayList<>();
        List<String> hyperlinks = new ArrayList<>();
        List<List<String>> returnList = new ArrayList<>();
        String html = "";//TODO: read HTML page
        try {
            Document d; //TODO: get from HTML page
            Elements elements; //TODO: initialize elements based on the webpage at the given url.
            //TODO : Use elements to put the keywords in the webpage in the list keywords.
            //TODO : Use elements to the hyperlinks to other pages in the environment in the list hyperlinks.
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        returnList.add(keywords);
        returnList.add(hyperlinks);
        return returnList;
    }
}
