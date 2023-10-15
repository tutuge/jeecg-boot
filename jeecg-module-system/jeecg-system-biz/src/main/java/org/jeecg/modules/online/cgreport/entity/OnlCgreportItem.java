 package org.jeecg.modules.online.cgreport.entity;

 import com.baomidou.mybatisplus.annotation.IdType;
 import com.baomidou.mybatisplus.annotation.TableId;
 import com.baomidou.mybatisplus.annotation.TableName;
 import com.fasterxml.jackson.annotation.JsonFormat;
 import java.io.Serializable;
 import java.util.Date;
 import org.springframework.format.annotation.DateTimeFormat;

 @TableName("onl_cgreport_item")
 public class OnlCgreportItem implements Serializable {
   private static final long serialVersionUID = 1L;

   @TableId(type = IdType.ASSIGN_ID)
   private String id;

   private String cgrheadId;

   private String fieldName;

   private String fieldTxt;

   private Integer fieldWidth;

   private String fieldType;

   private String searchMode;

   private Integer isOrder;

   private Integer isSearch;

   private String dictCode;

   private String fieldHref;

   private Integer isShow;

   private Integer orderNum;

   private String replaceVal;

   private String isTotal;

   private String createBy;

   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date createTime;

   private String updateBy;

   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date updateTime;

   private String groupTitle;

   public void setId(String id) {
     this.id = id;
   }

   public void setCgrheadId(String cgrheadId) {
     this.cgrheadId = cgrheadId;
   }

   public void setFieldName(String fieldName) {
     this.fieldName = fieldName;
   }

   public void setFieldTxt(String fieldTxt) {
     this.fieldTxt = fieldTxt;
   }

   public void setFieldWidth(Integer fieldWidth) {
     this.fieldWidth = fieldWidth;
   }

   public void setFieldType(String fieldType) {
     this.fieldType = fieldType;
   }

   public void setSearchMode(String searchMode) {
     this.searchMode = searchMode;
   }

   public void setIsOrder(Integer isOrder) {
     this.isOrder = isOrder;
   }

   public void setIsSearch(Integer isSearch) {
     this.isSearch = isSearch;
   }

   public void setDictCode(String dictCode) {
     this.dictCode = dictCode;
   }

   public void setFieldHref(String fieldHref) {
     this.fieldHref = fieldHref;
   }

   public void setIsShow(Integer isShow) {
     this.isShow = isShow;
   }

   public void setOrderNum(Integer orderNum) {
     this.orderNum = orderNum;
   }

   public void setReplaceVal(String replaceVal) {
     this.replaceVal = replaceVal;
   }

   public void setIsTotal(String isTotal) {
     this.isTotal = isTotal;
   }

   public void setCreateBy(String createBy) {
     this.createBy = createBy;
   }

   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   public void setCreateTime(Date createTime) {
     this.createTime = createTime;
   }

   public void setUpdateBy(String updateBy) {
     this.updateBy = updateBy;
   }

   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   public void setUpdateTime(Date updateTime) {
     this.updateTime = updateTime;
   }

   public void setGroupTitle(String groupTitle) {
     this.groupTitle = groupTitle;
   }

   public String getId() {
     return this.id;
   }

   public String getCgrheadId() {
     return this.cgrheadId;
   }

   public String getFieldName() {
     return this.fieldName;
   }

   public String getFieldTxt() {
     return this.fieldTxt;
   }

   public Integer getFieldWidth() {
     return this.fieldWidth;
   }

   public String getFieldType() {
     return this.fieldType;
   }

   public String getSearchMode() {
     return this.searchMode;
   }

   public Integer getIsOrder() {
     return this.isOrder;
   }

   public Integer getIsSearch() {
     return this.isSearch;
   }

   public String getDictCode() {
     return this.dictCode;
   }

   public String getFieldHref() {
     return this.fieldHref;
   }

   public Integer getIsShow() {
     return this.isShow;
   }

   public Integer getOrderNum() {
     return this.orderNum;
   }

   public String getReplaceVal() {
     return this.replaceVal;
   }

   public String getIsTotal() {
     return this.isTotal;
   }

   public String getCreateBy() {
     return this.createBy;
   }

   public Date getCreateTime() {
     return this.createTime;
   }

   public String getUpdateBy() {
     return this.updateBy;
   }

   public Date getUpdateTime() {
     return this.updateTime;
   }

   public String getGroupTitle() {
     return this.groupTitle;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgreport\entity\OnlCgreportItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
