package com.moonshot.dev4x.models;

public class Node {

    int _id;
    String _name;
    String _icon;
    int _viewCount;
    String _content;

    public Node() {
    }

    public Node(int id, String name, String icon, int viewCount, String content) {
        this._id = id;
        this._name = name;
        this._icon = icon;
        this._viewCount = viewCount;
        this._content = content;
    }

    public String getName() {
        return this._name;
    }

    public void setName(String name) {
        this._name = name;
    }

    public int getId() {
        return this._id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public String getIcon() {
        return this._icon;
    }

    public void setIcon(String icon) {
        this._icon = icon;
    }

    public int get_viewCount() {
        return _viewCount;
    }

    public void set_viewCount(int _viewCount) {
        this._viewCount = _viewCount;
    }

    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }
}
