package com.moonshot.dev4x.models;

/**
 * Created by hirendave on 8/22/15.
 */
public class Content {
    int id;
    String content;
    String content_type;
    String content_sub_type;
    int nodeId;
    public Content(){

    }
    public Content(int id, String content, String content_type, String content_sub_type) {
        this.id = id;
        this.content = content;
        this.content_type = content_type;
        this.content_sub_type = content_sub_type;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getContent(){
        return this.content;
    }

    public void setContent(String content){
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

    public int getNodeId(){
        return this.nodeId;
    }

    public void setNodeId(int nodeId){
        this.nodeId = nodeId;
    }
}
