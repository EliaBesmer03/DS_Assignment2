package com.example.searchengine;

import com.opencsv.CSVWriter;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

public class MultithreadCrawler extends Crawler {

    private final ThreadPoolTaskExecutor executorService;
    private final Set<String> visited = ConcurrentHashMap.newKeySet();
    private final ConcurrentHashMap<String, String[]> map = new ConcurrentHashMap<>();

    public MultithreadCrawler(String indexFileName) {
        super(indexFileName);
        this.executorService = new ThreadPoolTaskExecutor();
        int cores = Runtime.getRuntime().availableProcessors();
        this.executorService.setCorePoolSize(cores * 2);
        this.executorService.setMaxPoolSize(cores * 2);
        this.executorService.setQueueCapacity(2000);
        this.executorService.initialize();
    }

    @Override
    public void crawl(String startUrl) {
        long startTime = System.currentTimeMillis();

        // Start initial task
        executorService.submit(new CrawlerRunnable(this, startUrl));

        // Warte, bis alle Tasks fertig sind
        while (true) {
            if (executorService.getActiveCount() == 0 && executorService.getThreadPoolExecutor().getQueue().isEmpty()) {
                break;
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        executorService.shutdown();

        // Schreibe Ergebnisse
        try (FileWriter fileWriter = new FileWriter(indexFileName);
             CSVWriter writer = new CSVWriter(fileWriter, ',', CSVWriter.NO_QUOTE_CHARACTER, ' ', "\r\n")) {
            for (String[] row : map.values()) {
                writer.writeNext(row);
            }
        } catch (IOException e) {
            logger.error("Error writing index.csv: {}", e.getMessage());
        }

        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Crawl completed in " + duration + " ms");
        logger.info("duration multithread crawler: {} ms", duration);
    }

    record CrawlerRunnable(MultithreadCrawler crawler, String startUrl) implements Runnable {

        @Override
        public void run() {
            try {
                if (!crawler.visited.add(startUrl)) return;

                List<List<String>> info = crawler.getInfo(startUrl);
                if (info == null || info.size() < 2) return;

                List<String> keywords = info.get(0);
                List<String> hyperlinks = info.get(1);

                // relative URL
                String relativeUrl = startUrl.replaceFirst("^https://api\\.interactions\\.ics\\.unisg\\.ch/hypermedia-environment", "");
                if (!relativeUrl.startsWith("/")) relativeUrl = "/" + relativeUrl;

                String[] row = new String[keywords.size() + 1];
                row[0] = relativeUrl;
                for (int i = 0; i < keywords.size(); i++) {
                    row[i + 1] = keywords.get(i);
                }

                crawler.map.put(startUrl, row);

                // neue Links
                String base = "https://api.interactions.ics.unisg.ch/hypermedia-environment/";
                for (String link : hyperlinks) {
                    String fullUrl = link.startsWith("http") ? link : base + link.replaceFirst("^/", "");
                    if (!crawler.visited.contains(fullUrl)) {
                        crawler.executorService.submit(new CrawlerRunnable(crawler, fullUrl));
                    }
                }

            } catch (Exception e) {
                crawler.logger.error("Error processing {}: {}", startUrl, e.getMessage());
            }
        }
    }
}
