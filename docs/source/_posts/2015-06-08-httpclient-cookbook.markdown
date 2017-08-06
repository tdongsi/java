---
layout: post
title: "HttpClient cookbook"
date: 2015-06-08 17:14:29 -0700
comments: true
categories: 
- Java
---

Apache HttpClient library is regularly used in Java program to query REST endpoints.

<!--more-->

### Beginner Cookbook

Based on HttpClient Tutorial (version 4.3).

**Recipe 1**: For simple use cases, it is better to use [Fluent API](https://hc.apache.org/httpcomponents-client-ga/tutorial/html/fluent.html).

``` java Fluent API
    private static String API_ENDPOINT = "http://api.icndb.com/jokes/random";
    String see = Request.Get(API_ENDPOINT)
            .execute()
            .returnContent()
            .asString();
    assertFalse(see.isEmpty());
    System.out.println(see);
```

**Recipe 2**: Consuming response entities originate from a trusted HTTP server and are known to be of limited length. From [here](https://hc.apache.org/httpcomponents-client-ga/tutorial/html/fundamentals.html). [Another variant](http://www.vogella.com/tutorials/ApacheHttpClient/article.html).

``` java Consuming entity content
CloseableHttpClient httpclient = HttpClients.createDefault();
HttpGet httpget = new HttpGet("http://localhost/");
CloseableHttpResponse response = httpclient.execute(httpget);
try {
    HttpEntity entity = response.getEntity();
    if (entity != null) {
        long len = entity.getContentLength();
        if (len != -1 && len < 2048) {
            System.out.println(EntityUtils.toString(entity));
        } else {
            // Stream content out
        }
    }
} finally {
    response.close();
}
```

### Advanced Cookbook

#### Version 4.1.x -> 4.2.1

Git2Gus service uses HttpClient 4.2.1 (httpclient-4.2.1.jar).

**Recipe 1**: Proxy setup. Based on [this](https://stackoverflow.com/questions/9811828/common-httpclient-and-proxy):

``` java Setting up proxy
    HttpHost proxy = new HttpHost("127.0.0.1", 8080, "http");

    DefaultHttpClient httpclient = new DefaultHttpClient();
    try {
        httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);

        HttpHost target = new HttpHost("issues.apache.org", 443, "https");
        HttpGet req = new HttpGet("/");

        System.out.println("executing request to " + target + " via " + proxy);
        HttpResponse rsp = httpclient.execute(target, req);
        ...
    } finally {
        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();
    }
```

With proxy authentication? Check out [this](https://stackoverflow.com/questions/13288038/httpclient-4-2-2-and-proxy-with-username-password)

**Recipe 2**: Connection timeout. Based on [this](https://gist.github.com/tychobrailleur/9fbf521727539b403c90):

``` java Conneciton timeout
public class RunTest {

    @Test
    public void runDefaultClient() throws Exception {
        URI uri = new URL("http://localhost:3000/").toURI();

        HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());
        DefaultHttpClient httpClient = new DefaultHttpClient();

        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 10000);

        HttpGet message = new HttpGet("/");
        HttpResponse response = httpClient.execute(host, message);

        System.out.println(response.getStatusLine());
    }
}
```

#### Later version

**Recipe 1**: Based on [this](http://hc.apache.org/httpcomponents-client-ga/httpclient/examples/org/apache/http/examples/client/ClientExecuteProxy.java)

``` java Setting up proxy
public class ClientExecuteProxy {

    public static void main(String[] args)throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpHost target = new HttpHost("httpbin.org", 443, "https");
            HttpHost proxy = new HttpHost("127.0.0.1", 8080, "http");

            RequestConfig config = RequestConfig.custom()
                    .setProxy(proxy)
                    .build();
            HttpGet request = new HttpGet("/");
            request.setConfig(config);

            System.out.println("Executing request " + request.getRequestLine() + " to " + target + " via " + proxy);

            CloseableHttpResponse response = httpclient.execute(target, request);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());
                System.out.println(EntityUtils.toString(response.getEntity()));
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

}
```

### Reference

* [Project Home](https://hc.apache.org/index.html)
* [JavaDoc](https://hc.apache.org/httpcomponents-client-ga/httpclient/apidocs/index.html)
* Alternative: [Jetty HttpClient](https://www.eclipse.org/jetty/documentation/9.4.6.v20170531/http-client.html).

Apache HttpClient 4.2.1

* [Maven](https://mvnrepository.com/artifact/org.apache.httpcomponents/httpclient/4.2.1)
