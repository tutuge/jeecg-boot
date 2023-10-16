package org.jeecg.modules.online.cgreport.c;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.SimpleNode;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.*;

import java.io.StringReader;
import java.util.*;

public class bUtil {
    public static Map<String, Object> a(String paramString) {
        HashMap<Object, Object> hashMap = new HashMap<>(5);
        Select select = null;
        CCJSqlParserManager cCJSqlParserManager = new CCJSqlParserManager();
        try {
            select = (Select) cCJSqlParserManager.parse(new StringReader(paramString));
        } catch (JSQLParserException jSQLParserException) {
            jSQLParserException.printStackTrace();
        }
        SelectBody selectBody = select.getSelectBody();
        ArrayList arrayList = new ArrayList<>();
        if (selectBody instanceof SetOperationList) {
            SetOperationList setOperationList = (SetOperationList) selectBody;
            List list = setOperationList.getSelects();
            for (byte b1 = 0; b1 < list.size(); b1++) {
                PlainSelect plainSelect = (PlainSelect) list.get(b1);
                List<String> list1 = a(plainSelect);
                a((Map) hashMap, list1);
            }
        }
        if (selectBody instanceof PlainSelect) {
            PlainSelect plainSelect = (PlainSelect) selectBody;
            List<String> list = a(plainSelect);
            a((Map) hashMap, list);
        }
        return (Map) hashMap;
    }

    private static void a(Map<String, Object> paramMap, List<String> paramList) {
        for (String str : paramList) {
            if (!"*".equals(str))
                paramMap.put(str, str);
        }
    }

    public static List<String> a(PlainSelect paramPlainSelect) {
        List<SelectItem> list = paramPlainSelect.getSelectItems();
        ArrayList<String> arrayList = new ArrayList<>();
        if (list != null)
            for (SelectItem selectItem : list) {
                if (selectItem instanceof SelectExpressionItem) {
                    SelectExpressionItem selectExpressionItem = (SelectExpressionItem) selectItem;
                    String str = "";
                    Alias alias = selectExpressionItem.getAlias();
                    Expression expression = selectExpressionItem.getExpression();
                    if (expression instanceof net.sf.jsqlparser.expression.CaseExpression) {
                        str = alias.getName();
                    } else if (expression instanceof net.sf.jsqlparser.expression.LongValue || expression instanceof net.sf.jsqlparser.expression.StringValue || expression instanceof net.sf.jsqlparser.expression.DateValue || expression instanceof net.sf.jsqlparser.expression.DoubleValue) {
                        str = Objects.nonNull(alias.getName()) ? alias.getName() : expression.getASTNode().jjtGetValue().toString();
                    } else if (expression instanceof net.sf.jsqlparser.expression.TimeKeyExpression) {
                        str = alias.getName();
                    } else if (alias != null) {
                        str = alias.getName();
                    } else {
                        SimpleNode simpleNode = expression.getASTNode();
                        Object object = simpleNode.jjtGetValue();
                        if (object instanceof Column) {
                            str = ((Column) object).getColumnName();
                        } else if (object instanceof net.sf.jsqlparser.expression.Function) {
                            str = object.toString();
                        } else {
                            str = String.valueOf(object);
                            str = str.replace("'", "");
                            str = str.replace("\"", "");
                            str = str.replace("`", "");
                        }
                    }
                    str = str.replace("'", "");
                    str = str.replace("\"", "");
                    str = str.replace("`", "");
                    arrayList.add(str);
                    continue;
                }
                if (selectItem instanceof AllTableColumns) {
                    AllTableColumns allTableColumns = (AllTableColumns) selectItem;
                    arrayList.add(allTableColumns.toString());
                    continue;
                }
                arrayList.add(selectItem.toString());
            }
        return arrayList;
    }
}
