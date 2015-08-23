package com.moonshot.dev4x.models;

/**
 * Created by hirendave on 8/22/15.
 */
public class SkillSets {
    int id;
    String name;
    String icon;
    int viewCount;

    public SkillSets() {
    }

    public SkillSets(int id, String name, String icon, int viewCount) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.viewCount = viewCount;
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
}
