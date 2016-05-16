import com.opencsv.CSVReader;
import java.io.*;
import java.util.*;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.BaseFont;
public class Round{
    
    public static void main(String [] args)  {
        List<List<String[]>> Data = new ArrayList<List<String[]>>();

        List<String[]> Physics = new ArrayList<String[]>();
        List<String[]> Mathematics = new ArrayList<String[]>();
        List<String[]> Biology = new ArrayList<String[]>();
        List<String[]> Chemistry = new ArrayList<String[]>();
        List<String[]> EarthSpace = new ArrayList<String[]>();
        List<String[]> Energy = new ArrayList<String[]>();

        float totalNum;
        int physicsNum;
        int mathematicsNum;
        int biologyNum;
        int chemistryNum;
        int earthSpaceNum;
        int energyNum;
        int [] num;
        int roundNum;
        String [] names;

        try {
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("Questions.csv"), "UTF-8"));
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
        
        totalNum = Physics.size() + Mathematics.size() + Biology.size() + Chemistry.size() + EarthSpace.size() + Energy.size();
        physicsNum = (int)(25*Physics.size()/totalNum + .5);
        mathematicsNum = (int)(25*Mathematics.size()/totalNum + .5);
        biologyNum = (int)(25*Biology.size()/totalNum + .5);
        chemistryNum = (int)(25*Chemistry.size()/totalNum + .5);
        earthSpaceNum = (int)(25*EarthSpace.size()/totalNum + .5);
        energyNum = (int)(25*Energy.size()/totalNum + .5);

        //dealing with cases in which frequency of questions is exactly n + .5
        //=> cause rounding would give extra questions that don't add up to 25
        int extra = physicsNum + mathematicsNum + biologyNum + chemistryNum + earthSpaceNum + energyNum - 25;
        earthSpaceNum = earthSpaceNum - extra;
        
        num = new int [] {physicsNum, mathematicsNum, biologyNum, chemistryNum, earthSpaceNum, energyNum};
        roundNum = (int)(totalNum)/25;
        names = new String [] {"PHYSICS", "MATHEMATICS", "BIOLOGY", "CHEMISTRY", "EARTH and SPACE", "ENERGY"};


