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
            long duration = System.currentTimeMillis() - startTime; //TODO: Update value
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
        if (visited.contains(startUrl)) {
            return lines;
        }
        visited.add(startUrl);

        // getInfo liefert [ [keywords], [links] ]
        List<List<String>> info = getInfo(startUrl);
        if (info == null || info.size() < 2) {
            return lines;
        }

        List<String> keywords = info.get(0);
        List<String> hyperlinks = info.get(1);

        // CSV-Zeile vorbereiten
        String relativeUrl = startUrl.replaceFirst("^" + getBaseUrl(), "");
        if (!relativeUrl.startsWith("/")) {
            relativeUrl = "/" + relativeUrl;
        }

        String[] row = new String[keywords.size() + 1];
        row[0] = relativeUrl;
        for (int i = 0; i < keywords.size(); i++) {
            row[i + 1] = keywords.get(i);
        }
        lines.add(row);

        // Rekursiv neue Seiten besuchen
        for (String link : hyperlinks) {
            String fullUrl = getBaseUrl() + link;
            if (!visited.contains(fullUrl)) {
                explore(fullUrl, lines, visited);
            }
        }

        return lines;


    }
    // Zugriff auf private baseUrl aus Crawler
    protected String getBaseUrl() {
        try {
            java.lang.reflect.Field field = Crawler.class.getDeclaredField("baseUrl");
            field.setAccessible(true);
            return (String) field.get(this);
        } catch (Exception e) {
            logger.error("Failed to access baseUrl: {}", e.getMessage());
            return "";
        }
    }

}
