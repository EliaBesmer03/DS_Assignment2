package com.example.searchengine;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
@Component
public class IndexInverter {

    protected final Logger logger = LoggerFactory.getLogger(IndexInverter.class);

    public void invertIndex(String indexFileName, String invertedIndexFileName){
        try {
            CSVReader csvReader = new CSVReader(new FileReader(indexFileName));
            List<String[]> csvLines = csvReader.readAll();

            // Inverted Index Map: keyword -> Liste von URLs
            Map<String, List<String>> invertedIndex = new HashMap<>();

            for (String[] line : csvLines) {
                if (line.length < 2) continue; // Falls Zeile fehlerhaft ist
                String url = line[0];
                for (int i = 1; i < line.length; i++) {
                    String keyword = line[i];
                    invertedIndex.computeIfAbsent(keyword, k -> new ArrayList<>()).add(url);
                }
            }

            Set<String[]> lines = new HashSet<>();

            for (Map.Entry<String, List<String>> entry : invertedIndex.entrySet()) {
                List<String> row = new ArrayList<>();
                row.add(entry.getKey()); // Keyword zuerst
                row.addAll(entry.getValue()); // Danach alle URLs
                lines.add(row.toArray(new String[0]));
            }


            FileWriter f = new FileWriter(invertedIndexFileName);
            //TODO: define lines to contain the lines that should be printed to inverted_index.csv
            CSVWriter writer = new CSVWriter(f,',', CSVWriter.NO_QUOTE_CHARACTER,' ',"\r\n");
            for (String[] line : lines) {
                writer.writeNext(line);
            }
            f.flush();
            f.close();

        } catch (Exception e){
            logger.error(e.getMessage());
        }

    }

}
