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


public class OrderedRounds3{

    public static Boolean allSame(List<String[]> x) {
        for (int i = 0; i < x.size()-1; i++) {
            if (!x.get(i)[2].equals(x.get(i+1)[2])) return false;
        }
        return true;
    }

    public static Boolean hasRepeats(List<String> x) {
        for (int i = 0; i < x.size() - 1; i++) {
            if (x.get(i).equals(x.get(i+1))) return true;
        }
        return false;
    }
    public static List<String> getDist() {
        int math = 5;
        int bio = 5; 
        int chem = 5; 
        int physics = 5; 
        int es = 5; 
        int energy = (int) (Math.random() * 2); 
        int total = math + bio + chem + physics + es + energy; 
        int [] vals = {math, bio, chem, physics, es}; 
        while (total > 25) {
            int rand = (int) (Math.random() * 5); 
            if (vals[rand] > 4) vals[rand]--;
            total = vals[0] + vals[1] + vals[2] + vals[3] + vals[4] + energy; 
            System.out.println(total);
        }
        List<String> dist = new ArrayList<String>(); 
        for (int i = 0; i < vals[0]; i++) dist.add("Mathematics"); 
        for (int i = 0; i < vals[1]; i++) dist.add("Biology"); 
        for (int i = 0; i < vals[2]; i++) dist.add("Chemistry"); 
        for (int i = 0; i < vals[3]; i++) dist.add("Physics");
        for (int i = 0; i < vals[4]; i++) dist.add("Earth and Space Science"); 
        for (int i = 0; i < energy; i++) dist.add("Energy");
        while (hasRepeats(dist)) Collections.shuffle(dist); 
        return dist; 
    }

    public static Boolean allZero(HashMap<String, Integer> x) {
        for(Map.Entry<String, Integer> entry: x.entrySet()) {
            if (!(entry.getValue().compareTo(0) <= 0)) {
                return false;
            }
        }
        return true;
    }

    public static Boolean noMore(List<String []> x, String subj) {
        for (String [] i : x) {
            if (i[2].equals(subj)) return false;
        }
        return true;
    } 

