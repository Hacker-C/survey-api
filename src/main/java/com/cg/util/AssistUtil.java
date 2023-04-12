package com.cg.util;

import com.cg.result.SystemException;

import java.util.Random;

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
}
