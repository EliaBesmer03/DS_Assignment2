package com.example.searchengine;

import org.junit.jupiter.api.Test;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearcherTest extends TestBase {

    @Test
    public void testSearch(){
        Searcher searcher = new Searcher();
        Set<String> urls = new HashSet<>(searcher.search("examine", invertedIndexFileName));
        assertEquals(createSet("https://api.interactions.ics.unisg.ch/hypermedia-environment/5899f56058a1801a", "https://api.interactions.ics.unisg.ch/hypermedia-environment/3e6da20c596b18df", "https://api.interactions.ics.unisg.ch/hypermedia-environment/7d9b4ec090309f85"), urls);
    }

}
