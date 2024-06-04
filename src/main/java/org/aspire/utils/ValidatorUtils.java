package org.aspire.utils;

import org.apache.commons.lang3.StringUtils;
import org.aspire.exception.ValidationException;

import java.util.List;

public class ValidatorUtils {

    public static void validateNotNull(Object obj, String fieldName) {
        if (obj == null) {
            throw new ValidationException(String.format("%s cannot be null", fieldName));
        }
    }

    public static void validateNotBlank(String value, String fieldName) {
        if (StringUtils.isBlank(value)) {
            throw new ValidationException(String.format("%s cannot be blank", fieldName));
        }
    }

    public static void validateInRange(int value, int min, String fieldName) {
        if (value < min) {
            throw new ValidationException(String.format("%s cannot be less than %d. Current value: %d", fieldName, min, value));
        }
    }

    public static void validateInRange(double value, double min, String fieldName) {
        if (value < min) {
            throw new ValidationException(String.format("%s cannot be less than %f. Current value: %f", fieldName, min, value));
        }
    }

    public static void validateInRange(double value, double min, double max, String fieldName) {
        if (value < min || value > max) {
            throw new ValidationException(String.format("%s cannot be less than %f and greater than %f. Current value: %f",
                    fieldName, min, max, value));
        }
    }

    public static void validateInValues(String value, List<String> allowedValues, String fieldName) {
        validateNotBlank(value, fieldName);
        if (!allowedValues.contains(value)) {
            throw new ValidationException(String.format("%s is %s. Allowed values : [%s]", fieldName, value,
                    String.join(",", allowedValues)));
        }
    }
}
