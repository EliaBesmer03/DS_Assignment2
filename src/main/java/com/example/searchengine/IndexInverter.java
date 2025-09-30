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
            Set<String[]> lines = new HashSet<>();
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
