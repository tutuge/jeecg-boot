package org.jeecg.modules.cable.tools;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SavePath {
    //文件储存路径 通用类 project 数据库 modelName 模块名 tableName 表名
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String path(String project, String modelName, String tableName, String base_path) {
        Date date = new Date();
        SimpleDateFormat format_year = new SimpleDateFormat("yyyy");
        SimpleDateFormat format_month = new SimpleDateFormat("MM");
        SimpleDateFormat format_date = new SimpleDateFormat("dd");
        SimpleDateFormat format_hour = new SimpleDateFormat("HH");
        String year = format_year.format(date);
        String month = format_month.format(date);
        String day = format_date.format(date);
        String hour = format_hour.format(date);
        String project_path = base_path + project;
        File projectPath = new File(project_path);
        String model_path = base_path + project + "/" + modelName;
        File modelPath = new File(model_path);
        String table_path = base_path + project + "/" + modelName + "/" + tableName;
        File tablePath = new File(table_path);

        String year_path = base_path + project + "/" + modelName + "/" + tableName + "/" + year;
        File yearPath = new File(year_path);
        String month_path = base_path + project + "/" + modelName + "/" + tableName + "/" +
                year + "/" + month;
        File monthPath = new File(month_path);
        String day_path = base_path + project + "/" + modelName + "/" + tableName + "/" +
                year + "/" + month + "/" + day;
        File dayPath = new File(day_path);
        String hour_path = base_path + project + "/" + modelName + "/" + tableName + "/" +
                year + "/" + month + "/" + day + "/" + hour;
        File hourPath = new File(hour_path);
        String realPath = project + "/" + modelName + "/" + tableName + "/" +
                year + "/" + month + "/" + day + "/" + hour;
        if (!projectPath.exists()) {
            projectPath.mkdir();
            modelPath.mkdir();
            tablePath.mkdir();
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            hourPath.mkdir();
        } else if (!modelPath.exists()) {
            modelPath.mkdir();
            tablePath.mkdir();
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            hourPath.mkdir();
        } else if (!tablePath.exists()) {
            tablePath.mkdir();
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            hourPath.mkdir();
        } else if (!yearPath.exists()) {
            yearPath.mkdir();
            monthPath.mkdir();
            dayPath.mkdir();
            hourPath.mkdir();
        } else if (!monthPath.exists()) {
            monthPath.mkdir();
            dayPath.mkdir();
            hourPath.mkdir();
        } else if (!dayPath.exists()) {
            dayPath.mkdir();
            hourPath.mkdir();
        } else if (!hourPath.exists()) {
            hourPath.mkdir();
        }
        return realPath;
    }
}
