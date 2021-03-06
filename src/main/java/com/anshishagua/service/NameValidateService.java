package com.anshishagua.service;

import com.anshishagua.utils.CharacterUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * User: lixiao
 * Date: 2018/5/19
 * Time: 下午5:56
 */

@Service
public class NameValidateService {
    public boolean isValidSystemParamName(String name) {
        return isValidIdentifier(name);
    }

    private boolean isValidIdentifier(String id) {
        Objects.requireNonNull(id);

        for (int i = 0; i < id.length(); ++i) {
            char ch = id.charAt(i);

            if (i == 0 && !Character.isAlphabetic(ch)) {
                return false;
            }

            if (!Character.isAlphabetic(ch) && !Character.isDigit(ch) && (ch != '_')) {
                return false;
            }
        }

        return true;
    }

    public boolean isValidTableName(String tableName) {
        return isValidIdentifier(tableName);
    }

    private boolean isValidName(String name) {
        Objects.requireNonNull(name);

        for (int i = 0; i < name.length(); ++i) {
            char ch = name.charAt(i);

            if (i == 0 && Character.isDigit(ch)) {
                return false;
            }

            if (!CharacterUtils.isChinese(ch) && !Character.isDigit(ch)
                    && !Character.isAlphabetic(ch) && (ch != '_')) {
                return false;
            }
        }

        return true;
    }

    public boolean isValidTagName(String tagName) {
        return isValidName(tagName);
    }

    public boolean isValidIndexName(String indexName) {
        return isValidName(indexName);
    }
}