import com.opencsv.CSVReader;
import java.io.*;
import java.util.*;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
public class RoundMaker{
    
    public static void main(String [] args) {
        List<List<String[]>> Data = new ArrayList<List<String[]>>();

        List<String[]> Physics = new ArrayList<String[]>();
        List<String[]> Mathematics = new ArrayList<String[]>();
        List<String[]> Biology = new ArrayList<String[]>();
        List<String[]> Chemistry = new ArrayList<String[]>();
        List<String[]> EarthSpace = new ArrayList<String[]>();
        List<String[]> Energy = new ArrayList<String[]>();
        try {
            CSVReader reader = new CSVReader(new FileReader("yourfile.csv"));
            String [] nextLine;
            
            while ((nextLine = reader.readNext()) != null) {
                //choose which List to add the Question Pairs to
                switch(nextLine[2]){
                    case "Physics":
                        Physics.add(nextLine);
                        break;
                    case "Chemistry":
                        Chemistry.add(nextLine);
                        break;
                    case "Mathematics":
                        Mathematics.add(nextLine);
                        break;
                    case "Earth and Space Science":
                        EarthSpace.add(nextLine);
                        break;
                    case "Energy":
                        Energy.add(nextLine);
                        break;
                    case "Biology":
                        Biology.add(nextLine);
                        break;
                    default:
                        break;
                }
            }
            //Add the individual Lists to the main Data List
            Data.add(Physics);
            Data.add(Mathematics);
            Data.add(Biology);
            Data.add(Chemistry);
            Data.add(EarthSpace);
            Data.add(Energy);
        } catch (IOException e){
        }

        Font body = FontFactory.getFont(FontFactory.COURIER, 10, Font.NORMAL, new CMYKColor(255, 255, 255, 0));

        //Writing PDFs
        Document document = new Document();
        try
        {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Physics.pdf"));
            document.open();
            
            //main paragraph to start of main chapter
            Paragraph physicsTitle = new Paragraph("PHYSICS", body);
            //write chapter
            Chapter physicsText = new Chapter(physicsTitle, 1);
            physicsText.setNumberDepth(0);

            Paragraph question1Title = new Paragraph("Test", body);
            Section question1 = physicsText.addSection(question1Title);
            
            Paragraph question1Content = new Paragraph("Test Body", body);
            question1.add(question1Content);

            document.add(physicsText);


            //Set attributes here
            document.addAuthor("Shantanu Jha");
            document.addCreationDate();
            document.addCreator("https://sites.google.com/site/stuyvesantsciencebowl/");
            document.addTitle("Physics");
            document.addSubject("Physics Questions");
            
            
            document.close();
            writer.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        

    }
}
