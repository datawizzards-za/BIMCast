package com.itechhubsa.bimanage.Pojos;

import java.util.Date;

public class Fault {
    private String report_description;
    private int unit_number;
    private String parking_space;
    private String imageUrl;
    private Long report_date;

    public Fault(String report_description, int unit_number, String parking_space, String imageUrl) {
        this.report_description = report_description;
        this.unit_number = unit_number;
        this.parking_space = parking_space;
        this.imageUrl = imageUrl;
        this.report_date = new Date().getTime();
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

    public String getParking_space() {
        return parking_space;
    }

    public void setParking_space(String parking_space) {
        this.parking_space = parking_space;
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
