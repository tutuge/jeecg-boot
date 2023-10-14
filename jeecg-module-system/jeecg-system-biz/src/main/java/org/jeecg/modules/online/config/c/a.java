 package org.jeecg.modules.online.config.c;
 
 import java.io.StringReader;
 import java.util.ArrayList;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import java.util.Set;
 import net.sf.jsqlparser.JSQLParserException;
 import net.sf.jsqlparser.expression.Expression;
 import net.sf.jsqlparser.parser.CCJSqlParserManager;
 import net.sf.jsqlparser.schema.Column;
 import net.sf.jsqlparser.schema.Table;
 import net.sf.jsqlparser.statement.Statement;
 import net.sf.jsqlparser.statement.select.AllColumns;
 import net.sf.jsqlparser.statement.select.AllTableColumns;
 import net.sf.jsqlparser.statement.select.Join;
 import net.sf.jsqlparser.statement.select.PlainSelect;
 import net.sf.jsqlparser.statement.select.Select;
 import net.sf.jsqlparser.statement.select.SelectBody;
 import net.sf.jsqlparser.statement.select.SelectExpressionItem;
 import net.sf.jsqlparser.statement.select.SelectItem;
 import net.sf.jsqlparser.statement.select.SelectItemVisitor;
 import net.sf.jsqlparser.statement.select.SelectItemVisitorAdapter;
 import net.sf.jsqlparser.statement.select.SetOperationList;
 import org.jeecg.common.util.security.AbstractQueryBlackListHandler;
 import org.springframework.stereotype.Component;
 
 @Component("onlReportQueryBlackListHandler")
 public class a extends AbstractQueryBlackListHandler {
   private static ThreadLocal<Map<String, AbstractQueryBlackListHandler.QueryTable>> a = new ThreadLocal<>();
   
   private static ThreadLocal<String> b = new ThreadLocal<>();
   
   private void b() {
     a.set(new HashMap<>(5));
     b.set(new String());
   }
   
   private void c() {
     a.remove();
     b.remove();
   }
   
   private void a(String paramString, AbstractQueryBlackListHandler.QueryTable paramQueryTable) {
     ((Map<String, AbstractQueryBlackListHandler.QueryTable>)a.get()).put(paramString, paramQueryTable);
   }
   
   private AbstractQueryBlackListHandler.QueryTable a(String paramString) {
     return (AbstractQueryBlackListHandler.QueryTable)((Map)a.get()).get(paramString);
   }
   
   private void a(String paramString1, String paramString2) {
     AbstractQueryBlackListHandler.QueryTable queryTable = (AbstractQueryBlackListHandler.QueryTable)((Map)a.get()).get(paramString1);
     queryTable.addField(paramString2);
   }
   
   private List<AbstractQueryBlackListHandler.QueryTable> getResult() {
     Map map = a.get();
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
         Select select = (Select)statement;
         SelectBody selectBody = select.getSelectBody();
         if (selectBody instanceof PlainSelect) {
           PlainSelect plainSelect = (PlainSelect)selectBody;
           a(plainSelect);
           b(plainSelect);
         } 
         if (selectBody instanceof SetOperationList) {
           SetOperationList setOperationList = (SetOperationList)selectBody;
           List<SelectBody> list = setOperationList.getSelects();
           for (byte b = 0; b < list.size(); b++) {
             SelectBody selectBody1 = list.get(b);
             if (selectBody1 instanceof PlainSelect) {
               PlainSelect plainSelect = (PlainSelect)selectBody1;
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
     Table table = (Table)paramPlainSelect.getFromItem();
     a(table);
     List list = paramPlainSelect.getJoins();
     if (list != null)
       for (Join join : list) {
         Table table1 = (Table)join.getRightItem();
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
     if (((String)b.get()).length() == 0)
       b.set(str); 
     a(str, new AbstractQueryBlackListHandler.QueryTable(this, paramTable.getName(), str));
   }
   
   private void b(PlainSelect paramPlainSelect) {
     List list = paramPlainSelect.getSelectItems();
     String str = b.get();
     for (SelectItem selectItem : list) {
       selectItem.accept((SelectItemVisitor)new SelectItemVisitorAdapter(this, str) {
             public void visit(SelectExpressionItem item) {
               Expression expression = item.getExpression();
               if (expression instanceof Column) {
                 Column column = (Column)expression;
                 if (column.getTable() == null) {
                   String str = column.getColumnName();
                   a.a(this.b, this.a, str);
                 } else {
                   String str = column.getTable().getName();
                   AbstractQueryBlackListHandler.QueryTable queryTable = null;
                   if (str == null || "".equals(str)) {
                     queryTable = a.a(this.b, this.a);
                   } else {
                     queryTable = a.a(this.b, str);
                   } 
                   if (queryTable != null)
                     queryTable.addField(column.getColumnName()); 
                 } 
               } else if (!a.a(this.b, expression)) {
                 String str = expression.toString();
                 boolean bool = false;
                 Set set = ((Map)a.a().get()).keySet();
                 for (String str1 : set) {
                   String str2 = str1 + ".";
                   if (str.indexOf(str2) >= 0) {
                     bool = true;
                     a.a(this.b, str1, str);
                   } 
                 } 
                 if (!bool)
                   a.a(this.b, this.a, str); 
               } 
             }
             
             public void visit(AllTableColumns columns) {
               String str = null;
               try {
                 str = columns.getTable().getName();
               } catch (Exception exception) {}
               if (str == null)
                 str = this.a; 
               a.a(this.b, str).setAll(true);
             }
             
             public void visit(AllColumns columns) {
               if ("*".equals(columns.toString()))
                 a.a(this.b, this.a).setAll(true); 
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


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\config\c\a.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
