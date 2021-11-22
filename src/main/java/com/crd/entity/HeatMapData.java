package com.crd.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;

/**
 * Heatmap Data object
 *
 * @author YuCheng
 */
public class HeatMapData implements Serializable {
    private static final long serialVersionUID = 5530454436970805656L;

    private static Logger logger = LoggerFactory.getLogger(HeatMapData.class);

    private String timestamp;
    private String AlertId;
    private String Severity;
    private String accurateValue;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getAlertId() {
        return AlertId;
    }

    public void setAlertId(String alertId) {
        AlertId = alertId;
    }

    public String getSeverity() {
        return Severity;
    }

    public void setSeverity(String severity) {
        Severity = severity;
    }

    public String getAccurateValue() {
        return accurateValue;
    }

    public void setAccurateValue(String accurateValue) {
        this.accurateValue = accurateValue;
    }

    /**
     * Constructor
     */
    public HeatMapData() {

    }


}