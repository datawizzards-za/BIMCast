package com.itechhubsa.bimanage.Pojos;

import java.io.Serializable;
import java.util.Date;

public class Fault implements Serializable {
    private String report_description;
    private int unit_number;
    private String fault;
    private String unit_location;
    private String imageUrl;
    private Long report_date;

    public Fault(String report_description, int unit_number, String unit_location, String fault, String imageUrl) {
        this.report_description = report_description;
        this.unit_number = unit_number;
        this.unit_location = unit_location;
        this.imageUrl = imageUrl;
        this.fault = fault;
        this.report_date = new Date().getTime();
    }

    public String getFault() {
        return fault;
    }

    public void setFault(String fault) {
        this.fault = fault;
    }

    public Fault() {

    }

    public String getReport_description() {
        return report_description;
    }

    public void setReport_description(String report_description) {
        this.report_description = report_description;
    }

    public int getUnit_number() {
        return unit_number;
    }

    public void setUnit_number(int unit_number) {
        this.unit_number = unit_number;
    }

    public String getUnit_location() {
        return unit_location;
    }

    public void setUnit_location(String unit_location) {
        this.unit_location = unit_location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getReport_date() {
        return report_date;
    }

    public void setReport_date(Long report_date) {
        this.report_date = report_date;
    }
}