        //Writing PDFs
        try
        {
            //Font stuff
            FontFactory.register("fonts/ArialUnicode.ttf","ArialUnicodeMS");
            Font title = FontFactory.getFont("ArialUnicodeMS",BaseFont.IDENTITY_H,BaseFont.EMBEDDED, 10, Font.BOLD);
            Font bigTitle = FontFactory.getFont("ArialUnicodeMS",BaseFont.IDENTITY_H,BaseFont.EMBEDDED, 12, Font.BOLD);
            Font header = FontFactory.getFont("ArialUnicodeMS",BaseFont.IDENTITY_H,BaseFont.EMBEDDED, 14, Font.BOLD);
            Font body = FontFactory.getFont("ArialUnicodeMS",BaseFont.IDENTITY_H,BaseFont.EMBEDDED, 10, Font.NORMAL);

            /*
             * THESE FONTS did not work with unicode :( 
            Font title = FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD, new CMYKColor(255, 255, 255, 0));
            Font bigTitle = FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD, new CMYKColor(255, 255, 255, 0));
            Font body = FontFactory.getFont(FontFactory.COURIER, 10, Font.NORMAL, new CMYKColor(255, 255, 255, 0));
            */

            for(int j = 1; j <= roundNum; j++){
                
                Document document = new Document();
                String pdfName=""+j+".pdf";

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("rounds/" + pdfName));
            document.open();
            
            //main paragraph to start of main chapter
            Paragraph subjectTitle = new Paragraph("Round " + j +"\n", header);
            //write chapter
            Chapter subjectChapter = new Chapter(subjectTitle, 1);
            subjectChapter.setNumberDepth(0);

            List<String[]> foo;
            String [] temp;
            for(int i = 0; i < 6; i++){
                foo = Data.get(i);
                for(int k=0; k< num[i];k++){
                    if(foo.size() > 0) {
                    temp = foo.get(foo.size()-1);
                    Data.get(i).remove(foo.size()-1);
               

                Section questionSubject = subjectChapter.addSection(new Paragraph(names[i], bigTitle));
                if(temp[3].equals("Multiple Choice") && temp[4].equals("Short Answer")){
                    //Toss Up Multiple Choice
                    questionSubject.add(new Paragraph("Toss Up: Multiple Choice", title));
                    questionSubject.add(
                            new Paragraph(
                                "\t" + temp[6] + "\n\t\t" + 
                                "W) " + temp[7] + "\n\t\t" +
                                "X) " + temp[8] + "\n\t\t" +
                                "Y) " + temp[9] + "\n\t\t" +
                                "Z) " + temp[10] + "\n", body));
                    questionSubject.add(new Paragraph("\tToss Up Answer: " + temp[11] + "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------", title));
                    //Bonus Short Answer
                    questionSubject.add(new Paragraph("Bonus: Short Answer", title));
                    questionSubject.add(new Paragraph("\t" + temp[12] + "\n", body));
                    questionSubject.add(new Paragraph("\tBonus Answer: " + temp[13] + "\n=========================================================================================\n", title));
                } else if(temp[3].equals("Multiple Choice") && temp[4].equals("Multiple Choice")){
                    //Toss Up Multiple Choice
                    questionSubject.add(new Paragraph("Toss Up: Multiple Choice", title));
                    questionSubject.add(
                            new Paragraph(
                                "\t" + temp[14] + "\n\t\t" + 
                                "W) " + temp[15] + "\n\t\t" +
                                "X) " + temp[16] + "\n\t\t" +
                                "Y) " + temp[17] + "\n\t\t" +
                                "Z) " + temp[18] + "\n\n", body));
                    questionSubject.add(new Paragraph("\tToss Up Answer: " + temp[19] + "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------", title));
                    //Bonus Multiple Choice
                    questionSubject.add(new Paragraph("Bonus: Multiple Choice", title));
                    questionSubject.add(
                            new Paragraph(
                                "\t" + temp[20] + "\n\t\t" + 
                                "W) " + temp[21] + "\n\t\t" +
                                "X) " + temp[22] + "\n\t\t" +
                                "Y) " + temp[23] + "\n\t\t" +
                                "Z) " + temp[24] + "\n\n", body));
                    questionSubject.add(new Paragraph("\tBonus Answer: " + temp[25] + "\n=========================================================================================\n", title));
                } else if(temp[3].equals("Short Answer") && temp[5].equals("Multiple Choice")){
                    //Toss Up Short Answer
                    questionSubject.add(new Paragraph("Toss Up: Short Answer", title));
                    questionSubject.add(new Paragraph("\t" + temp[26] + "\n", body));
                    questionSubject.add(new Paragraph("\tBonus Answer: " + temp[27] + "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------", title));
                    //Bonus Multiple Choice
                    questionSubject.add(new Paragraph("Bonus: Multiple Choice", title));
                    questionSubject.add(
                            new Paragraph(
                                "\t" + temp[28] + "\n\t\t" + 
                                "W) " + temp[29] + "\n\t\t" +
                                "X) " + temp[30] + "\n\t\t" +
                                "Y) " + temp[31] + "\n\t\t" +
                                "Z) " + temp[32] + "\n\n", body));
                    questionSubject.add(new Paragraph("\tBonus Answer: " + temp[33] + "\n=========================================================================================\n", title));


                } else if(temp[3].equals("Short Answer") && temp[5].equals("Short Answer")){
                    //Toss Up Short Answer
                    questionSubject.add(new Paragraph("Toss Up: Short Answer", title));
                    questionSubject.add(new Paragraph("\t" + temp[34] + "\n", body));
                    questionSubject.add(new Paragraph("\tBonus Answer: " + temp[35] + "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------", title));
                    //Bonus Short Answer
                    questionSubject.add(new Paragraph("Bonus: Short Answer", title));
                    questionSubject.add(new Paragraph("\t" + temp[36] + "\n", body));
                    questionSubject.add(new Paragraph("\tBonus Answer: " + temp[37] + "\n=========================================================================================\n", title));
                } else {
                    System.out.println("UH OH SOMETHING WENT WRONG at: " + i + "; 3: " + temp[3] + " 4: " + temp[4] + " 5: " + temp[5] );
                }
                }}}
            
            document.add(subjectChapter);
                

            //Set attributes here
            document.addAuthor("Shantanu Jha");
            document.addCreationDate();
            document.addCreator("https://sites.google.com/site/stuyvesantsciencebowl/");
            document.addTitle("Physics");
            document.addSubject("Physics Questions");
            
            
            document.close();
            writer.close();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        

    }
}
