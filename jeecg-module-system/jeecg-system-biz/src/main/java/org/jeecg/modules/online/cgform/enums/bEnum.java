package org.jeecg.modules.online.cgform.enums;

public enum bEnum {
    a("only", "only", ""),
    b("n6-16", "^\\d{6,16}$", "请输入6-16位的数字"),
    c("*6-16", "^.{6,16}$", "请输入6-16位任意字符"),
    d("s6-18", "^[a-z|A-Z]{6,18}$", "请输入6-18位字母"),
    e("url", "^((ht|f)tps?):\\/\\/[\\w\\-]+(\\.[\\w\\-]+)+([\\w\\-.,@?^=%&:\\/~+#]*[\\w\\-@?^=%&\\/~+#])?$", "请输入正规的网址"),
    f("m", "^1[3456789]\\d{9}$", "请输入正规的手机号码"),
    g("p", "^[0-9]{6}$", "请输入正规的邮政编码"),
    h("s", "^[A-Z|a-z]+$", "请输入字母"),
    i("n", "^-?\\d+(\\.?\\d+|\\d?)$", "请输入数字"),
    j("z", "z", "请输入整数"),
    k("*", "^.+$", "该字段不能为空"),
    l("e", "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", "请输入正确格式的邮箱地址"),
    m("money", "^(([1-9][0-9]*)|([0]\\.\\d{0,2}|[1-9][0-9]*\\.\\d{0,5}))$", "请输入正确的金额");

    public static bEnum[] a() {
        return bEnum.values();
    }

    public static bEnum a(String paramString) {
        return Enum.valueOf(bEnum.class, paramString);
    }

    String n;

    String o;

    String p;

    bEnum(String paramString1, String paramString2, String paramString3) {
        this.o = paramString2;
        this.p = paramString3;
        this.n = paramString1;
    }

    public String getType() {
        return this.n;
    }

    public void setType(String type) {
        this.n = type;
    }

    public String getPattern() {
        return this.o;
    }

    public void setPattern(String pattern) {
        this.o = pattern;
    }

    public String getMsg() {
        return this.p;
    }

    public void setMsg(String msg) {
        this.p = msg;
    }

    public static bEnum b(String paramString) {
        for (bEnum b1 : a()) {
            if (b1.n.equals(paramString))
                return b1;
        }
        return null;
    }
}
