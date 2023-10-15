package org.jeecg.modules.online.config.c;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.*;
import org.jeecg.common.util.security.AbstractQueryBlackListHandler;
import org.springframework.stereotype.Component;

import java.io.StringReader;
import java.util.*;

@Component("onlReportQueryBlackListHandler")
public class OnlReportQueryBlackListHandler extends AbstractQueryBlackListHandler {
    private static ThreadLocal<Map<String, AbstractQueryBlackListHandler.QueryTable>> threadLocal = new ThreadLocal<>();

    private static ThreadLocal<String> b = new ThreadLocal<>();

    private void b() {
        threadLocal.set(new HashMap<>(5));
        b.set("");
    }

    private void c() {
        threadLocal.remove();
        b.remove();
    }

    private void a(String paramString, AbstractQueryBlackListHandler.QueryTable paramQueryTable) {
        threadLocal.get().put(paramString, paramQueryTable);
    }

    private AbstractQueryBlackListHandler.QueryTable a(String paramString) {
        return (AbstractQueryBlackListHandler.QueryTable) ((Map) threadLocal.get()).get(paramString);
    }

    private void a(String paramString1, String paramString2) {
        AbstractQueryBlackListHandler.QueryTable queryTable = (AbstractQueryBlackListHandler.QueryTable) ((Map) threadLocal.get()).get(paramString1);
        queryTable.addField(paramString2);
    }

    private List<AbstractQueryBlackListHandler.QueryTable> getResult() {
        Map map = threadLocal.get();
        ArrayList<AbstractQueryBlackListHandler.QueryTable> arrayList = new ArrayList(map.values());
        c();
        return arrayList;
    }

    protected List<AbstractQueryBlackListHandler.QueryTable> getQueryTableInfo(String sql) {
        b();
        CCJSqlParserManager cCJSqlParserManager = new CCJSqlParserManager();
        try {
            Statement statement = cCJSqlParserManager.parse(new StringReader(sql));
            if (statement instanceof Select) {
                Select select = (Select) statement;
                SelectBody selectBody = select.getSelectBody();
                if (selectBody instanceof PlainSelect) {
                    PlainSelect plainSelect = (PlainSelect) selectBody;
                    a(plainSelect);
                    b(plainSelect);
                }
                if (selectBody instanceof SetOperationList) {
                    SetOperationList setOperationList = (SetOperationList) selectBody;
                    List<SelectBody> list = setOperationList.getSelects();
                    for (byte b = 0; b < list.size(); b++) {
                        SelectBody selectBody1 = list.get(b);
                        if (selectBody1 instanceof PlainSelect) {
                            PlainSelect plainSelect = (PlainSelect) selectBody1;
                            a(plainSelect);
                            b(plainSelect);
                        }
                    }
                }
            }
            return getResult();
        } catch (JSQLParserException jSQLParserException) {
            jSQLParserException.printStackTrace();
            return null;
        }
    }

    private void a(PlainSelect paramPlainSelect) {
        Table table = (Table) paramPlainSelect.getFromItem();
        a(table);
        List<Join> list = paramPlainSelect.getJoins();
        if (list != null)
            for (Join join : list) {
                Table table1 = (Table) join.getRightItem();
                a(table1);
            }
    }

    private void a(Table paramTable) {
        String str = "";
        if (paramTable.getAlias() != null) {
            str = paramTable.getAlias().getName();
        } else {
            str = paramTable.getName();
        }
        if (b.get().length() == 0)
            b.set(str);
        a(str, new AbstractQueryBlackListHandler.QueryTable(paramTable.getName(), str));
    }

    private void b(PlainSelect var1) {
        List var2 = var1.getSelectItems();
        final String var3 = b.get();
        Iterator var4 = var2.iterator();

        while (var4.hasNext()) {
            SelectItem var5 = (SelectItem) var4.next();
            var5.accept(new SelectItemVisitorAdapter() {
                public void visit(SelectExpressionItem item) {
                    Expression var2 = item.getExpression();
                    if (var2 instanceof Column) {
                        Column var3x = (Column) var2;
                        String var4;
                        if (var3x.getTable() == null) {
                            var4 = var3x.getColumnName();
                            OnlReportQueryBlackListHandler.this.a(var3, var4);
                        } else {
                            var4 = var3x.getTable().getName();
                            AbstractQueryBlackListHandler.QueryTable var5 = null;
                            if (var4 != null && !"".equals(var4)) {
                                var5 = OnlReportQueryBlackListHandler.this.a(var4);
                            } else {
                                var5 = OnlReportQueryBlackListHandler.this.a(var3);
                            }

                            if (var5 != null) {
                                var5.addField(var3x.getColumnName());
                            }
                        }
                    } else if (!OnlReportQueryBlackListHandler.this.b(var2)) {
                        String var9 = var2.toString();
                        boolean var10 = false;
                        Set var11 = ((Map) threadLocal.get()).keySet();
                        Iterator var6 = var11.iterator();

                        while (var6.hasNext()) {
                            String var7 = (String) var6.next();
                            String var8 = var7 + ".";
                            if (var9.indexOf(var8) >= 0) {
                                var10 = true;
                                OnlReportQueryBlackListHandler.this.a(var7, var9);
                            }
                        }

                        if (!var10) {
                            OnlReportQueryBlackListHandler.this.a(var3, var9);
                        }
                    }

                }

                public void visit(AllTableColumns columns) {
                    String var2 = null;

                    try {
                        var2 = columns.getTable().getName();
                    } catch (Exception var4) {
                    }

                    if (var2 == null) {
                        var2 = var3;
                    }

                    OnlReportQueryBlackListHandler.this.a(var2).setAll(true);
                }

                public void visit(AllColumns columns) {
                    if ("*".equals(columns.toString())) {
                        OnlReportQueryBlackListHandler.this.a(var3).setAll(true);
                    }

                }
            });
        }

    }

    private boolean a(Expression paramExpression) {
        if (paramExpression != null)
            return (paramExpression instanceof net.sf.jsqlparser.expression.Function || paramExpression instanceof net.sf.jsqlparser.expression.BinaryExpression || paramExpression instanceof net.sf.jsqlparser.expression.CastExpression || paramExpression instanceof net.sf.jsqlparser.expression.CaseExpression);
        return false;
    }

    private boolean b(Expression paramExpression) {
        return (paramExpression instanceof net.sf.jsqlparser.expression.StringValue || paramExpression instanceof net.sf.jsqlparser.expression.DoubleValue || paramExpression instanceof net.sf.jsqlparser.expression.LongValue);
    }
}
