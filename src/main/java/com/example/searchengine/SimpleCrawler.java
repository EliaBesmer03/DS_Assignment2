package com.example.searchengine;

import com.opencsv.CSVWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.*;

public class SimpleCrawler extends Crawler {

    Logger logger = LogManager.getLogger();


    protected SimpleCrawler(String indexFileName) {
        super(indexFileName);
    }

    public void crawl(String startUrl){
        try {
            long startTime = System.currentTimeMillis();
            Set<String[]> lines = explore(startUrl, new HashSet<>(), new HashSet<>());
            FileWriter fileWriter = new FileWriter(indexFileName);
            CSVWriter writer = new CSVWriter(fileWriter,',', CSVWriter.NO_QUOTE_CHARACTER,' ',"\r\n");
            for (String[] line : lines) {
                writer.writeNext(line);
            }
            fileWriter.flush();
            fileWriter.close();
            long duration = 0; //TODO: Update value
            logger.info("duration simple crawler: {}", duration);
        } catch (Exception e){
            logger.error(e.getMessage());
        }

    }

    /**
     *
     * @param startUrl the url where the crawling operation starts
     * @param lines stores the lines to print on the index file
     * @param visited stores the urls that the program has already visited
     * @return the set of lines to print on the index file
     */
    public Set<String[]> explore(String startUrl, Set<String[]> lines, Set<String> visited){
        //TODO: complete the exploration program. Note: This function is recursive. It updates the set of lines. The visited set should also be updated. It uses the getInfo method of the Crawler class.
        return lines;


    }

}
