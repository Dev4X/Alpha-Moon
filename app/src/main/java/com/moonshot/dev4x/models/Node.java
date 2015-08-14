package com.moonshot.dev4x.models;

public class Node {

    int id;
    String name;
    String icon;
    int viewCount;
    String content;
    String content_type;
    String content_sub_type;

    public Node() {
    }

    public Node(int id, String name, String icon, int viewCount, String content, String content_type, String content_sub_type) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.viewCount = viewCount;
        this.content = content;
        this.content_type = content_type;
        this.content_sub_type = content_sub_type;
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

    public String getContentType(){
        return this.content_type;
    }

    public void setContentType(String content_type){
        this.content_type = content_type;
    }

    public String getContentSubType(){
        return this.content_sub_type;
    }

    public void setContentSubType(String content_sub_type){
        this.content_sub_type = content_sub_type;
    }
}
