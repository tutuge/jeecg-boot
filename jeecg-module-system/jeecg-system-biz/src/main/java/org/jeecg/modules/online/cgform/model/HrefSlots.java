package org.jeecg.modules.online.cgform.model;

public class HrefSlots {
    private String slotName;

    private String href;

    public void setSlotName(String slotName) {
        this.slotName = slotName;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String toString() {
        return "HrefSlots(slotName=" + getSlotName() + ", href=" + getHref() + ")";
    }

    public String getSlotName() {
        return this.slotName;
    }

    public String getHref() {
        return this.href;
    }

    public HrefSlots() {
    }

    public HrefSlots(String slotName, String href) {
        this.slotName = slotName;
        this.href = href;
    }
}
