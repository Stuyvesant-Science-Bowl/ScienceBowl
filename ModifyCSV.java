import com.opencsv.CSVReader;
import java.io.*;
import java.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
public class ModifyCSV{

    public static void main(String [] args) throws FileNotFoundException {
        
		//Input to determine whether or not to display names!
		InputStreamReader isr;
		BufferedReader in;

		isr = new InputStreamReader( System.in );
		in = new BufferedReader( isr );
		
		List<List<String[]>> Data = new ArrayList<List<String[]>>();

        List<String[]> Physics = new ArrayList<String[]>();
        List<String[]> Mathematics = new ArrayList<String[]>();
        List<String[]> Biology = new ArrayList<String[]>();
        List<String[]> Chemistry = new ArrayList<String[]>();
        List<String[]> EarthSpace = new ArrayList<String[]>();
        List<String[]> Energy = new ArrayList<String[]>();


		//input

        String useNamesChoice;
		int useNames = -1; //whether or not to include names when listing quetions: -1 -> initialized, 0 -> no, 1 -> yes
		
		System.out.println("Do you wish to print names?");
		while (useNames < 0) {
			System.out.print("Please enter a valid answer (\"y\" or \"n\"): ");
			try {
				 useNamesChoice = in.readLine();
				 if(useNamesChoice.equals("n")) useNames = 0;
				 if(useNamesChoice.equals("y")) useNames = 1;

			}
			catch ( IOException e ) {useNames = -1;}
			catch ( NumberFormatException e) {useNames = -1;}
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


			
			PrintWriter pw = new PrintWriter(new File("data.csv"));
			StringBuilder sb = new StringBuilder();

            for(int j = 1; j <= roundNum; j++){




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
                
				sb.append("0: " + names[subject] + "\u0102");
				
				if(temp[3].equals("Multiple Choice") && temp[4].equals("Short Answer")){
                    //Toss Up Multiple Choice
                    sb.append("1: Toss Up | Multiple Choice: " +
                                "\t" + temp[6] + "\n\t\t" + 
                                "W) " + temp[7] + "\n\t\t" +
                                "X) " + temp[8] + "\n\t\t" +
                                "Y) " + temp[9] + "\n\t\t" +
                                "Z) " + temp[10] + "\u0102");
                    sb.append("2: Toss Up Answer: " + temp[11] + "\u0102");
                    //Bonus Short Answer
                    sb.append("3: Bonus | Short Answer: " + temp[12] + "\u0102");
                    sb.append("4: Bonus Answer: " + temp[13] + "\u0103");
                } else if(temp[3].equals("Multiple Choice") && temp[4].equals("Multiple Choice")){
                    //Toss Up Multiple Choice
                    sb.append("1: Toss Up | Multiple Choice: " + "\t" + temp[14] + "\n\t\t" + 
                                "W) " + temp[15] + "\n\t\t" +
                               "X) " + temp[16] + "\n\t\t" +
                                "Y) " + temp[17] + "\n\t\t" +
                                "Z) " + temp[18] + "\u0102");
                    sb.append("2: Toss Up Answer: " + temp[19] + "\u0102");
                    //Bonus Multiple Choice
                    sb.append("3: Bonus | Multiple Choice: " + "\t" + temp[20] + "\n\t\t" + 
                                "W) " + temp[21] + "\n\t\t" +
                                "X) " + temp[22] + "\n\t\t" +
                                "Y) " + temp[23] + "\n\t\t" +
                                "Z) " + temp[24] + "\u0102");
                    sb.append("4: Bonus Answer: " + temp[25] + "\u0103");
                } else if(temp[3].equals("Short Answer") && temp[5].equals("Multiple Choice")){
                    sb.append("1: Toss Up | Short Answer: " + temp[26] + "\u0102");
                    sb.append("2: Toss Up Answer: " + temp[27] + "\u0102");
                    //Bonus Multiple Choice
                    sb.append("3: Bonus | Multiple Choice: " + 
                                "\t" + temp[28] + "\n\t\t" + 
                                "W) " + temp[29] + "\n\t\t" +
                                "X) " + temp[30] + "\n\t\t" +
                                "Y) " + temp[31] + "\n\t\t" +
                                "Z) " + temp[32] + "\u0102");
                    sb.append("4: Bonus Answer: " + temp[33] + "\u0103");


                } else if(temp[3].equals("Short Answer") && temp[5].equals("Short Answer")){
                    //Toss Up Short Answer
                    sb.append("1: Toss Up | Short Answer: " +  temp[34] + "\u0102");
                    sb.append("2: Toss Up Answer: " + temp[35] + "\u0103");
                    //Bonus Short Answer
                    sb.append("3: Bonus | Short Answer: " + temp[36] + "\u0102");
                    sb.append("4: Bonus Answer: " + temp[37] + "\u0103");
                } else {
                    System.out.println("UH OH SOMETHING WENT WRONG at: " + i + "; 3: " + temp[3] + " 4: " + temp[4] + " 5: " + temp[5] );
                    for(int l=0; l<temp.length; l++){
                        System.out.println(temp[l]);
                    }

				
				}
			}
			}

		pw.write(sb.toString());
        pw.close();
        System.out.println("done!");
	
	}
}
