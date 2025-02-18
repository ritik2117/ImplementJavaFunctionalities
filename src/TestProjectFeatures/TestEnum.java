package TestProjectFeatures;

public enum TestEnum {
    TEST_ONE("Test one"),
    TEST_TWO("Test two");

    private final String value;

    TestEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static TestEnum fromValue(String value) {
        for (TestEnum testEnum : TestEnum.values()) {
            if (testEnum.value.equals(value)) {
                return testEnum;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
}
