package org.jeecg.modules.online.cgform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("onl_cgform_enhance_js")
public class OnlCgformEnhanceJs implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String cgformHeadId;

    private String cgJsType;

    private String cgJs;

    private String content;

    public void setId(String id) {
        this.id = id;
    }

    public void setCgformHeadId(String cgformHeadId) {
        this.cgformHeadId = cgformHeadId;
    }

    public void setCgJsType(String cgJsType) {
        this.cgJsType = cgJsType;
    }

    public void setCgJs(String cgJs) {
        this.cgJs = cgJs;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getId() {
        return this.id;
    }

    public String getCgformHeadId() {
        return this.cgformHeadId;
    }

    public String getCgJsType() {
        return this.cgJsType;
    }

    public String getCgJs() {
        return this.cgJs;
    }

    public String getContent() {
        return this.content;
    }
}


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\entity\OnlCgformEnhanceJs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
