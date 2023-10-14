 package org.jeecg.modules.online.cgform.dConstants;

 import cn.hutool.core.io.FileUtil;
 import java.io.File;
 import java.io.FileInputStream;
 import java.io.FileOutputStream;
 import java.io.IOException;
 import java.net.URLDecoder;
 import java.util.List;
 import java.util.zip.ZipEntry;
 import java.util.zip.ZipOutputStream;

 public class d {
   public static File a(List<String> paramList, String paramString) throws RuntimeException {
     File file = FileUtil.touch(paramString);
     if (file == null)
       return null;
     if (!file.getName().endsWith(".zip"))
       return null;
     ZipOutputStream zipOutputStream = null;
     try {
       FileOutputStream fileOutputStream = new FileOutputStream(file);
       zipOutputStream = new ZipOutputStream(fileOutputStream);
       for (String str1 : paramList) {
         str1 = URLDecoder.decode(str1, "UTF-8").replace("生成成功：", "");
         File file1 = new File(str1);
         if (file1 == null || !file1.exists())
           continue;
         byte[] arrayOfByte = new byte[4096];
         String str2 = null;
         if (file1.getAbsolutePath().indexOf("src\\") != -1) {
           str2 = file1.getAbsolutePath().substring(file1.getAbsolutePath().indexOf("src\\") - 1);
         } else {
           str2 = file1.getAbsolutePath().substring(file1.getAbsolutePath().indexOf("src/") - 1);
         }
         zipOutputStream.putNextEntry(new ZipEntry(str2));
         FileInputStream fileInputStream = new FileInputStream(file1);
         int i;
         while ((i = fileInputStream.read(arrayOfByte)) != -1)
           zipOutputStream.write(arrayOfByte, 0, i);
         fileInputStream.close();
         zipOutputStream.closeEntry();
       }
       if (zipOutputStream != null)
         try {
           zipOutputStream.close();
         } catch (IOException iOException) {
           System.out.println("ZipUtil toZip close exception" + iOException);
         }
       fileOutputStream.close();
     } catch (Exception exception) {
       throw new RuntimeException("zipFile error from ZipUtils", exception);
     }
     return file;
   }
 }


/* Location:              H:\tools\repository\org\jeecgframework\boot\hibernate-re\3.5.3\hibernate-re-3.5.3.jar!\org\jeecg\modules\online\cgform\d\d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */
