package org.jeecg.modules.online.cgform.model;

public class OnlColumn {
    private String title;

    private String dataIndex;

    private String align;

    private String customRender;

    private g scopedSlots;

    private String hrefSlotName;

    private int showLength;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDataIndex(String dataIndex) {
        this.dataIndex = dataIndex;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public void setCustomRender(String customRender) {
        this.customRender = customRender;
    }

    public void setScopedSlots(g scopedSlots) {
        this.scopedSlots = scopedSlots;
    }

    public void setHrefSlotName(String hrefSlotName) {
        this.hrefSlotName = hrefSlotName;
    }

    public void setShowLength(int showLength) {
        this.showLength = showLength;
    }

    public void setSorter(boolean sorter) {
        this.sorter = sorter;
    }

    public void setLinkField(String linkField) {
        this.linkField = linkField;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDataIndex() {
        return this.dataIndex;
    }

    public String getAlign() {
        return this.align;
    }

    public String getCustomRender() {
        return this.customRender;
    }

    public g getScopedSlots() {
        return this.scopedSlots;
    }

    public String getHrefSlotName() {
        return this.hrefSlotName;
    }

    public int getShowLength() {
        return this.showLength;
    }

    private boolean sorter = false;

    private String linkField;

    private String tableName;

    private String dbType;

    private String fieldType;

    public boolean isSorter() {
        return this.sorter;
    }

    public String getLinkField() {
        return this.linkField;
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getDbType() {
        return this.dbType;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public OnlColumn(String title, String dataIndex) {
        this.align = "center";
        this.title = title;
        this.dataIndex = dataIndex;
    }

    public OnlColumn() {
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\model\OnlColumn.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
