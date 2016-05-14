import com.opencsv.CSVReader;
import java.io.*;
import java.util.*;
public class RoundMaker{
    public static void main(String [] args) {
        try {
        CSVReader reader = new CSVReader(new FileReader("Questions.csv"));
        List myEntries = reader.readAll();
        } catch (IOException e){
        }
    }
}
