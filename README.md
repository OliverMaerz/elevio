## Elevio Challenge Project

### Documentation

#### Setup

Before using the application the `conf/api.conf.sample` needs to be copied over to `conf/api.conf` 
and the API key and token you want to use need to be specified. 

The application was written using the Play 2.7 framework in IntelliJ - so reviewing it in IntelliJ should work out 
of the box.

#### Articles menu

Select the articles menu to see a list of all articles. Then click on one of the titles to read the article.

#### Search

Select the search menu to open a search form. Enter the keywords you want to search for and click on search. 
The titles of the articles with those keywords are then shown in a list. Click on any title of the search results to read the article. 

#### Reviewing the application

If you would like to change the number of articles shown in the list and in the search results, then you can
modify the `num_articles` parameter in `conf/api.conf`. For example, to test the paginator you can `num_articles=1` 
which would give you lots of pages (with one article title each).

To test the search feature: If using my data (my key/token) then searching for "api" will give you lots of results. 

