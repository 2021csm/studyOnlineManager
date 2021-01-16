package com.example.study.bean;

import javax.persistence.Id;

public class Source {
    @Id
    private Integer source_id;
    private String source_name;
    private String source_image;
    private String source_url;

    @Override
    public String toString() {
        return "Source{" +
                "source_id=" + source_id +
                ", source_name='" + source_name + '\'' +
                ", source_image='" + source_image + '\'' +
                ", source_url='" + source_url + '\'' +
                '}';
    }

    public Integer getSource_id() {
        return source_id;
    }

    public void setSource_id(Integer source_id) {
        this.source_id = source_id;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    public String getSource_image() {
        return source_image;
    }

    public void setSource_image(String source_image) {
        this.source_image = source_image;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }
}
