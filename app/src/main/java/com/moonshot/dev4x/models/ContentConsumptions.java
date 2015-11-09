package com.moonshot.dev4x.models;

/**
 * Created by hirendave on 8/22/15.
 */
public class ContentConsumptions {

    int cid;
    int content_id;
    int node_id;
    int skill_id;

    public ContentConsumptions(){

    }
    public ContentConsumptions(int cid, int content_id, int node_id, int skill_id) {
        this.cid = cid;
        this.content_id = content_id;
        this.node_id = node_id;
        this.skill_id = skill_id;
    }

    public int getCid() {
        return this.cid;
    }

    public void setCid(int id) {
        this.cid = id;
    }

    public int getSkillId() {
        return this.skill_id;
    }

    public void setSkillId(int skill_id) {
        this.skill_id = skill_id;
    }

    public int getContentId() {
        return this.content_id;
    }

    public void setContentId(int content_id) {
        this.content_id = content_id;
    }

    public int getNodeId() {
        return this.node_id;
    }

    public void setNodeId(int node_id) {
        this.node_id = node_id;
    }
}
