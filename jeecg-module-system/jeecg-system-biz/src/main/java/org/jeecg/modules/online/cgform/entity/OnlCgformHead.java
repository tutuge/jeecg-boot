package org.jeecg.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@TableName("onl_cgform_head")
public class OnlCgformHead implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String tableName;

    private Integer tableType;

    private Integer tableVersion;

    private String tableTxt;

    private String isCheckbox;

    private String isDbSynch;

    private String isPage;

    private String isTree;

    private String idSequence;

    private String idType;

    private String queryMode;

    private Integer relationType;

    private String subTableStr;

    private Integer tabOrderNum;

    private String treeParentIdField;

    private String treeIdField;

    private String treeFieldname;

    private String formCategory;

    private String formTemplate;

    private String themeTemplate;

    private String formTemplateMobile;

    private String extConfigJson;

    private String updateBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    private String createBy;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer copyType;

    private Integer copyVersion;

    private String physicId;

    private transient Integer hascopy;

    private Integer scroll;

    private transient String taskId;

    private String isDesForm;

    private String desFormCode;

    private String lowAppId;

    private transient String selectFieldString;

    public void setId(String id) {
        
        this.id = id;
    }

    public void setTableName(String tableName) {
        
        this.tableName = tableName;
    }

    public void setTableType(Integer tableType) {
        
        this.tableType = tableType;
    }

    public void setTableVersion(Integer tableVersion) {
        
        this.tableVersion = tableVersion;
    }

    public void setTableTxt(String tableTxt) {
        
        this.tableTxt = tableTxt;
    }

    public void setIsCheckbox(String isCheckbox) {
        
        this.isCheckbox = isCheckbox;
    }

    public void setIsDbSynch(String isDbSynch) {
        
        this.isDbSynch = isDbSynch;
    }

    public void setIsPage(String isPage) {
        
        this.isPage = isPage;
    }

    public void setIsTree(String isTree) {
        
        this.isTree = isTree;
    }

    public void setIdSequence(String idSequence) {
        
        this.idSequence = idSequence;
    }

    public void setIdType(String idType) {
        
        this.idType = idType;
    }

    public void setQueryMode(String queryMode) {
        
        this.queryMode = queryMode;
    }

    public void setRelationType(Integer relationType) {
        
        this.relationType = relationType;
    }

    public void setSubTableStr(String subTableStr) {
        
        this.subTableStr = subTableStr;
    }

    public void setTabOrderNum(Integer tabOrderNum) {
        
        this.tabOrderNum = tabOrderNum;
    }

    public void setTreeParentIdField(String treeParentIdField) {
        
        this.treeParentIdField = treeParentIdField;
    }

    public void setTreeIdField(String treeIdField) {
        
        this.treeIdField = treeIdField;
    }

    public void setTreeFieldname(String treeFieldname) {
        
        this.treeFieldname = treeFieldname;
    }

    public void setFormCategory(String formCategory) {
        
        this.formCategory = formCategory;
    }

    public void setFormTemplate(String formTemplate) {
        
        this.formTemplate = formTemplate;
    }

    public void setThemeTemplate(String themeTemplate) {
        
        this.themeTemplate = themeTemplate;
    }

    public void setFormTemplateMobile(String formTemplateMobile) {
        
        this.formTemplateMobile = formTemplateMobile;
    }

    public void setExtConfigJson(String extConfigJson) {
        
        this.extConfigJson = extConfigJson;
    }

    public void setUpdateBy(String updateBy) {
        
        this.updateBy = updateBy;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setUpdateTime(Date updateTime) {
        
        this.updateTime = updateTime;
    }

    public void setCreateBy(String createBy) {
        
        this.createBy = createBy;
    }

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public void setCreateTime(Date createTime) {
        
        this.createTime = createTime;
    }

    public void setCopyType(Integer copyType) {
        
        this.copyType = copyType;
    }

    public void setCopyVersion(Integer copyVersion) {
        
        this.copyVersion = copyVersion;
    }

    public void setPhysicId(String physicId) {
        
        this.physicId = physicId;
    }

    public void setHascopy(Integer hascopy) {
        
        this.hascopy = hascopy;
    }

    public void setScroll(Integer scroll) {
        
        this.scroll = scroll;
    }

    public void setTaskId(String taskId) {
        
        this.taskId = taskId;
    }

    public void setIsDesForm(String isDesForm) {
        
        this.isDesForm = isDesForm;
    }

    public void setDesFormCode(String desFormCode) {
        
        this.desFormCode = desFormCode;
    }

    public void setLowAppId(String lowAppId) {
        
        this.lowAppId = lowAppId;
    }

    public void setSelectFieldString(String selectFieldString) {
        
        this.selectFieldString = selectFieldString;
    }

    public boolean equals(Object o) {
        
        if (o == this)
             return true;
        
        if (!(o instanceof OnlCgformHead))
             return false;
        
        OnlCgformHead onlCgformHead = (OnlCgformHead) o;
        
        if (!onlCgformHead.canEqual(this))
             return false;
        
        Integer integer1 = getTableType(), integer2 = onlCgformHead.getTableType();
        
        if ((integer1 == null) ? (integer2 != null) : !integer1.equals(integer2))
             return false;
        
        Integer integer3 = getTableVersion(), integer4 = onlCgformHead.getTableVersion();
        
        if ((integer3 == null) ? (integer4 != null) : !integer3.equals(integer4))
             return false;
        
        Integer integer5 = getRelationType(), integer6 = onlCgformHead.getRelationType();
        
        if ((integer5 == null) ? (integer6 != null) : !integer5.equals(integer6))
             return false;
        
        Integer integer7 = getTabOrderNum(), integer8 = onlCgformHead.getTabOrderNum();
        
        if ((integer7 == null) ? (integer8 != null) : !integer7.equals(integer8))
             return false;
        
        Integer integer9 = getCopyType(), integer10 = onlCgformHead.getCopyType();
        
        if ((integer9 == null) ? (integer10 != null) : !integer9.equals(integer10))
             return false;
        
        Integer integer11 = getCopyVersion(), integer12 = onlCgformHead.getCopyVersion();
        
        if ((integer11 == null) ? (integer12 != null) : !integer11.equals(integer12))
             return false;
        
        Integer integer13 = getScroll(), integer14 = onlCgformHead.getScroll();
        
        if ((integer13 == null) ? (integer14 != null) : !integer13.equals(integer14))
             return false;
        
        String str1 = getId(), str2 = onlCgformHead.getId();
        
        if ((str1 == null) ? (str2 != null) : !str1.equals(str2))
             return false;
        
        String str3 = getTableName(), str4 = onlCgformHead.getTableName();
        
        if ((str3 == null) ? (str4 != null) : !str3.equals(str4))
             return false;
        
        String str5 = getTableTxt(), str6 = onlCgformHead.getTableTxt();
        
        if ((str5 == null) ? (str6 != null) : !str5.equals(str6))
             return false;
        
        String str7 = getIsCheckbox(), str8 = onlCgformHead.getIsCheckbox();
        
        if ((str7 == null) ? (str8 != null) : !str7.equals(str8))
             return false;
        
        String str9 = getIsDbSynch(), str10 = onlCgformHead.getIsDbSynch();
        
        if ((str9 == null) ? (str10 != null) : !str9.equals(str10))
             return false;
        
        String str11 = getIsPage(), str12 = onlCgformHead.getIsPage();
        
        if ((str11 == null) ? (str12 != null) : !str11.equals(str12))
             return false;
        
        String str13 = getIsTree(), str14 = onlCgformHead.getIsTree();
        
        if ((str13 == null) ? (str14 != null) : !str13.equals(str14))
             return false;
        
        String str15 = getIdSequence(), str16 = onlCgformHead.getIdSequence();
        
        if ((str15 == null) ? (str16 != null) : !str15.equals(str16))
             return false;
        
        String str17 = getIdType(), str18 = onlCgformHead.getIdType();
        
        if ((str17 == null) ? (str18 != null) : !str17.equals(str18))
             return false;
        
        String str19 = getQueryMode(), str20 = onlCgformHead.getQueryMode();
        
        if ((str19 == null) ? (str20 != null) : !str19.equals(str20))
             return false;
        
        String str21 = getSubTableStr(), str22 = onlCgformHead.getSubTableStr();
        
        if ((str21 == null) ? (str22 != null) : !str21.equals(str22))
             return false;
        
        String str23 = getTreeParentIdField(), str24 = onlCgformHead.getTreeParentIdField();
        
        if ((str23 == null) ? (str24 != null) : !str23.equals(str24))
             return false;
        
        String str25 = getTreeIdField(), str26 = onlCgformHead.getTreeIdField();
        
        if ((str25 == null) ? (str26 != null) : !str25.equals(str26))
             return false;
        
        String str27 = getTreeFieldname(), str28 = onlCgformHead.getTreeFieldname();
        
        if ((str27 == null) ? (str28 != null) : !str27.equals(str28))
             return false;
        
        String str29 = getFormCategory(), str30 = onlCgformHead.getFormCategory();
        
        if ((str29 == null) ? (str30 != null) : !str29.equals(str30))
             return false;
        
        String str31 = getFormTemplate(), str32 = onlCgformHead.getFormTemplate();
        
        if ((str31 == null) ? (str32 != null) : !str31.equals(str32))
             return false;
        
        String str33 = getThemeTemplate(), str34 = onlCgformHead.getThemeTemplate();
        
        if ((str33 == null) ? (str34 != null) : !str33.equals(str34))
             return false;
        
        String str35 = getFormTemplateMobile(), str36 = onlCgformHead.getFormTemplateMobile();
        
        if ((str35 == null) ? (str36 != null) : !str35.equals(str36))
             return false;
        
        String str37 = getExtConfigJson(), str38 = onlCgformHead.getExtConfigJson();
        
        if ((str37 == null) ? (str38 != null) : !str37.equals(str38))
             return false;
        
        String str39 = getUpdateBy(), str40 = onlCgformHead.getUpdateBy();
        
        if ((str39 == null) ? (str40 != null) : !str39.equals(str40))
             return false;
        
        Date date1 = getUpdateTime(), date2 = onlCgformHead.getUpdateTime();
        
        if ((date1 == null) ? (date2 != null) : !date1.equals(date2))
             return false;
        
        String str41 = getCreateBy(), str42 = onlCgformHead.getCreateBy();
        
        if ((str41 == null) ? (str42 != null) : !str41.equals(str42))
             return false;
        
        Date date3 = getCreateTime(), date4 = onlCgformHead.getCreateTime();
        
        if ((date3 == null) ? (date4 != null) : !date3.equals(date4))
             return false;
        
        String str43 = getPhysicId(), str44 = onlCgformHead.getPhysicId();
        
        if ((str43 == null) ? (str44 != null) : !str43.equals(str44))
             return false;
        
        String str45 = getIsDesForm(), str46 = onlCgformHead.getIsDesForm();
        
        if ((str45 == null) ? (str46 != null) : !str45.equals(str46))
             return false;
        
        String str47 = getDesFormCode(), str48 = onlCgformHead.getDesFormCode();
        
        if ((str47 == null) ? (str48 != null) : !str47.equals(str48))
             return false;
        
        String str49 = getLowAppId(), str50 = onlCgformHead.getLowAppId();
        
        return !((str49 == null) ? (str50 != null) : !str49.equals(str50));
    }

    protected boolean canEqual(Object other) {
        
        return other instanceof OnlCgformHead;
    }


    public String getId() {
        
        return this.id;
    }

    public String getTableName() {
        return this.tableName;
    }

    public Integer getTableType() {
        
        return this.tableType;
    }

    public Integer getTableVersion() {
        
        return this.tableVersion;
    }

    public String getTableTxt() {
        
        return this.tableTxt;
    }

    public String getIsCheckbox() {
        
        return this.isCheckbox;
    }

    public String getIsDbSynch() {
        
        return this.isDbSynch;
    }

    public String getIsPage() {
        
        return this.isPage;
    }

    public String getIsTree() {
        
        return this.isTree;
    }

    public String getIdSequence() {
        
        return this.idSequence;
    }

    public String getIdType() {
        
        return this.idType;
    }

    public String getQueryMode() {
        
        return this.queryMode;
    }

    public Integer getRelationType() {
        
        return this.relationType;
    }

    public String getSubTableStr() {
        
        return this.subTableStr;
    }

    public Integer getTabOrderNum() {
        
        return this.tabOrderNum;
    }

    public String getTreeParentIdField() {
        
        return this.treeParentIdField;
    }

    public String getTreeIdField() {
        
        return this.treeIdField;
    }

    public String getTreeFieldname() {
        
        return this.treeFieldname;
    }

    public String getFormCategory() {
        
        return this.formCategory;
    }

    public String getFormTemplate() {
        
        return this.formTemplate;
    }

    public String getThemeTemplate() {
        
        return this.themeTemplate;
    }

    public String getFormTemplateMobile() {
        
        return this.formTemplateMobile;
    }

    public String getExtConfigJson() {
        
        return this.extConfigJson;
    }

    public String getUpdateBy() {
        
        return this.updateBy;
    }

    public Date getUpdateTime() {
        
        return this.updateTime;
    }

    public String getCreateBy() {
        
        return this.createBy;
    }

    public Date getCreateTime() {
        
        return this.createTime;
    }

    public Integer getCopyType() {
        
        return this.copyType;
    }

    public Integer getCopyVersion() {
        
        return this.copyVersion;
    }

    public String getPhysicId() {
        
        return this.physicId;
    }

    public Integer getHascopy() {
        
        return this.hascopy;
    }

    public Integer getScroll() {
        
        return this.scroll;
    }

    public String getTaskId() {
        
        return this.taskId;
    }

    public String getIsDesForm() {
        
        return this.isDesForm;
    }

    public String getDesFormCode() {
        
        return this.desFormCode;
    }

    public String getLowAppId() {
        
        return this.lowAppId;
    }

    public String getSelectFieldString() {
        
        return this.selectFieldString;
    }

    public List<String> getSelectFieldList() {
        
        if (this.selectFieldString == null || "".equals(this.selectFieldString))
             return null;
        
        return Arrays.asList(this.selectFieldString.split(","));
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\entity\OnlCgformHead.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
