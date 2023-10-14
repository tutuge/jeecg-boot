 package org.jeecg.modules.online.cgform.entity;

 import com.baomidou.mybatisplus.annotation.IdType;
 import com.baomidou.mybatisplus.annotation.TableId;
 import com.baomidou.mybatisplus.annotation.TableName;
 import com.fasterxml.jackson.annotation.JsonFormat;
 import java.io.Serializable;
 import java.util.Date;
 import org.jeecg.modules.online.cgform.b1.bConstant;
 import org.springframework.format.annotation.DateTimeFormat;

 @TableName("onl_cgform_field")
 public class OnlCgformField implements Serializable {
   private static final long serialVersionUID = 1L;

   @TableId(type = IdType.ASSIGN_UUID)
   private String id;

   private String cgformHeadId;

   private String dbFieldName;

   private String dbFieldTxt;

   private String dbFieldNameOld;

   private Integer dbIsKey;

   private Integer dbIsNull;

   public void setId(String id) {
     this.id = id;
   }

   public void setCgformHeadId(String cgformHeadId) {
     this.cgformHeadId = cgformHeadId;
   }

   public void setDbFieldName(String dbFieldName) {
     this.dbFieldName = dbFieldName;
   }

   public void setDbFieldTxt(String dbFieldTxt) {
     this.dbFieldTxt = dbFieldTxt;
   }

   public void setDbFieldNameOld(String dbFieldNameOld) {
     this.dbFieldNameOld = dbFieldNameOld;
   }

   public void setDbIsKey(Integer dbIsKey) {
     this.dbIsKey = dbIsKey;
   }

   public void setDbIsNull(Integer dbIsNull) {
     this.dbIsNull = dbIsNull;
   }

   public void setDbIsPersist(Integer dbIsPersist) {
     this.dbIsPersist = dbIsPersist;
   }

   public void setDbType(String dbType) {
     this.dbType = dbType;
   }

   public void setDbLength(Integer dbLength) {
     this.dbLength = dbLength;
   }

   public void setDbPointLength(Integer dbPointLength) {
     this.dbPointLength = dbPointLength;
   }

   public void setDbDefaultVal(String dbDefaultVal) {
     this.dbDefaultVal = dbDefaultVal;
   }

   public void setDictField(String dictField) {
     this.dictField = dictField;
   }

   public void setDictTable(String dictTable) {
     this.dictTable = dictTable;
   }

   public void setDictText(String dictText) {
     this.dictText = dictText;
   }

   public void setFieldShowType(String fieldShowType) {
     this.fieldShowType = fieldShowType;
   }

   public void setFieldHref(String fieldHref) {
     this.fieldHref = fieldHref;
   }

   public void setFieldLength(Integer fieldLength) {
     this.fieldLength = fieldLength;
   }

   public void setFieldValidType(String fieldValidType) {
     this.fieldValidType = fieldValidType;
   }

   public void setFieldMustInput(String fieldMustInput) {
     this.fieldMustInput = fieldMustInput;
   }

   public void setFieldExtendJson(String fieldExtendJson) {
     this.fieldExtendJson = fieldExtendJson;
   }

   public void setFieldDefaultValue(String fieldDefaultValue) {
     this.fieldDefaultValue = fieldDefaultValue;
   }

   public void setIsQuery(Integer isQuery) {
     this.isQuery = isQuery;
   }

   public void setIsShowForm(Integer isShowForm) {
     this.isShowForm = isShowForm;
   }

   public void setIsShowList(Integer isShowList) {
     this.isShowList = isShowList;
   }

   public void setIsReadOnly(Integer isReadOnly) {
     this.isReadOnly = isReadOnly;
   }

   public void setQueryMode(String queryMode) {
     this.queryMode = queryMode;
   }

   public void setMainTable(String mainTable) {
     this.mainTable = mainTable;
   }

   public void setMainField(String mainField) {
     this.mainField = mainField;
   }

   public void setOrderNum(Integer orderNum) {
     this.orderNum = orderNum;
   }

   public void setUpdateBy(String updateBy) {
     this.updateBy = updateBy;
   }

   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   public void setUpdateTime(Date updateTime) {
     this.updateTime = updateTime;
   }

   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   public void setCreateTime(Date createTime) {
     this.createTime = createTime;
   }

   public void setCreateBy(String createBy) {
     this.createBy = createBy;
   }

   public void setConverter(String converter) {
     this.converter = converter;
   }

   public void setQueryConfigFlag(String queryConfigFlag) {
     this.queryConfigFlag = queryConfigFlag;
   }

   public void setQueryDefVal(String queryDefVal) {
     this.queryDefVal = queryDefVal;
   }

   public void setQueryDictText(String queryDictText) {
     this.queryDictText = queryDictText;
   }

   public void setQueryDictField(String queryDictField) {
     this.queryDictField = queryDictField;
   }

   public void setQueryDictTable(String queryDictTable) {
     this.queryDictTable = queryDictTable;
   }

   public void setQueryShowType(String queryShowType) {
     this.queryShowType = queryShowType;
   }

   public void setQueryValidType(String queryValidType) {
     this.queryValidType = queryValidType;
   }

   public void setQueryMustInput(String queryMustInput) {
     this.queryMustInput = queryMustInput;
   }

   public void setSortFlag(String sortFlag) {
     this.sortFlag = sortFlag;
   }

   public void setAlias(String alias) {
     this.alias = alias;
   }


   public String getId() {
     return this.id;
   }

   public String getCgformHeadId() {
     return this.cgformHeadId;
   }

   public String getDbFieldName() {
     return this.dbFieldName;
   }

   public String getDbFieldTxt() {
     return this.dbFieldTxt;
   }

   public String getDbFieldNameOld() {
     return this.dbFieldNameOld;
   }

   public Integer getDbIsKey() {
     return this.dbIsKey;
   }

   public Integer getDbIsNull() {
     return this.dbIsNull;
   }

   public Integer getDbIsPersist() {
     return this.dbIsPersist;
   }

   public String getDbType() {
     return this.dbType;
   }

   public Integer getDbLength() {
     return this.dbLength;
   }

   public Integer getDbPointLength() {
     return this.dbPointLength;
   }

   public String getDbDefaultVal() {
     return this.dbDefaultVal;
   }

   public String getDictField() {
     return this.dictField;
   }

   public String getDictTable() {
     return this.dictTable;
   }

   public String getDictText() {
     return this.dictText;
   }

   public String getFieldShowType() {
     return this.fieldShowType;
   }

   public String getFieldHref() {
     return this.fieldHref;
   }

   public Integer getFieldLength() {
     return this.fieldLength;
   }

   public String getFieldValidType() {
     return this.fieldValidType;
   }

   public String getFieldMustInput() {
     return this.fieldMustInput;
   }

   public String getFieldExtendJson() {
     return this.fieldExtendJson;
   }

   public String getFieldDefaultValue() {
     return this.fieldDefaultValue;
   }

   public Integer getIsQuery() {
     return this.isQuery;
   }

   public Integer getIsShowForm() {
     return this.isShowForm;
   }

   public Integer getIsShowList() {
     return this.isShowList;
   }

   public Integer getIsReadOnly() {
     return this.isReadOnly;
   }

   public String getQueryMode() {
     return this.queryMode;
   }

   public String getMainTable() {
     return this.mainTable;
   }

   public String getMainField() {
     return this.mainField;
   }

   public Integer getOrderNum() {
     return this.orderNum;
   }

   public String getUpdateBy() {
     return this.updateBy;
   }

   public Date getUpdateTime() {
     return this.updateTime;
   }

   public Date getCreateTime() {
     return this.createTime;
   }

   public String getCreateBy() {
     return this.createBy;
   }

   public String getConverter() {
     return this.converter;
   }

   public String getQueryConfigFlag() {
     return this.queryConfigFlag;
   }

   public String getQueryDefVal() {
     return this.queryDefVal;
   }

   public String getQueryDictText() {
     return this.queryDictText;
   }

   public String getQueryDictField() {
     return this.queryDictField;
   }

   public String getQueryDictTable() {
     return this.queryDictTable;
   }

   public String getQueryShowType() {
     return this.queryShowType;
   }

   public String getQueryValidType() {
     return this.queryValidType;
   }

   public String getQueryMustInput() {
     return this.queryMustInput;
   }

   public String getSortFlag() {
     return this.sortFlag;
   }

   public String getAlias() {
     return this.alias;
   }

   private Integer dbIsPersist = bConstant.b;

   private String dbType;

   private Integer dbLength;

   private Integer dbPointLength;

   private String dbDefaultVal;

   private String dictField;

   private String dictTable;

   private String dictText;

   private String fieldShowType;

   private String fieldHref;

   private Integer fieldLength;

   private String fieldValidType;

   private String fieldMustInput;

   private String fieldExtendJson;

   private String fieldDefaultValue;

   private Integer isQuery;

   private Integer isShowForm;

   private Integer isShowList;

   private Integer isReadOnly;

   private String queryMode;

   private String mainTable;

   private String mainField;

   private Integer orderNum;

   private String updateBy;

   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date updateTime;

   @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
   @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
   private Date createTime;

   private String createBy;

   private String converter;

   private String queryConfigFlag;

   private String queryDefVal;

   private String queryDictText;

   private String queryDictField;

   private String queryDictTable;

   private String queryShowType;

   private String queryValidType;

   private String queryMustInput;

   private String sortFlag;

   private transient String alias;
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\entity\OnlCgformField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
