## Elevio Challenge Project

### Documentation

#### Setup

Before using the application the `conf/api.conf.sample` needs to be copied over to `conf/api.conf` 
and the api key and token you want to use need to be specified. Alternatively for the purpose of this demo I have
added a `conf/api.conf` with my read only key/token to access that data, that I had used for development and testing 
(I had added some text form your API documentation as articles).

The application was written using the Play 2.7 framework in IntelliJ - so reviewing it in IntelliJ should work out 
of the box.


#### Reviewing the application

If you would like to change the number of articles shown in the list and in the search results, then you can
modify the `num_articles` parameter in `conf/api.conf`. For example to test the paginator you can `num_articles=1` 
which would give you lots of pages (with one article title each).

To test the search feature: If using my data (my key/token) then searching for "api" will give you lots of 
results. 


#### Tests

still working on those ...
