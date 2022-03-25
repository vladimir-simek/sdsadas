package com.vladimirsimek.csgo;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<Diamond> diamonds = new ArrayList<>() {};
	    Operations.readCSV("diamonds.csv" , diamonds);
        Operations.getStatistics(diamonds);
    }
}
