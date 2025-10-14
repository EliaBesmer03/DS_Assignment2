Assignment 2
------------

# Team Members
Elia Besmer
Leon Schwendener

# GitHub link to your (forked) repository
https://github.com/EliaBesmer03/DS_Assignment2

...

# Task 1

1. Indicate the time necessary for the SimpleCrawler to work.

Ans: Crawl completed in 122613 ms



# Task 2

1. Explain why it is useful to invert the index before implementing  a program to search the URLs corresponding to a keyword in the index.

Ans: Inverting the index is useful because it transforms the data structure from page → keywords to keyword → pages. 
This allows the search engine to directly retrieve all URLs containing a specific term without scanning every document linearly.

# Task 3

1. Explain your design choices for the API design.

Ans:
The administrative interface was added to the OpenAPI specification following RESTful design principles.
Each operation uses the appropriate HTTP method according to its semantics:
•	POST /admin/crawl launches a new crawling process from a given seed URL.
•	GET /admin/status retrieves the current crawler and index status (fields crawlerFree and indexUpToDate).
•	POST /admin/reindex regenerates the inverted index after a crawling operation.
•	DELETE /admin/delete removes a given URL from the index.
•	PUT /admin/update updates or adds keywords for a specific URL.

All administrative operations are secured with bearer token authentication, ensuring that only authorized users can trigger management actions.
No mechanism for obtaining the token was implemented, as stated in the task description.

# Task 4

1.  Indicate the time necessary for the MultithreadCrawler to work.

Ans: Crawl completed in 11672 ms


2. Indicate the ratio of the time for the SimpleCrawler divided by the time for the MultithreadCrawler to get the increase in speed.

Ans: 122613 / 11672 ≈ 10.5x faster


