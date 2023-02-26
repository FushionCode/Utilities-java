package com.fushioncoder.Utility_Service.utilities;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.text.CaseUtils;

public class StringUtils {

    public static String buildUrlPaths(Object... pathVariables) {
        String res = "";
        for (int i = 0; i < pathVariables.length; i++) {
            if (i != 0) {
                res += "/";
            }
            res += pathVariables[i];
        }
        return res;
    }

    public static String buildName(Object... values) {
        StringBuilder nameBuilder = new StringBuilder();
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null) {
                if (i != 0) {
                    nameBuilder.append(" ");
                }
                nameBuilder.append(values[i]);
            }
        }
        return nameBuilder.toString();
    }

    public static String generateRandomChars(int length) {
        return  RandomStringUtils.randomNumeric(length);
    }

    public static String parseStringDate(String dateString) {
        if (dateString.contains("-")) {
            return dateString.replaceAll("-", "");
        }
        return dateString;
    }


    public static String convertStringWithDelimeterToCamelCase(
            String strToBeConverted,
            String delimiter
    ) {
        return CaseUtils.toCamelCase(
                strToBeConverted,
                false,
                delimiter.toCharArray()
        );
    }

    public static String concat(String separator, String... data) {
        String res = "";
        for (String string : data) {
            if (!data[0].equals(string)) {
                res += separator;
            }
            res += string;
        }
        return res;
    }
}
