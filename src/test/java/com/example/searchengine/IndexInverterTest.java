package com.example.searchengine;

import com.opencsv.CSVReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.FileReader;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IndexInverterTest extends TestBase {

    private final Logger logger = LogManager.getLogger();

    @BeforeAll
    public void invertIndex(){
        IndexInverter inverter = new IndexInverter();
        inverter.invertIndex(indexFileName, invertedIndexFileName);
    }

    @Test
    public void testInverter(){
        try {
            CSVReader csvReader = new CSVReader(new FileReader(invertedIndexFileName));
            List<String[]> lines = csvReader.readAll();
            boolean hasPeaceKeyword = false;
            for (String[] line: lines){
                boolean b = testKeyword(line, "trap", createSet("/2888f12113145734","/da5125c507a6c938","/cb6c4e2e5e24aafe"));
                if (b){
                    hasPeaceKeyword = true;
                }
            }
            if (!hasPeaceKeyword){
                fail();
            }
        } catch (Exception e){
            logger.error(e.getMessage());
            fail();
        }
    }

    public boolean testKeyword(String[] line, String keyword, Set<String> urls){
        boolean b = false;
        if (line[0].equals(keyword)){
            Set<String> set = new HashSet<>(Arrays.asList(line).subList(1, line.length));
            assertEquals(set, urls);
            b = true;
        }
        return b;

    }
}
