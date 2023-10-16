package org.jeecg.modules.online.cgreport.model;

import org.jeecg.modules.online.cgreport.entity.OnlCgreportHead;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportItem;
import org.jeecg.modules.online.cgreport.entity.OnlCgreportParam;

import java.util.Arrays;
import java.util.List;

public class OnlCgreportModel {
    private OnlCgreportHead head;

    private List<OnlCgreportParam> params;

    private String deleteParamIds;

    private List<OnlCgreportItem> items;

    private String deleteItemIds;

    public OnlCgreportHead getHead() {
        return this.head;
    }

    public void setHead(OnlCgreportHead head) {
        this.head = head;
    }

    public List<OnlCgreportParam> getParams() {
        return this.params;
    }

    public void setParams(List<OnlCgreportParam> params) {
        this.params = params;
    }

    public List<OnlCgreportItem> getItems() {
        return this.items;
    }

    public void setItems(List<OnlCgreportItem> items) {
        this.items = items;
    }

    public String getDeleteParamIds() {
        return this.deleteParamIds;
    }

    public List<String> getDeleteParamIdList() {
        return Arrays.asList(this.deleteParamIds.split(","));
    }

    public void setDeleteParamIds(String deleteParamIds) {
        this.deleteParamIds = deleteParamIds;
    }

    public String getDeleteItemIds() {
        return this.deleteItemIds;
    }

    public List<String> getDeleteItemIdList() {
        return Arrays.asList(this.deleteItemIds.split(","));
    }

    public void setDeleteItemIds(String deleteItemIds) {
        this.deleteItemIds = deleteItemIds;
    }

    public String toString() {
        return "OnlCgreportModel [head=" + this.head + ", params=" + this.params + ", deleteParamIds=" + this.deleteParamIds + ", items=" + this.items + ", deleteItemIds=" + this.deleteItemIds + "]";
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\model\OnlCgreportModel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
