/**
 * Copyright 2013-2015 JEECG (jeecgos@163.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jeecg.poi.excel.entity.result;

import lombok.Data;
import lombok.Getter;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * 导入返回类
 *
 * @author JEECG
 * @date 2014年6月29日 下午5:12:10
 */
@Data
public class ExcelImportResult<T> {

    /**
     * 结果集
     */
    @Getter
    private List<T> list;
    /**
     * 分sheet的结果集
     */
    private List<List<T>> listArray;

    /**
     * 分sheet的结果集
     */
    private List<String> sheetNames;

    /**
     * 是否存在校验失败
     */
    private boolean verfiyFail;

    /**
     * 数据源
     */
    @Getter
    private Workbook workbook;

    public ExcelImportResult() {

    }

    public ExcelImportResult(List<T> list, boolean verfiyFail, Workbook workbook) {
        this.list = list;
        this.verfiyFail = verfiyFail;
        this.workbook = workbook;
    }

    public ExcelImportResult(List<T> list, List<List<T>> listArray, List<String> sheetNames, boolean verfiyFail, Workbook workbook) {
        this.list = list;
        this.listArray = listArray;
        this.sheetNames = sheetNames;
        this.verfiyFail = verfiyFail;
        this.workbook = workbook;
    }

    public boolean isVerfiyFail() {
        return verfiyFail;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void setVerfiyFail(boolean verfiyFail) {
        this.verfiyFail = verfiyFail;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

}
