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
        long startTime = System.currentTimeMillis(); // Beginn der Messung
        List<String> urls = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader(invertedIndexFileName))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                if (line.length > 0 && line[0].trim().equalsIgnoreCase(keyword.trim())) {
                    for (int i = 1; i < line.length; i++) {
                        String link = line[i].trim();
                        if (!link.startsWith(baseUrlWithoutSlash)) {
                            if (!link.startsWith("/")) link = "/" + link;
                            link = baseUrlWithoutSlash + link;
                        }
                        urls.add(link);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("Error while searching: {}", e.getMessage());
        }

        long duration = System.currentTimeMillis() - startTime; // ← hier überschreibst du die alte 0
        logger.info("duration searcher using inverted index: {}", duration);
        return urls;
    }


}
