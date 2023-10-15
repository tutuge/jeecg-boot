package org.jeecg.modules.online.cgform.converter.a1;

import org.jeecg.common.system.vo.DictModel;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.online.cgform.converter.FieldCommentConverter;

import java.util.List;
import java.util.Map;

public class bConfig implements FieldCommentConverter {
    protected String a;

    protected List<DictModel> b;

    public String getFiled() {
        return this.a;
    }

    public void setFiled(String filed) {
        this.a = filed;
    }

    public List<DictModel> getDictList() {
        return this.b;
    }

    public void setDictList(List<DictModel> dictList) {
        this.b = dictList;
    }

    public String converterToVal(String txt) {
        if (oConvertUtils.isNotEmpty(txt))
            for (DictModel dictModel : this.b) {
                if (dictModel.getText().equals(txt))
                    return dictModel.getValue();
            }
        return null;
    }

    public String converterToTxt(String val) {
        if (oConvertUtils.isNotEmpty(val))
            for (DictModel dictModel : this.b) {
                if (dictModel.getValue().equals(val))
                    return dictModel.getText();
            }
        return null;
    }

    public Map<String, String> getConfig() {
        return null;
    }
}
