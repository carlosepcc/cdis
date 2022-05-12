package com.pid.proyecto.Json;

public class JsonFile {

    private String name;

    private String url;

    public JsonFile() {
    }

    public String getName() {
        return name;
    }

    public JsonFile(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
