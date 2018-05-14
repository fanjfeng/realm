package com.xintu.realm.entity;

/**
 * 标题，测试dataBinding
 * Created by fanjianfeng on 2018/5/4.
 */
public class Title {

    private int id;

    private String name;

    private String demo;

    public int getId () {
        return id;
    }

    public void setId ( int id ) {
        this.id = id;
    }

    public String getName () {
        return name == null ? "" : name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public String getDemo () {
        return demo == null ? "" : demo;
    }

    public void setDemo ( String demo ) {
        this.demo = demo;
    }

    @Override
    public String toString () {
        return "Title{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", demo='" + demo + '\'' +
                '}';
    }
}
