package com.system.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.system.bean.User;
import com.system.database.BaseData;

import java.io.*;
import java.util.List;

public class FileUtil {

    public static <T> List<T> readJsonFile(String filePath, Class<T> clazz) {
        String jsonContent = "";
        try {
            File file = new File(BaseData.class.getResource(filePath).getFile());
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            fileReader.close();
            jsonContent = stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return JSON.parseArray(new String(jsonContent), clazz);
    }

    public static <T> void writeJsonFile(String filePath, List<T> dataList) {
        String jsonString = JSON.toJSONString(dataList, SerializerFeature.PrettyFormat);
        try (FileWriter fileWriter = new FileWriter(BaseData.class.getResource(filePath).getFile(), false)) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

