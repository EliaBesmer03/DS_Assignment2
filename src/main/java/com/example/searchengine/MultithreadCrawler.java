package com.example.searchengine;

import com.opencsv.CSVWriter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class MultithreadCrawler extends Crawler {

    private final ThreadPoolTaskExecutor executorService;

    private long startTime;

    private final Set<String> visited = ConcurrentHashMap.newKeySet();

    private final ConcurrentHashMap<String, String[]> map;

    private boolean done = false;



    public MultithreadCrawler(String indexFileName) {
        super(indexFileName);
        this.executorService = new ThreadPoolTaskExecutor();
        //TODO: configure executor service
        this.executorService.initialize();
        this.map = new ConcurrentHashMap<>();
    }

    public void crawl(String startUrl){
        double startTime = System.currentTimeMillis();
        //TODO: start crawl
        //TODO: monitor when crawling is done
        //TODO: write lines when crawling is done


    }


    /*
      TODO: complete class.
      The purpose of this runnable is to do two tasks:
      1. Process the page at the given url (startUrl).
      2. Create new jobs for the hyperlinks found in the page.
      The instances of this class are used as input to the executorService.submit method.
       */
    record CrawlerRunnable(MultithreadCrawler crawler, String startUrl) implements Runnable {

        @Override
        public void run() {
            //TODO: Process the start URL
            //TODO: Follow the new hyperlinks and process them.

        }
    }


}
