package CSVreader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

public class CSVReader2 {

    public static void CSVToJson() throws Exception {
        File input = new File("C:\\Users\\Michael\\careerdevs\\Advanced\\caboodle\\src\\main\\amazon_co-ecommerce_sample.csv");

        try {
            CsvSchema csv = CsvSchema.emptySchema().withHeader();
            CsvMapper csvMapper = new CsvMapper();
            MappingIterator<Map<?, ?>> mappingIterator = csvMapper.reader().forType(Map.class).with(csv).readValues(input);
            List<Map<?, ?>> list = mappingIterator.readAll();

            List<?> dataSet = new ArrayList<>();

            for (int i=0; i<list.get(1).size(); i++) {
//                System.out.println(list.get(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}