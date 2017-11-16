package com.anil.weatherapp.specs;

public class StringSpecs {

    private StringSpecs() {
        // private constructor for static
    }

    public static String isExist(java.util.List<String> source, String searched) {
        if (searched.equals(""))
            return "";
        for (String s : source)
            if (s.toLowerCase().matches(searched.toLowerCase() + "(.*)"))
                return s;
        return "";
    }

}
