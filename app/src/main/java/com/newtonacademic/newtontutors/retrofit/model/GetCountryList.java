package com.newtonacademic.newtontutors.retrofit.model;

import java.util.List;

/**
 * @author laiyongyang
 * @date 2020-07-08
 * @desc
 * @email fzhlaiyy@intretech.com
 */
public class GetCountryList {

    private int status;
    private String descript;
    private List<Data> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public List<Data> getData() {
        return data;
    }

    public static class Data {
        private String chineseName;
        private int countryCode;
        private String englishName;
        private String photoCode;
        private String sortLetters;

        public String getSortLetters() {
            return sortLetters;
        }

        public void setSortLetters(String sortLetters) {
            this.sortLetters = sortLetters;
        }

        public String getChineseName() {
            return chineseName;
        }

        public int getCountryCode() {
            return countryCode;
        }

        public String getEnglishName() {
            return englishName;
        }

        public String getPhotoCode() {
            return photoCode;
        }
    }
}
