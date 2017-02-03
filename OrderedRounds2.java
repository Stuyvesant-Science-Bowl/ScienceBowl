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
public class OrderedRounds2{

	
	private static void clear(){
		final String clear = "\u001b[2J";
		final String home = "\u001b[H";
		System.out.print(clear + home);
		System.out.flush();
	}

	private static void delay(){
		try {
			Thread.sleep(200);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public static void printNames(List<String> names){
		System.out.println("LIST OF QUESTION CONTRIBUTORS: ");
		for( int i = 1; i < names.size(); i++)
			System.out.println("\t" + i + ". " + names.get(i));
	
	}


    public static void main(String [] args)  {
		clear(); 
		//Input to determine whether or not to display names!
		InputStreamReader isr;
		BufferedReader in;

		isr = new InputStreamReader( System.in );
		in = new BufferedReader( isr );
		
		List<String[]> PreData = new ArrayList<String[]>();
		List<List<String[]>> Data = new ArrayList<List<String[]>>();

        List<String[]> Physics = new ArrayList<String[]>();
        List<String[]> Mathematics = new ArrayList<String[]>();
        List<String[]> Biology = new ArrayList<String[]>();
        List<String[]> Chemistry = new ArrayList<String[]>();
        List<String[]> EarthSpace = new ArrayList<String[]>();
        List<String[]> Energy = new ArrayList<String[]>();


		//input

        String answerChoice;
		int useNames = -1; //whether or not to include names when listing quetions: -1 -> initialized, 0 -> no, 1 -> yes
		int removePlayers = -1; //whether or not to remove questions by certain players: -1 -> initialized, 0 -> no, 1-> yes
		List<String> goodNames = new ArrayList<String>();
		List<String> badNames = new ArrayList<String>(); //list of names to remove from question repo when forming rounds


		
		System.out.println("Do you wish to print names?");
		while (useNames < 0) {
			System.out.print("Please enter a valid answer (\"y\" or \"n\"): ");
			try {
				 answerChoice = in.readLine();
				 if(answerChoice.equals("n")) useNames = 0;
				 if(answerChoice.equals("y")) useNames = 1;

			}
			catch ( IOException e ) {useNames = -1;}
			catch ( NumberFormatException e) {useNames = -1;}
		}



		System.out.println("Do you wish to remove questions by certain players?");
		while (removePlayers < 0) {
			System.out.print("Please enter a valid answer (\"y\" or \"n\"): ");
			try {
				 answerChoice = in.readLine();
				 if(answerChoice.equals("n")) removePlayers = 0;
				 if(answerChoice.equals("y")) removePlayers = 1;

			}
			catch ( IOException e ) {removePlayers = -1;}
			catch ( NumberFormatException e) {removePlayers = -1;}
		}
	
		
		//end of input
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

        String [] nextLine;
        
        try {
            CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream("Questions.csv"), "UTF-8"));

            while ((nextLine = reader.readNext()) != null) {
                PreData.add(nextLine);
				if(!goodNames.contains(nextLine[1])) goodNames.add(nextLine[1]);
			}
        } catch (IOException e){
        }

		int playerToRemove; //player to remove
		String nameToRemove;
		while (removePlayers > 0){
			playerToRemove = 1;
			clear();
			System.out.println("Please enter the number of the player you wish to remove.");
			printNames(goodNames);
			while (playerToRemove > 0) {
				System.out.print("Enter a valid player number from 1-" + (goodNames.size()-1) + " or enter \"0\" to stop removing and generate the rounds: ");
				try {
					playerToRemove = Integer.parseInt(in.readLine());
					if (playerToRemove > 0 && playerToRemove < goodNames.size()){
						nameToRemove = goodNames.get(playerToRemove);
						badNames.add(nameToRemove);
						if(!goodNames.remove(nameToRemove)){
							System.out.println("ERROR: Name to remove not found!");
						} else {
							System.out.println(nameToRemove + "'s questions were removed!");
						}
						playerToRemove = -1;
						removePlayers = 1; //just to make sure the outermost while loop keeps going
					} else if (playerToRemove == 0){
						System.out.println("ALRIGHT, generating the rounds now!");
						playerToRemove = -1;
						removePlayers = -1; //to stop the outermost while loop
					} else {
						System.out.println("Please enter a valid player number between 1 and " + (goodNames.size() - 1));
						playerToRemove = 1;
						removePlayers = 1;
					}
				}
				catch ( IOException e ) {playerToRemove = -1;}
				catch ( NumberFormatException e) {playerToRemove = -1;}
			}
		}
			


		for (int ques = PreData.size()-1; ques >= 0; ques--){
			if (!badNames.contains(PreData.get(ques)[1])){
				nextLine = PreData.get(ques);
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
		}
            //Add the individual Lists to the main Data List
        Data.add(Physics);
        Data.add(Mathematics);
        Data.add(Biology);
        Data.add(Chemistry);
        Data.add(EarthSpace);
        Data.add(Energy);

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
		/*Debugging
		System.out.println("Extra: " + extra);
		System.out.println("Energy: " + energyNum);
		*/
        earthSpaceNum = earthSpaceNum - extra;
        num = new ArrayList<int[]>();
        if (physicsNum > 0) num.add(new int [] {physicsNum, 0});
        if (mathematicsNum > 0) num.add(new int [] {mathematicsNum, 1});
        if (biologyNum > 0) num.add(new int [] {biologyNum, 2});
        if (chemistryNum > 0) num.add(new int [] {chemistryNum, 3});
        if (earthSpaceNum > 0) num.add(new int [] {earthSpaceNum, 4});
        if (energyNum > 0) num.add(new int [] {energyNum,5});




    
        roundNum = (int)(totalNum)/25;

		roundNum = roundNum - 1; //HOTFIX -- not permenant
		/*Debugging
		System.out.println("\n------------------------------------------------------------------------------------------------------------");
		System.out.println("Earth Space Size: " + EarthSpace.size() + " Earth Space Expected: " + earthSpaceNum*roundNum);
		System.out.println("Bio  Size: " + Biology.size() + " Bio Expected: " + biologyNum*roundNum);
		System.out.println("Math Size: " + Mathematics.size() + " Math Expected: " + mathematicsNum*roundNum);
		System.out.println("Energy Size: " + Energy.size() + " Energy Expected: " + energyNum*roundNum);
		System.out.println("Chemistry Size: " + Chemistry.size() + " Chemistry Expected: " + chemistryNum*roundNum);
		System.out.println("Physics Size: " + Physics.size() + " Physics Expected: " + physicsNum*roundNum);
		System.out.println("------------------------------------------------------------------------------------------------------------");
		*/
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

            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("orderedRounds/" + pdfName));
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
			//rand is always 0 because it will just take the first entry in numTemp (i.e. the first subject that still has a positive number of 
			//questions left to contribute to the round
			for(int i = 0; i < 25; i++){	
                numTemp.set(0, new int [] {numTemp.get(0)[0]-1, numTemp.get(0)[1]});
                choice = numTemp.get(0)[1];
                /*DeBugging
                System.out.println();
                for(int n =0; n < numTemp.size(); n++){
                    System.out.print("" + numTemp.get(n)[0] + ":" + numTemp.get(n)[1] + " ");
                }
                */
				/* DeBugging
				System.out.println("\n------------------------------------------------------------------------------------------------------------");
				System.out.println("Energy Extra Questions Left: " + extraQuestions[5]);
				System.out.println("Random Subject Category Chosen: " + names[numTemp.get(rand)[1]]);
				*/
                if(numTemp.get(0)[0] <= 0) {
                    numTemp.remove(0);
                }


                foo = Data.get(choice);
                if(foo.size() == 0){
					//System.out.println("lol");
                    int extraQuestionPos = (choice + 1)%6;
                    while(extraQuestions[extraQuestionPos] <= 0){
                        extraQuestionPos = (choice + 1)%6;
                    }
                    extraQuestions[extraQuestionPos] -= 1;
                    choice = extraQuestionPos;
                    foo = Data.get(choice);
                }

				System.out.println("Round: " + j + " Question: " + (i + 1));
				/* DeBugging

				System.out.println("Earth Space Size: " + EarthSpace.size() + " Earth Space Expected: " + earthSpaceNum*roundNum);
				System.out.println("Bio  Size: " + Biology.size() + " Bio Expected: " + biologyNum*roundNum);
				System.out.println("Math Size: " + Mathematics.size() + " Math Expected: " + mathematicsNum*roundNum);
				System.out.println("Energy Size: " + Energy.size() + " Energy Expected: " + energyNum*roundNum);
				System.out.println("Chemistry Size: " + Chemistry.size() + " Chemistry Expected: " + chemistryNum*roundNum);
				System.out.println("Physics Size: " + Physics.size() + " Physics Expected: " + physicsNum*roundNum);
				System.out.println("Question Type: " + names[choice]);
				System.out.println("------------------------------------------------------------------------------------------------------------");
				
				*/


				System.out.println("Choice: " + choice);
				temp = foo.get(foo.size()-1);
                Data.get(choice).remove(foo.size()-1);
                

                //formatting PDF
                Section questionSubject = subjectChapter.addSection(new Paragraph(names[choice], bigTitle));
				if(useNames == 1) questionSubject.add(new Paragraph("Writer: " +  temp[1], title)); //name of the question writer 
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
