package com.cg.util;

import com.cg.result.SystemException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.UUID;

public class AssistUtil {
    private AssistUtil(){}

    public static void assertionWithSystemException(Boolean flag, String msg) {
        if(flag)
            throw new SystemException(msg);
    }
    public static void assertionWithRuntimeException(Boolean flag, String msg) {
        if(flag)
            throw new RuntimeException(msg);
    }

    public static String getNickname() {
        StringBuilder sb = new StringBuilder("user_");
        Random r = new Random();
        for (int i = 0; i < 10; i++)
            sb.append(r.nextInt(10));
        return sb.toString();
    }

    public static String generatorFilePath(String filename) {
        LocalDate localDate = LocalDate.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String dataPath = localDate.format(dateTimeFormatter);
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        int index = filename.lastIndexOf(".");
        String fileType = filename.substring(index);
        return new StringBuilder().append(dataPath).append("/").append(uuid).append(fileType).toString();
    }
}