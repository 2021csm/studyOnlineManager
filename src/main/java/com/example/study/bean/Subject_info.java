package com.example.study.bean;

import javax.persistence.Id;
import java.io.Serializable;

public class Subject_info implements Serializable{
    @Id
    private String subject_info_id;
    private String subject_id;
    private String subject_info_name;
    private String value_list;

    @Override
    public String toString() {
        return "Subject_info{" +
                "subject_info_id='" + subject_info_id + '\'' +
                ", subject_id='" + subject_id + '\'' +
                ", subject_info_name='" + subject_info_name + '\'' +
                ", value_list='" + value_list + '\'' +
                '}';
    }

    public String getSubject_info_id() {
        return subject_info_id;
    }

    public void setSubject_info_id(String subject_info_id) {
        this.subject_info_id = subject_info_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getSubject_info_name() {
        return subject_info_name;
    }

    public void setSubject_info_name(String subject_info_name) {
        this.subject_info_name = subject_info_name;
    }

    public String getValue_list() {
        return value_list;
    }

    public void setValue_list(String value_list) {
        this.value_list = value_list;
    }


}
