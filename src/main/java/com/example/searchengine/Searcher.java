package com.example.searchengine;

import com.opencsv.CSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class Searcher {

    private final String baseUrlWithoutSlash = "https://api.interactions.ics.unisg.ch/hypermedia-environment";

    private final Logger logger = LoggerFactory.getLogger(Searcher.class);

    /**
     *
     * @param keyword to search
     * @param invertedIndexFileName the file where the search is performed.
     * @return the list of urls
     */
    public List<String> search(String keyword, String invertedIndexFileName){
        long duration = 0; //TODO: update the value in the code
        List<String> urls = new ArrayList<>();
        //TODO: Update the list urls to contain all urls whose page contains the keyword.
        logger.info("duration searcher using inverted index: {}", duration);
        return urls;
    }


}
