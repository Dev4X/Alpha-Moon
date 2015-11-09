package com.moonshot.dev4x.models;

public class Node {

    int id;
    int skill_id;
    int content_id;


    public Node() {
    }

    public Node(int id, int skill_id, int content_id) {
        this.id = id;
        this.skill_id = skill_id;
        this.content_id = content_id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
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


}
