package com.qacart.todo.data;

public class ValidationData {

    public static final String INVALID_EMAIL_FORMAT = "Test";
    public static final String INVALID_PASSWORD_LENGTH = "1";
    public static final String EMPTY = "";
    public static final String MALE_WARE_ATTACK = "some<script>maliciousCode</script>name";
    public static final String XSS_ATTACK = "<script>alert('XSS')</script>";
    public static final String SQL_INJECTION_INPUT_DROP_TABLE = "Robert'); DROP TABLE Users;--";
    public static final String SQL_INJECTION_INPUT = "' OR '1'='1";
    public static final String LONG_STRING_FORMAT = "Test_TEST_TEST_TEST_TEST_TEST_TEST_TEST_TEST_TEST_TEST_";
    public static final String SHORT_STRING_FORMAT = "A";
    public static final String SPECIAL_CHAR = "!#@#@$$!%@^&@*@*(@";
    public static final String SQL_INJECTION_SPECIAL_CHARS = "='+;--??{}";
    public static final String SPACE_Value = "   ";
}
