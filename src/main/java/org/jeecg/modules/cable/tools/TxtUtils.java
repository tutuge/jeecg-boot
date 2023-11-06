package org.jeecg.modules.cable.tools;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxtUtils {
    //根据文件路径读取txt文件
    public static Map<Integer, String> readTxtFile(String filePath) {
        Map<Integer, String> textMap = new HashMap<>(10000);
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, StandardCharsets.UTF_8);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {
            String line;
            Integer i = 0;
            while ((line = bufferedReader.readLine()) != null) {
                i = i + 1;
                textMap.put(i, line);
            }
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return textMap;
    }

    //写入文件
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void writeTxtFile(String filePath, List<String> textList) throws IOException {

        File file = new File(filePath);
        file.createNewFile();
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (String text : textList) {
                bufferedWriter.write(text);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
