package com.example.searchengine;

import com.opencsv.CSVReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class CrawlerTest extends TestBase{

    private final Logger logger = LogManager.getLogger();

    @BeforeAll
    public void crawl(){
        Crawler c = generateCrawler();
        c.crawl(startUrl);

    }

    @Test
    public void testCrawlFirstUrl(){
        try {
            logger.info("test crawl 1");
            CSVReader csvReader = new CSVReader(new FileReader(indexFileName));
            List<String[]> lines = csvReader.readAll();
            boolean hasUrl7ab162649f1bb44 = false;
            for (String[] line: lines){
                boolean b = testUrl(line, "/7ab162649f1bb44", createSet("warning", "doug", "rebecca"));
                if (b){
                    hasUrl7ab162649f1bb44 = true;
                }
            }
            if (!hasUrl7ab162649f1bb44){
                fail();
            }
        } catch (Exception e){
            logger.error(e.getMessage());
            fail();
        }
    }

    @Test
    public void testCrawlSecondUrl(){
        try {
            logger.info("test crawl 2");
            CSVReader csvReader = new CSVReader(new FileReader(indexFileName));
            List<String[]> lines = csvReader.readAll();
            boolean hasUrl1bd38608b1a6cf7e = false;
            for (String[] line: lines){
                boolean b = testUrl(line,"/1bd38608b1a6cf7e", createSet("tied", "outer", "situation"));

                if (b){
                    hasUrl1bd38608b1a6cf7e = true;
                }

            }
            if ( !hasUrl1bd38608b1a6cf7e){
                fail();
            }
        } catch (Exception e){
            logger.error(e.getMessage());
            fail();
        }
    }
    @Test
    public void testCrawlThirdUrl(){
        try {
            logger.info("test crawl 3");
            CSVReader csvReader = new CSVReader(new FileReader(indexFileName));
            List<String[]> lines = csvReader.readAll();
            boolean hasUrl7721490c7ca6df6a = false;
            for (String[] line: lines){
                boolean b = testUrl(line,"/7721490c7ca6df6a", createSet("shorts", "takes", "nodes"));

                if (b){
                    hasUrl7721490c7ca6df6a = true;
                }

            }
            if ( !hasUrl7721490c7ca6df6a){
                fail();
            }
        } catch (Exception e){
            logger.error(e.getMessage());
            fail();
        }
    }

    @Test
    public void testIndexHas2000Lines() {
        try {
            logger.info("test index line count");
            CSVReader csvReader = new CSVReader(new FileReader(indexFileName));
            List<String[]> lines = csvReader.readAll();
            assertEquals(2000, lines.size());
        } catch (Exception e) {
            logger.error(e.getMessage());
            fail();
        }
    }


    public boolean testUrl(String[] line, String url, Set<String> values){
        boolean b = false;
        if (line[0].equals(url)){
            Set<String> set = new HashSet<>(Arrays.asList(line).subList(1, line.length));
            assertEquals(set, values);
            b = true;
        }
        return b;
    }

    public abstract Crawler generateCrawler();
}
