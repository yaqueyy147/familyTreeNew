package com.witkey.familyTree.util;

import java.io.Serializable;

/**
 * Created by suyx on 2017/6/14 0014.
 */
public class PeopleTree {
    private String id;
    private String pId;
    private String name;
    private String mateName;
    private String icon;
    private boolean open;
    private String peopleStatus;
    private String isSupplement;
    private String dieAddr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMateName() {
        return mateName;
    }

    public void setMateName(String mateName) {
        this.mateName = mateName;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public String getPeopleStatus() {
        return peopleStatus;
    }

    public void setPeopleStatus(String peopleStatus) {
        this.peopleStatus = peopleStatus;
    }

    public String getIsSupplement() {
        return isSupplement;
    }

    public void setIsSupplement(String isSupplement) {
        this.isSupplement = isSupplement;
    }

    public String getDieAddr() {
        return dieAddr;
    }

    public void setDieAddr(String dieAddr) {
        this.dieAddr = dieAddr;
    }
}
