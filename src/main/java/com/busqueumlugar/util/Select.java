package com.busqueumlugar.util;

import java.io.Serializable;

public class Select implements Serializable, Comparable<Select> {

    private String key;
    private String label;

    public Select(String key, String label) {
        this.key = key;
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int compareTo(Select select) {
        Integer esse = Integer.valueOf(this.getKey());
        Integer novo = Integer.valueOf(select.getKey());
        return esse.intValue() - novo.intValue();
    }

}