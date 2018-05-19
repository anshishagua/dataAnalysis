package com.anshishagua.utils;

import com.anshishagua.object.Result;
import com.anshishagua.parser.BasicType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * User: lixiao
 * Date: 2018/5/19
 * Time: 下午10:49
 */

public class TypeUtils {
    private static final Pattern INT_PATTERN = Pattern.compile("(\\-)?[0-9]+");

    public static Result checkValue(String value, BasicType type) {
        Objects.requireNonNull(value);
        Objects.requireNonNull(type);

        switch (type) {
            case Boolean:
                if (!"true".equals(value) && !"false".equals(value)) {
                    return Result.error(String.format("%s不符合布尔值:[true,false]", value));
                }
                return Result.ok();
            case Integer:
                try {
                    Integer.parseInt(value);

                    return Result.ok();
                } catch (NumberFormatException ex) {
                    return Result.error(String.format("%s不符合整数格式", value));
                }
            case Long:
                try {
                    Long.parseLong(value);

                    return Result.ok();
                } catch (NumberFormatException ex) {
                    return Result.error(String.format("%s不符合长整型格式", value));
                }
            case Float:
                try {
                    Float.parseFloat(value);

                    return Result.ok();
                } catch (NumberFormatException ex) {
                    return Result.error(String.format("%s不符合单精度浮点数格式", value));
                }
            case Double:
                try {
                    Double.parseDouble(value);

                    return Result.ok();
                } catch (NumberFormatException ex) {
                    return Result.error(String.format("%s不符合双精度浮点数格式", value));
                }
            case Date:
                try {
                    LocalDate.parse(value, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                    return Result.ok();
                } catch (DateTimeParseException ex) {
                    return Result.error(String.format("%s不符合日期型格式", value));
                }
            case String:
                return Result.ok();
            default:
                return Result.error(String.format("不识别的类型:%s", type));
        }
    }
}