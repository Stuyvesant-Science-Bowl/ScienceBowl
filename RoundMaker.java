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
            CSVReader reader = new CSVReader(new FileReader("Questions.csv"));
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
        
        Font title = FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD, new CMYKColor(255, 255, 255, 0));
        Font bigTitle = FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD, new CMYKColor(255, 255, 255, 0));
        Font body = FontFactory.getFont(FontFactory.COURIER, 10, Font.NORMAL, new CMYKColor(255, 255, 255, 0));

        //Writing PDFs
        Document document = new Document();
        try
        {

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Physics.pdf"));
            document.open();
            
            //main paragraph to start of main chapter
            Paragraph physicsTitle = new Paragraph("PHYSICS\n", bigTitle);
            //write chapter
            Chapter physicsChapter = new Chapter(physicsTitle, 1);
            physicsChapter.setNumberDepth(0);

            String [] temp;
            
            for(int i = 0; i < Physics.size(); i++){
                temp = Physics.get(i);
               

                Section questionPhysics = physicsChapter.addSection(new Paragraph("PHYSICS", bigTitle));
                if(temp[3].equals("Multiple Choice") && temp[4].equals("Short Answer")){
                    //Toss Up Multiple Choice
                    questionPhysics.add(new Paragraph("Toss Up: Multiple Choice", title));
                    questionPhysics.add(
                            new Paragraph(
                                "\t" + temp[6] + "\n\t\t" + 
                                "W) " + temp[7] + "\n\t\t" +
                                "X) " + temp[8] + "\n\t\t" +
                                "Y) " + temp[9] + "\n\t\t" +
                                "Z) " + temp[10] + "\n", body));
                    questionPhysics.add(new Paragraph("\tToss Up Answer: " + temp[11] + "\n---------------------------------------------------------------------------", title));
                    //Bonus Short Answer
                    questionPhysics.add(new Paragraph("Bonus: Short Answer", title));
                    questionPhysics.add(new Paragraph("\t" + temp[12] + "\n", body));
                    questionPhysics.add(new Paragraph("\tBonus Answer: " + temp[13] + "\n======================================================================\n", title));
                } else if(temp[3].equals("Multiple Choice") && temp[4].equals("Multiple Choice")){
                    //Toss Up Multiple Choice
                    questionPhysics.add(new Paragraph("Toss Up: Multiple Choice", title));
                    questionPhysics.add(
                            new Paragraph(
                                "\t" + temp[14] + "\n\t\t" + 
                                "W) " + temp[15] + "\n\t\t" +
                                "X) " + temp[16] + "\n\t\t" +
                                "Y) " + temp[17] + "\n\t\t" +
                                "Z) " + temp[18] + "\n\n", body));
                    questionPhysics.add(new Paragraph("\tToss Up Answer: " + temp[19] + "\n---------------------------------------------------------------------------", title));
                    //Bonus Multiple Choice
                    questionPhysics.add(new Paragraph("Bonus: Multiple Choice", title));
                    questionPhysics.add(
                            new Paragraph(
                                "\t" + temp[20] + "\n\t\t" + 
                                "W) " + temp[21] + "\n\t\t" +
                                "X) " + temp[22] + "\n\t\t" +
                                "Y) " + temp[23] + "\n\t\t" +
                                "Z) " + temp[24] + "\n\n", body));
                    questionPhysics.add(new Paragraph("\tBonus Answer: " + temp[25] + "\n======================================================================\n", title));
                } else if(temp[3].equals("Short Answer") && temp[5].equals("Multiple Choice")){
                    //Toss Up Short Answer
                    questionPhysics.add(new Paragraph("Toss Up: Short Answer", title));
                    questionPhysics.add(new Paragraph("\t" + temp[26] + "\n", body));
                    questionPhysics.add(new Paragraph("\tBonus Answer: " + temp[27] + "\n---------------------------------------------------------------------------", title));
                    //Bonus Multiple Choice
                    questionPhysics.add(new Paragraph("Bonus: Multiple Choice", title));
                    questionPhysics.add(
                            new Paragraph(
                                "\t" + temp[28] + "\n\t\t" + 
                                "W) " + temp[29] + "\n\t\t" +
                                "X) " + temp[30] + "\n\t\t" +
                                "Y) " + temp[31] + "\n\t\t" +
                                "Z) " + temp[32] + "\n\n", body));
                    questionPhysics.add(new Paragraph("\tBonus Answer: " + temp[33] + "\n======================================================================\n", title));


                } else if(temp[3].equals("Short Answer") && temp[5].equals("Multiple Choice")){
                    //Toss Up Short Answer
                    questionPhysics.add(new Paragraph("Toss Up: Short Answer", title));
                    questionPhysics.add(new Paragraph("\t" + temp[34] + "\n", body));
                    questionPhysics.add(new Paragraph("\tBonus Answer: " + temp[35] + "\n---------------------------------------------------------------------------", title));
                    //Bonus Short Answer
                    questionPhysics.add(new Paragraph("Bonus: Short Answer", title));
                    questionPhysics.add(new Paragraph("\t" + temp[36] + "\n", body));
                    questionPhysics.add(new Paragraph("\tBonus Answer: " + temp[37] + "\n======================================================================\n", title));
                } else {
                    System.out.println("UH OH SOMETHING WENT WRONG at: " + i);
                }
            }
            
            document.add(physicsChapter);

                

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
