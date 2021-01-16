package com.example.study.bean;

import javax.persistence.Id;

public class Video {
    @Id
    private Integer id;
   private String  video_id;
   private String subject_id;
   private String video_url;
   private String video_name;


    @Override
    public String toString() {
        return "Video{" +
                "id=" + id +
                ", video_id='" + video_id + '\'' +
                ", subject_id='" + subject_id + '\'' +
                ", video_url='" + video_url + '\'' +
                ", video_name='" + video_name + '\'' +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }


    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }


}
