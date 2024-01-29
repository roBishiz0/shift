package com.app;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class Filter {

    private final List<String> files;
    private final String p;
    private final boolean a;
    private final String o;
    private final boolean s;
    private final boolean f;

    public Filter(List<String> files, String p, boolean a, String o, boolean s, boolean f) {
        this.files = files;
        this.p = p;
        this.a = a;
        this.o = o;
        this.s = s;
        this.f = f;
    }


    public void filter() throws IOException {

        BufferedReader br = null;

        try (BufferedWriter outInteger = new BufferedWriter(new FileWriter(o + p + "integers.txt", a));
             BufferedWriter outFloat = new BufferedWriter(new FileWriter(o + p + "floats.txt", a));
             BufferedWriter outString = new BufferedWriter(new FileWriter(o + p + "strings.txt", a));) {

            String strCurrentLine;

            List<InputStream> inputStreams = new LinkedList<>();

            String newLine = "\n";
            for (String f : files) {
                inputStreams.add(new FileInputStream(f));
                inputStreams.add(new ByteArrayInputStream(newLine.getBytes()));
            }

            SequenceInputStream sis = new SequenceInputStream(Collections.enumeration(inputStreams));
            br = new BufferedReader(new InputStreamReader(sis, StandardCharsets.UTF_8));

            List<BigInteger> bigIntegersList = new LinkedList<>();
            List<BigDecimal> bigDecimalList = new LinkedList<>();
            List<String> stringList = new LinkedList<>();

            int longCounter = 0;
            int floatCounter = 0;
            int stringCounter = 0;

            while ((strCurrentLine = br.readLine()) != null) {
                try {
                    BigInteger ch1 = new BigInteger(strCurrentLine);
                    //System.out.println("integer: " + ch1);
                    if (f) {
                        bigIntegersList.add(ch1);
                    } else {
                        longCounter++;
                    }
                    outInteger.write(strCurrentLine);
                    outInteger.write("\n");
                    continue;
                } catch (NumberFormatException e) {

                }

                try {
                    BigDecimal ch2 = new BigDecimal(strCurrentLine);
                    if (f) {
                        bigDecimalList.add(ch2);
                    } else {
                        floatCounter++;
                    }
                    //System.out.println("float: " + ch2);
                    outFloat.write(strCurrentLine);
                    outFloat.write("\n");
                    continue;
                } catch (NumberFormatException e) {

                }

                try {
                    //System.out.println("string: " + strCurrentLine);
                    if (f) {
                        stringList.add(strCurrentLine);
                    } else {
                        stringCounter++;
                    }
                    outString.write(strCurrentLine);
                    outString.write("\n");
                } catch (NumberFormatException e) {

                }
            }
            if (f) {
                StatisticsCollector sc = new StatisticsCollector(bigIntegersList, bigDecimalList, stringList);
                sc.collectStatistics();
            }
            if (s) {
                if (stringCounter > 0)
                    System.out.println("Strings count: " + stringCounter);
                if (longCounter > 0)
                    System.out.println("Integers count: " + longCounter);
                if (floatCounter > 0)
                    System.out.println("Floats count: " + floatCounter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
