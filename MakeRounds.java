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
public class MakeRounds{

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

        int [] extraQuestions; // to fill in gaps at the end of rounds

        List<int[]> num;
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
		System.out.println("Extra: " + extra);
		System.out.println("Energy: " + energyNum);
        earthSpaceNum = earthSpaceNum - extra;
        num = new ArrayList<int[]>();
        if (physicsNum > 0) num.add(new int [] {physicsNum, 0});
        if (mathematicsNum > 0) num.add(new int [] {mathematicsNum, 1});
        if (biologyNum > 0) num.add(new int [] {biologyNum, 2});
        if (chemistryNum > 0) num.add(new int [] {chemistryNum, 3});
        if (earthSpaceNum > 0) num.add(new int [] {earthSpaceNum, 4});
        if (energyNum > 0) num.add(new int [] {energyNum,5});




    
        roundNum = (int)(totalNum)/25;

		System.out.println("\n------------------------------------------------------------------------------------------------------------");
		System.out.println("Earth Space Size: " + EarthSpace.size() + " Earth Space Expected: " + earthSpaceNum*roundNum);
		System.out.println("Bio  Size: " + Biology.size() + " Bio Expected: " + biologyNum*roundNum);
		System.out.println("Math Size: " + Mathematics.size() + " Math Expected: " + mathematicsNum*roundNum);
		System.out.println("Energy Size: " + Energy.size() + " Energy Expected: " + energyNum*roundNum);
		System.out.println("Chemistry Size: " + Chemistry.size() + " Chemistry Expected: " + chemistryNum*roundNum);
		System.out.println("Physics Size: " + Physics.size() + " Physics Expected: " + physicsNum*roundNum);
		System.out.println("------------------------------------------------------------------------------------------------------------");
        names = new String [] {"PHYSICS", "MATHEMATICS", "BIOLOGY", "CHEMISTRY", "EARTH and SPACE", "ENERGY"};
        
        extraQuestions = new int [] {Physics.size() - physicsNum*roundNum, Mathematics.size() - mathematicsNum*roundNum, Biology.size() - biologyNum*roundNum, Chemistry.size() - chemistryNum*roundNum, EarthSpace.size() - earthSpaceNum*roundNum, Energy.size() - energyNum*roundNum};


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
            List<int[]> numTemp = new ArrayList<int[]>(num); //stores distribution of number of questions for each subject in a round
            //System.out.println("Initial numTemp size: " + numTemp.size() + " num size: " + num.size());
            int choice;
            
            
            List<String[][]> roundQuestions = new ArrayList<String[][]>();
            //collecting questions for a round
            for(int i = 0; i < 25; i++){
                int rand = (int)(Math.random()*numTemp.size());
                numTemp.set(rand, new int [] {numTemp.get(rand)[0]-1, numTemp.get(rand)[1]});
                choice = numTemp.get(rand)[1];
               
                /*
                System.out.println();
                for(int n =0; n < numTemp.size(); n++){
                    System.out.print("" + numTemp.get(n)[0] + ":" + numTemp.get(n)[1] + " ");
                }
                */

				System.out.println("\n------------------------------------------------------------------------------------------------------------");
				System.out.println("Energy Extra Questions Left: " + extraQuestions[5]);
				System.out.println("Random Subject Category Chosen: " + names[numTemp.get(rand)[1]]);

                if(numTemp.get(rand)[0] <= 0) {
                    numTemp.remove(rand);
                }


                foo = Data.get(choice);
                if(foo.size() == 0){
                    int extraQuestionPos = (int)(Math.random()*6);
                    while(extraQuestions[extraQuestionPos] <= 0){
                        extraQuestionPos = (int)(Math.random()*6);
                    }
                    extraQuestions[extraQuestionPos] -= 1;
                    choice = extraQuestionPos;
                    foo = Data.get(choice);
                }
				System.out.println("Round: " + j + " Question: " + (i + 1));

				System.out.println("Earth Space Size: " + EarthSpace.size() + " Earth Space Expected: " + earthSpaceNum*roundNum);
				System.out.println("Bio  Size: " + Biology.size() + " Bio Expected: " + biologyNum*roundNum);
				System.out.println("Math Size: " + Mathematics.size() + " Math Expected: " + mathematicsNum*roundNum);
				System.out.println("Energy Size: " + Energy.size() + " Energy Expected: " + energyNum*roundNum);
				System.out.println("Chemistry Size: " + Chemistry.size() + " Chemistry Expected: " + chemistryNum*roundNum);
				System.out.println("Physics Size: " + Physics.size() + " Physics Expected: " + physicsNum*roundNum);
				System.out.println("Question Type: " + names[choice]);
				System.out.println("------------------------------------------------------------------------------------------------------------");
				
				
				temp = foo.get(foo.size()-1);
                Data.get(choice).remove(foo.size()-1);
                roundQuestions.add(new String [][] {{""+choice}, temp});
            }


            //generating PDF
            int lastSubject = -1; //made to prevent repeating Subjects in consecutive questions
            temp = null;
            int questionNum = -1; //next question picked out of roundQuestions
            int subject;
            for(int i = 25; i > 0; i--){

                //making sure that the next question is not the same subject as
                //the last question!!!
                questionNum = 0;
                subject = Integer.parseInt((roundQuestions.get(questionNum)[0][0]));
                while(lastSubject == subject && questionNum < i-1){
                    questionNum++;
                    subject = Integer.parseInt((roundQuestions.get(questionNum)[0][0]));
                }

                lastSubject = subject;
                temp = roundQuestions.get(questionNum)[1]; 
                roundQuestions.remove(questionNum);
                

                //formatting PDF
                Section questionSubject = subjectChapter.addSection(new Paragraph(names[subject], bigTitle));
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
                    for(int l=0; l<temp.length; l++){
                        System.out.println(temp[l]);
                    }
                }
            }

            //adding round to PDF
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
