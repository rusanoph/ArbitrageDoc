package ru.idr.arbitragestatistics.model;

import java.util.HashSet;
import java.util.Set;

public class TitleData {
    private Integer count;
    private Set<String> files = new HashSet<>();

    public TitleData() {}

    public TitleData(Integer count, Set<String> files) {
        this.count = count;
        this.files = files;
    }

    public void addFile(String file) {
        files.add(file);
    }


    public Set<String> getFiles() {
        return files;
    }
    public void setFiles(Set<String> files) {
        this.files = files;
    }


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    
}
