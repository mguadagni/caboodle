package CSVreader;

import java.io.*;
import java.util.Scanner;

public class CSVReader {

    public static void dataReader() throws Exception {

        String path = "C:\\Users\\Michael\\careerdevs\\Advanced\\caboodle\\src\\main\\amazon_co-ecommerce_sample.csv";
        Scanner sc = new Scanner(new File(path));
        sc.useDelimiter(",");
        while (sc.hasNext()) {
            System.out.println(sc.next());
        }
        sc.close();
    }
}