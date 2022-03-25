package com.vladimirsimek.csgo;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Operations {
    public static void readCSV(String path, List<Diamond> diamonds){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
            bufferedReader.readLine();
            String line = bufferedReader.readLine();
            while (line != null){
                String[] rawDiamond = line.split(",");
                for (int i = 0; i < rawDiamond.length; i++) {
                    rawDiamond[i] = rawDiamond[i].replaceAll("\"", "");
                }
                diamonds.add(new Diamond(
                        Integer.parseInt(rawDiamond[0]),
                        Double.parseDouble(rawDiamond[1]),
                        rawDiamond[2],
                        rawDiamond[3],
                        rawDiamond[4],
                        Integer.parseInt(rawDiamond[7]),
                        Double.parseDouble(rawDiamond[8]),
                        Double.parseDouble(rawDiamond[9])
                ));
                line = bufferedReader.readLine();

            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Successfully added all diamonds.");
    }

    public static int amountOfFairDiamonds(@NotNull List<Diamond> diamonds){
        var amountList =diamonds.stream()
                .map(Diamond::getCut)
                .filter(i -> i.equals("Fair"))
                .collect(Collectors.toList());
        return amountList.size();
    }

    public static int roundedPriceOfPremiumDiamonds(@NotNull List<Diamond> diamonds){
        OptionalDouble optionalDouble = diamonds.stream()
                .filter(i -> i.getCut().equals("Premium"))
                .mapToInt(Diamond::getPrice)
                .average();
        double db = optionalDouble.orElse(0);
        return (int) Math.round((db / 10)) * 10;

    }

    public static void sameXYDiamonds(@NotNull List<Diamond> diamonds){
        var sameXYDiamonds = diamonds.stream()
                .filter(i -> i.getX() == i.getY())
                .collect(Collectors.toList());
        for (int i = 0; i < sameXYDiamonds.size(); i++) {
            System.out.println(sameXYDiamonds.get(i).getCut() + " " + sameXYDiamonds.get(i).getColor() + " (" + sameXYDiamonds.get(i).getPrice() + ")");
        }
    }

    public static void getStatistics(List<Diamond> diamonds){
        System.out.println(amountOfFairDiamonds(diamonds));
        System.out.println(roundedPriceOfPremiumDiamonds(diamonds));
        sameXYDiamonds(diamonds);
        ss(diamonds);
        fairL(diamonds);
    }

    public static void ss(@NotNull List<Diamond> diamonds){
        var s = diamonds.stream()
                .map(Diamond::getCut)
                .collect(Collectors.toSet());

        System.out.println(s);
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public static void fairL(@NotNull List<Diamond> diamonds){
        var uzToMrdam = diamonds.stream()
                .filter(i -> i.getCut().equals("Fair"))
                .sorted((o1, o2) -> (o2.getPrice() - o1.getPrice()))
                .limit(1)
                .map(Diamond::getPrice)
                .findFirst()
                .get();

        var kokotina = diamonds.stream()
                .filter(i -> i.getCut().equals("Fair"))
                .sorted((o1, o2) -> (o2.getPrice() - o1.getPrice()))
                .map(i -> Math.round( (float) i.getPrice()  / uzToMrdam * 10000) / (float) 100 + "%")
                .collect(Collectors.toList());

        kokotina.forEach(System.out::println);
    }
}
