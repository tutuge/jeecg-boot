package org.jeecg.modules.online.cgform.enums;

public enum cEnum {
    a("MYSQL", "1"),
    b("ORACLE", "2"),
    c("SQLSERVER", "3"),
    d("POSTGRESQL", "4");

    public static cEnum[] a() {
        return cEnum.values();
    }

    public static cEnum a(String paramString) {
        return Enum.valueOf(cEnum.class, paramString);
    }

    private String e;

    private String f;

    cEnum(String paramString1, String paramString2) {
        this.e = paramString1;
        this.f = paramString2;
    }

    public static String b(String paramString) {
        for (cEnum c1 : a()) {
            if (c1.f.equals(paramString))
                return c1.e;
        }
        return a.e;
    }

    public String getName() {
        return this.e;
    }

    public void setName(String name) {
        this.e = name;
    }

    public String getValue() {
        return this.f;
    }

    public void setValue(String value) {
        this.f = value;
    }
}
