package com.moonshot.dev4x.models;

public class Node {

    int id;
    String name;
    String icon;
    int viewCount;
    String content;

    public Node() {
    }

    public Node(int id, String name, String icon, int viewCount, String content) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.viewCount = viewCount;
        this.content = content;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
