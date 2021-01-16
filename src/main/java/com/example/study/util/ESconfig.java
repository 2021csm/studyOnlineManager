package com.example.study.util;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "esconfig")
public class ESconfig {
    private String hostname="127.0.0.1";
    private Integer port=9200;
    private String scheme="http";


    @Bean
    RestHighLevelClient elasticsearchClient() {
        // HttpHost httpHost=new HttpHost("127.0.0.1",9200,"http");
        HttpHost httpHost=new HttpHost(this.hostname,this.port,this.scheme);
        RestClientBuilder restClientBuilder= RestClient.builder(httpHost);
        return  new RestHighLevelClient(restClientBuilder);
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }
}