    public static Boolean others(HashMap<String, Integer> x, String subj) {
        for(Map.Entry<String, Integer> entry: x.entrySet()) {
            if (!entry.getKey().equals(subj) && entry.getValue().compareTo(0) > 0) {
                return false;
            }
        }
        return true;
    }
    public static void main(String [] args)  {
		//initialize input reader
		
		InputStreamReader isr;
		BufferedReader in;

		isr = new InputStreamReader( System.in );
		in = new BufferedReader(isr);

        HashMap<String, List<String[]>> data = new HashMap<>();
        List<String[]> extras = new ArrayList<String[]>(); 

		try {
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("Questions.csv"), "UTF-8"));
            String [] nextLine;
            reader.readNext();
            while ((nextLine = reader.readNext()) != null) {
                //choose which List to add the Question Pairs to
               if (data.get(nextLine[38]) == null && !nextLine[38].equals("")) {
                   data.put(nextLine[38], new ArrayList<String[]>());
               }
               if (!nextLine[38].equals("")) //nextLine[38] = "1";
                   data.get(nextLine[38]).add(nextLine);
            }
        } catch (IOException e){
        }
        
        
        //Writing PDFs
        try
        {
            //Font stuff
            /*
            FontFactory.register("fonts/ArialUnicode.ttf","ArialUnicodeMS");
            Font title = FontFactory.getFont("ArialUnicodeMS",BaseFont.IDENTITY_H,BaseFont.EMBEDDED, 10, Font.BOLD);
            Font bigTitle = FontFactory.getFont("ArialUnicodeMS",BaseFont.IDENTITY_H,BaseFont.EMBEDDED, 12, Font.BOLD);
            Font header = FontFactory.getFont("ArialUnicodeMS",BaseFont.IDENTITY_H,BaseFont.EMBEDDED, 14, Font.BOLD);
            Font body = FontFactory.getFont("ArialUnicodeMS",BaseFont.IDENTITY_H,BaseFont.EMBEDDED, 10, Font.NORMAL);
            */
            FontFactory.register("fonts/Montserrat-Light.otf","Montserrat-Light");
            Font title = FontFactory.getFont("Montserrat-Light",BaseFont.IDENTITY_H,BaseFont.EMBEDDED, 10, Font.BOLD);
            Font bigTitle = FontFactory.getFont("Montserrat-Light",BaseFont.IDENTITY_H,BaseFont.EMBEDDED, 12, Font.BOLD);
            Font header = FontFactory.getFont("Montserrat-Light",BaseFont.IDENTITY_H,BaseFont.EMBEDDED, 14, Font.BOLD);
            Font body = FontFactory.getFont("Montserrat-Light",BaseFont.IDENTITY_H,BaseFont.EMBEDDED, 10, Font.NORMAL);
            /*
             * THESE FONTS did not work with unicode :( 
            Font title = FontFactory.getFont(FontFactory.COURIER, 10, Font.BOLD, new CMYKColor(255, 255, 255, 0));
            Font bigTitle = FontFactory.getFont(FontFactory.COURIER, 12, Font.BOLD, new CMYKColor(255, 255, 255, 0));
            Font body = FontFactory.getFont(FontFactory.COURIER, 10, Font.NORMAL, new CMYKColor(255, 255, 255, 0));
            */
            HashMap<String, Integer> distribution = new HashMap<>(); 
            HashMap<String, List<String[]>> finalData = new HashMap<>(); 
            int pdfNum = 0; 
            int total = 0; 
            int diffNum = 0; 
            String lastSubj = ""; 
            for(Map.Entry<String, List<String[]>> entry: data.entrySet()) {
                diffNum = Integer.parseInt(entry.getKey());
                List<String[]> questionsOfDiff = entry.getValue();
                for (String [] x : extras) {
                    if (Math.abs(diffNum - Integer.parseInt(x[38])) < 2) questionsOfDiff.add(x);
                } 
                distribution.put("Mathematics", 5); 
                distribution.put("Earth and Space Science", 5); 
                distribution.put("Physics", 5); 
                distribution.put("Chemistry", 5); 
                distribution.put("Biology", 4);
                distribution.put("Energy", 1); 
                List<String> dist = getDist();
                for (int i = 0; i < questionsOfDiff.size(); i++) {
                    if (total % 25 == 0) {
                        pdfNum++;
                        dist = getDist(); 
                    }
                    total++;
                    if (finalData.get(pdfNum+"") == null) {
                        finalData.put(pdfNum+"", new ArrayList<String[]>());
                    }
                    int rand = (int) (Math.random() * questionsOfDiff.size());
                    String subj = questionsOfDiff.get(rand)[2]; 
                    while (!subj.equals(dist.get(total %25))) {
                        if (noMore(questionsOfDiff, dist.get(total % 25))) break;
                        rand = (int) (Math.random() * questionsOfDiff.size());
                        subj = questionsOfDiff.get(rand)[2]; 
                    }
                    // lastSubj = subj;
                    for(Map.Entry<String, Integer> x: distribution.entrySet()) {
                        if (allSame(questionsOfDiff) || noMore(questionsOfDiff, x.getKey())) {
                            for (int j = 0; j < questionsOfDiff.size(); j++) {
                                extras.add(questionsOfDiff.get(j));
                                questionsOfDiff.remove(j);
                                j--;
                            }
                        }
                    }
                    if (!questionsOfDiff.isEmpty()) {
                        finalData.get(pdfNum + "").add(questionsOfDiff.get(rand));
                        questionsOfDiff.remove(rand); 
                      //  System.out.println(questionsOfDiff.size() + "\t" + subj); 
                    }
                    i--;
                }
            }

            for(Map.Entry<String, List<String[]>> entry: finalData.entrySet()) {

                Document document = new Document();
                List<String[]> questions = entry.getValue();
                String diff = entry.getKey();
                System.out.println(diff + ": " + questions.size());
                String pdfName = diff + ".pdf";

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("Difficulty/" + pdfName));
            document.open();
            
            //main paragraph to start of main chapter
            Paragraph diffTitle = new Paragraph(diff+"\n", header);
            //write chapter
            Chapter diffChapter = new Chapter(diff, 1);
            diffChapter.setNumberDepth(0);

            String [] temp;
            
            for(int i = 0; i < questions.size(); i++){
                temp = questions.get(i);
                String subjectName = temp[2];

                Section questionSubject = diffChapter.addSection(new Paragraph(subjectName, bigTitle));
				// if(names == 1) questionSubject.add(new Paragraph("Writer: " +  temp[1], title)); //name of the question writer
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
                    questionSubject.add(new Paragraph("\tToss Up Answer: " + temp[27] + "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------", title));
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
                    questionSubject.add(new Paragraph("\tToss Up Answer: " + temp[35] + "\n-----------------------------------------------------------------------------------------------------------------------------------------------------------", title));
                    //Bonus Short Answer
                    questionSubject.add(new Paragraph("Bonus: Short Answer", title));
                    questionSubject.add(new Paragraph("\t" + temp[36] + "\n", body));
                    questionSubject.add(new Paragraph("\tBonus Answer: " + temp[37] + "\n=========================================================================================\n", title));
                } else {
                    System.out.println("UH OH SOMETHING WENT WRONG at: " + i + "; 3: " + temp[3] + " 4: " + temp[4] + " 5: " + temp[5] );
                }
            }
            
            document.add(diffChapter);
                

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