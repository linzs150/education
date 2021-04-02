package com.newtonacademic.mylibrary;

public interface ConstantGlobal {
    String LOCALE_LANGUAGE = "locale_language";
    String LOCALE_COUNTRY = "locale_country";
    public enum LanguageType{
        CHINESE,
        EN,
        HK
    }

    public static class Net {

        //        public static final String URL = "http://121.idaguo.com/api";//测试环境
        public static final String URL = "https://www.newtontutor.hk/api";//生产环境
    }
}
