package org.jeecg.modules.online.cgform.model;

public class TreeModel {
    private String label;

    private String store;

    private String id;

    private String pid;

    public void setLabel(String label) {
        this.label = label;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLabel() {
        return this.label;
    }

    public String getStore() {
        return this.store;
    }

    public String getId() {
        return this.id;
    }

    public String getPid() {
        return this.pid;
    }
}
