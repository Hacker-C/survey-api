package com.cg.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class CopyBeanUtil {

    private CopyBeanUtil() {}
    //单个实体类拷贝
    public static <V, T> V copy(T source, Class<V> clazz) {
        V result = null;
        try {
            result = clazz.newInstance();
            BeanUtils.copyProperties(source, result);

        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return result;
    }
    //集合拷贝
    public static <V, T> List<V> copyList(List<T> source, Class<V> clazz) {
        return source.stream()
                .map(item -> copy(item, clazz))
                .collect(Collectors.toList());
    }

//    public static <T> PageDto<T> copyPage(Long total, List<T> list) {
//        PageDto<T> result = new PageDto<>();
//        result.setRows(list);
//        result.setTotal(total);
//        return result;
//    }
//    public static void main(String[] args) {
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String encode = passwordEncoder.encode("Lzx123");
//        System.out.println(encode);
//    }
}
