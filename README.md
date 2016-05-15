# Science Bowl - Brief Description
We use a google form to collect questions from our team members. These questions
are fed and stored on a google spreadsheet like with normal google forms. Then
we download the google spreadsheet as a csv file and name it "Questions.csv"

This code then parses the csv Data and creates 6 PDFs and stores them in the PDF directory. These PDFs are formatted collections of the questions that we collected and stored in our csv file through gogole forms. 

This allows for an easy system to keep track of submissions through the form
summaries on google forms, and then seamlessly create uniformly formatted PDFs
of the different categories. You can just copy and past from these PDFs to
create rounds. 

#Running the Code
There is a bash scrip nameked MakeRounds.sh on the top level of this repo. To
run it do this in your terminal:
```
./MakeRounds.sh
```

#Setting up the Google Form
Here is the form we used. If you want this program to work, the form must have
the exact same order as this one:
[Form](https://docs.google.com/forms/d/1S3mKHc_Qs65-kIGPR6w_ELu06LeUKPCd9dBREdLVSJw/viewform?fbzx=2271745987249313300)

Also, here is the how the spreadsheet should look like:
[Spreadsheet](https://docs.google.com/spreadsheets/d/1YUfQR5MfDDLHuljZvPGnid8iiYveHVcOK2-H-DJREAg/edit?usp=sharing)

Here are some images that contain each part of the form to help recreate this
type of form - remember to include everything: 


![Alt text](form/1.png?raw=true "Format")
![Alt text](form/2.png?raw=true "Format")
![Alt text](form/3.png?raw=true "Format")
![Alt text](form/4.png?raw=true "Format")
![Alt text](form/5.png?raw=true "Format")
![Alt text](form/6.png?raw=true "Format")
![Alt text](form/7.png?raw=true "Format")
![Alt text](form/8.png?raw=true "Format")
![Alt text](form/9.png?raw=true "Format")
![Alt text](form/10.png?raw=true "Format")
![Alt text](form/11.png?raw=true "Format")
![Alt text](form/12.png?raw=true "Format")
![Alt text](form/13.png?raw=true "Format")
![Alt text](form/14.png?raw=true "Format")
![Alt text](form/15.png?raw=true "Format")
![Alt text](form/16.png?raw=true "Format")

END OF FORM

#Author
[Shantanu Jha](https://github.com/Phionx/)
President: [Stuyvesant Science Bowl](https://sites.google.com/site/stuyvesantsciencebowl/)
