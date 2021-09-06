/**
 *  @author:    Doug Plager and Ajalon Corcoran
 *  @version:   5-5-2019
 * 
 *  Course:     CS341 - Data Structures
 *  Assignment: Final Project
 *  File Name:  PeptideSetClient.java
 *
 *  Purpose:   A main method class utilizing a "PeptideSet" abstract data type (ADT)
 *             to identify and work with peptides derived from a DNA gene sequence.
 *             
 *  Constants: None.
 *  Input:     An appropriately formatted .txt file containing a DNA gene sequence 
 *             of interest and an appropriatly formatted .txt file providing paired
 *             DNA codon ('key') and corresponding amino acid information ('value')
 *             for translating from DNA sequence to amino acid sequence.
 *  Output:    Lists of generated peptides based on user-input parameters and
 *             various associate information for the input gene sequence. 
 *
 *  Exceptions:   InputMismatchException, IOException, FileNotFoundException, 
 *                and IllegalArgumentException
 *  Associated Major Classes: PeptideSetInterface.java, PeptideSet.java, Peptide.java, 
 *                            and AminoAcid.java
 */ 
 
import java.util.*;  // Scanner & some Exceptions
import java.io.*;    // (1) Read & Write, FileI/O
import javax.swing.JFileChooser; // (A) Open & Save, JFileChooser

public class PeptideSetClient {

    public static void main(String[] args) throws IOException {  // (2) Read & Write, FileI/O

        // Variables and/or CONSTANTS
        Scanner keyboard = new Scanner(System.in);

        String ncbiId = "", geneName = "", codingSeq = "";
        String line;
        String codon, oneLtrName, threeLtrName, fullName, aaCharge;
        double molWeight;

        AminoAcid aminoAcid;

        LinkedHashMap<String, AminoAcid> codonTable = new LinkedHashMap<>();

        int peptideLength = 1, aminoAcidShift = 1;

        String startCodon, geneValidationInfo;

        int menuOption;
        String listOption, finalList;

        LinkedList<Peptide> peptides;

        Peptide tempPeptide;
        AminoAcid tempAminoAcid;
        String peptideAAs = "";

        String consensusSeq = "";

        // Read in NCBI DNA Coding Sequence (from .txt file)
/* TEST FILES: StaphAureusGene(test).txt; Desmoglein3(test).txt; or ZikaVirusGenome(test).txt */
/* HANDLING a non-compatible selected file?? */
        try {
            System.out.println("\nPlease select a properly formatted .txt file containing "
                    + "\nthe DNA coding sequence of interest.");

            String inFileName;      // (B) Open, JFileChooser

            JFileChooser openFile = new JFileChooser("../"); // (C) Open, JFileChooser
            // REM: "../" for up one (parent) directory;  WHAT for same directory?!
            // [ not ( ) or ( "/" ) or ( "" ) ]
            int status = openFile.showOpenDialog(null);      // (D) Open, JFileChooser

            if (status == JFileChooser.APPROVE_OPTION) {	   // (E) Open, JFileChooser	

                inFileName = openFile.getSelectedFile().getAbsolutePath();  // (F) Open, JFileChooser
            } else if (status == JFileChooser.CANCEL_OPTION) {
                return;  // “return” to exit the current method;  handles [Cancel] button  
            } else if (status == JFileChooser.ERROR_OPTION) {
                return;  // handles Exit/Close [X] button  
            } else {
                return;  // handles any other possibilities  
            }

            // Reading from the selected file
            FileReader fr = new FileReader(inFileName);   // (3) Read, FileI/O
            Scanner inFileCodingSeq = new Scanner(fr);    // (4) Read, FileI/O

            ncbiId = inFileCodingSeq.nextLine();   // captures first line of the .txt file
            geneName = inFileCodingSeq.nextLine(); // captures second line of the .txt file

            // (a) Read each line of the DNA coding sequence [while( --.hasNextLine()) / .nextLine() ] 
            //     and assign to a String variable;  (5) Read, FileI/O
            // (b) .replaceAll("\\W", "") [[remove white space and any punctuation]]
            // (c) .replaceAll("\\d", "") [[remove any digits]]
            // (d) concatenate each line to preceding collective string ( line += line; )
            while (inFileCodingSeq.hasNextLine()) {        // (5) Read, FileI/O

                line = inFileCodingSeq.nextLine();
                line = line.replaceAll("\\W", "");
                line = line.replaceAll("\\d", "");

                codingSeq += line;
            }

            inFileCodingSeq.close();                        // (6) Read, FileI/O

            // Write "trimmed" and condensed codingSeq to test file "TestCDS.txt"
            FileWriter fw = new FileWriter("TestCDS.txt");      // (3) Write
            PrintWriter outFileCodingSeq = new PrintWriter(fw, false);
            // (4) Write; overwriting

            outFileCodingSeq.println(codingSeq);                // (5) Write

            outFileCodingSeq.close();                             // (6) Write
        } /*REMOVE or CHANGE below 'catch' to something more appropriate??*/ 
        catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Please ensure that the file name for the designated "
                    + "\ngene coding sequence is correct and the file is accessible.");
        }

        // Read in Codon-AminoAcid object .txt file, then convert to 
        // LockedHashMap< String codon, AminoAcid aminoAcid > codonTable.
        // Include 'codonTable' as a component of PeptideSet object constructor.
           /*NOTE: Handle ATG vs atg*/
        try {
            FileReader fr2 = new FileReader("Codon-AAObjectPairs.txt");  // (3)
            Scanner inFileCodonTable = new Scanner(fr2);                 // (4)

            while (inFileCodonTable.hasNext()) {                         // (5)
                codon = inFileCodonTable.next();
                oneLtrName = inFileCodonTable.next();
                threeLtrName = inFileCodonTable.next();
                fullName = inFileCodonTable.next();
                molWeight = inFileCodonTable.nextDouble();
                aaCharge = inFileCodonTable.next();

                aminoAcid = new AminoAcid(oneLtrName, threeLtrName, fullName,
                        molWeight, aaCharge);

                // 'Map' codon 'key's to their respective 'AminoAcid' object 'value's
                codonTable.put(codon, aminoAcid);

            }  // end 'while'

   /*       // Checking 'codonTable' by outputting partial content
            for( Map.Entry<String, AminoAcid> codonKeyValue : codonTable.entrySet() ) {
               System.out.println( "Key: " + codonKeyValue.getKey() + "\tValue: " +
                  codonKeyValue.getValue().getThreeLtrName() );
            }
   */
            inFileCodonTable.close();     // (6)

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            System.out.println("Please ensure that the file name for the designated "
                    + "\ncodon table is correct and the file is accessible.");
        }

        // User input of desired peptide length and amino acid shift
        boolean flag1 = true;

        while (flag1) {
            try {
                System.out.print("\nHow many amino acids long would you like "
                        + "your peptides to be (e.g., 9)? ");
                peptideLength = keyboard.nextInt();
                System.out.print("\nHow many amino acids would you like to shift "
                        + "from one peptide to the next (e.g., 2)? ");
                aminoAcidShift = keyboard.nextInt();

                if (aminoAcidShift <= 1 || aminoAcidShift > peptideLength) {
                    throw new IllegalArgumentException();
                }

                flag1 = false;
            } catch (IllegalArgumentException e) {
                System.out.println("\nPlease enter a 'shift' value greater than 0 and "
                        + "< or = to your chosen peptide length.");
            } catch (InputMismatchException e) {
                keyboard.nextLine();
                System.out.println("\nPlease enter an integer for your chosen peptide "
                        + "length and 'shift' value.");
            }

        }

        // Create PeptideSet "partial" object ("LinkedList<Peptide> peptides" yet to be populated)
        PeptideSet peptideSet1 = new PeptideSet(ncbiId, geneName, codingSeq, codonTable,
                peptideLength, aminoAcidShift);
        

        while (true) {
            // User input of expected start codon for the input gene coding sequence
            System.out.print("\nWhat is the expected start codon for this gene (atg, ttg, or ctg)? ");
            startCodon = keyboard.next().toLowerCase();
            /* NOTE: Include the expected startCodon as a data field of PeptideSet??!! */
            if (startCodon.equals("ctg") || startCodon.equals("atg") || startCodon.equals("ttg")) {
                break;
            }
        }
        // MENU OF CHOICES
        boolean menuFlag = true;

        while (menuFlag) {
            menuOption = menu();

            switch (menuOption) {

                case 1:  // Validate DNA coding sequence

                    // Find start and in-frame stop codon and total Nts, inclusive,
                    // and return as a String
                    geneValidationInfo = peptideSet1.validateCodingSeq(startCodon);
                    System.out.println("\nNCBI Identifier: " + peptideSet1.getNCBI_Id()
                            + "\nCoding sequence name: " + peptideSet1.getGeneName()
                            + "\n" + geneValidationInfo);

                    break;

                case 2:  // Generate peptides translated from the DNA coding sequence

                    // Generate peptides across the gene coding sequence;
                    // LL<Peptide> returned (peptides w/o MWs or Priorities at this point)
                    peptides = peptideSet1.generatePeptides(startCodon);

                    System.out.println("\nGenerated " + peptides.size() + " peptides.");

                    break;

                case 3:  // Calculate and set the molecular weight for each peptide

                    // Calculate and 'set' the MWs of generated peptides
                    peptideSet1.calcMWsOfPeptides();
                    System.out.println("\nPeptide MWs calculated and set.");

                    break;

                case 4:  // Calculate and set the priority score for each peptide
                    boolean temp = true;
                    while (temp) {
                        try {
                            keyboard.nextLine();  // consuming a residual 'newline' character,
                            // BUT FROM WHERE??!!
                            consensusSeq = "";
                            /* Put within 'try' and 'catch' IllegalArgumentException */
                            System.out.println("\nPlease enter a comma-separated series of the consensus peptide's amino acids' "
                                    + "\ncharge properties (np = nonpolar, p = polar, + = positive, or - = negative)."
                                    + "\n\nFor example,  +,-,np,np,np,p,-,p,+,p  for 10-amino acid peptides: ");

                            consensusSeq = keyboard.nextLine();
                            // Is a 'newline' character causing trouble??
                            // Changing from .println to .print just above did not matter.

                /*          System.out.print(consensusSeq);  */  // just test print out
                            peptideSet1.calcPrioritiesOfPeptides(consensusSeq);

                            System.out.println("\nPeptide priority scores calculated and set.");
                            temp = false;
                        } catch (IllegalArgumentException e) {
                            System.out.println("\n" + e.getMessage());
                            temp = true;
                        }
                    }
                    break;

                case 5:  // List the full, generated/sorted LL<Peptide> 'peptides' data field (OPTION A)
                         // or the MW/PS LL<Peptide> 'peptideSearch' data field (OPTION B)
                         // [[ vs. listing a passed, stand-alone LL<Peptide> peptideList, e.g., after a search ]]
                    while (true) {
                        System.out.print("\nDo you want to list: "
                                + "\n\tA -- the full set of unsorted or sorted peptides, or"
                                + "\n\tB -- the current partial set of searched-for peptides?"
                                + "\nPlease enter 'A' or 'B': ");
                        listOption = keyboard.next().toUpperCase();

                        if (listOption.equals("A")) {
                            //finalList = peptideSet1.listPeptides();
                            //System.out.println("\n" + finalList);
                            System.out.println("\n" + peptideSet1.listPeptides());
                            break;
                        } else if (listOption.equals("B")) {
                            try {
                                System.out.println("\n" + peptideSet1.listPeptidesB());
                            } catch (NullPointerException e) {
                                System.out.println("Array is not populated select 8 or 9 in the menu to populate");

                            }
                            break;
                        } else {
                            System.out.println("\nPlease try again and enter 'A' or 'B'.");
                        }

                    }
                    break;
                    
                case 6:
                    peptideSet1.peptideMWSort();
                    System.out.println("\nSelect option 5 to view peptide sort.");
                    break;
                    
                case 7:
                    boolean flag2 = true;
                    while (flag2) {
                        try {
                            keyboard.nextLine();  // consuming a residual 'newline' character,
                            // BUT FROM WHERE??!!

                            /* Put within 'try' and 'catch' IllegalArgumentException */
                            System.out.println("\nPlease enter a comma-separated series of the consensus peptide's amino acids' "
                                    + "\ncharge properties (np = nonpolar, p = polar, + = positive, or - = negative)."
                                    + "\n\nFor example,  +,-,np,np,np,p,-,p,+,p  for 10-amino acid peptides: ");

                            consensusSeq = keyboard.nextLine();
                            // Is a 'newline' character causing trouble??
                            // Changing from .println to .print just above did not matter.

                            peptideSet1.calcPrioritiesOfPeptides(consensusSeq);

                            System.out.println("\nPeptide priority scores calculated and set.");
                            flag2 = false;
                        } catch (IllegalArgumentException e) {
                            System.out.println("\n" + e.getMessage());
                            flag2 = true;
                        }

                        peptideSet1.peptidePrioritySort();

                    }
                    System.out.println("\nSelect option 5 to view peptide sort.");
           /*       System.out.println(" ");  */
                    break;
                    
                case 8:
                    //boolean flag3 = true;
                    while (true) {
                        try {
           /*               System.out.println("press enter to start:");  */

                            keyboard.nextLine();
                            double maxMW = 0.0, minMW = 0.0;
                            String temp7;
                            System.out.print("\nPlease enter maximum MW expected: ");

                            //maxMW = keyboard.nextDouble();
                            temp7 = keyboard.nextLine();
                            maxMW = Double.parseDouble(temp7);
                            //System.out.print(maxMW);
                            System.out.print("Please enter minimum MW expected: ");
                            minMW = keyboard.nextDouble();
                            //System.out.print(minMW);
                            peptideSet1.setPeptideSearch(peptideSet1.peptideMWSearch(minMW, maxMW));
                            System.out.println("\nSelect option 5 to view peptide MW search.");
                            break; //flag3 = false;
                        } catch (java.util.InputMismatchException e) {
                            System.out.println("\nPlease enter a double for the max and min values.");
                            System.out.println(" ");
                            continue;
                        } catch (NumberFormatException e) {
                            System.out.println("\nPlease enter a double for the max and min values.");
                        }

                    }
                    break;

                case 9:
                    boolean flag4 = true;
                    flag2 = true;
                    String temp2 = "";
                    while (flag2) {
                        try {
                            keyboard.nextLine();  // consuming a residual 'newline' character,
                            // BUT FROM WHERE??!!
                            
                            System.out.println("\nPlease enter a comma-separated series of the consensus peptide's amino acids' "
                                    + "\ncharge properties (np = nonpolar, p = polar, + = positive, or - = negative)."
                                    + "\n\nFor example,  +,-,np,np,np,p,-,p,+,p  for 10-amino acid peptides: ");

                            consensusSeq = keyboard.nextLine();

                            peptideSet1.calcPrioritiesOfPeptides(consensusSeq);

                            System.out.println("\nPeptide priority scores calculated and set.");
                            flag2 = false;
                        } catch (IllegalArgumentException e) {
                            System.out.println("\n" + e.getMessage());
                            flag2 = true;
                        }
                    }
                        while (flag4) {
                            try {
                                //keyboard.nextLine();
                                int maxPS = 0, minPS = 0;
                                System.out.print("\nPlease enter the maximum desired peptide priority score: ");
                                temp2 = keyboard.nextLine();
                                maxPS = Integer.parseInt(temp2);
                                System.out.print("Please enter the minimum desired peptide priority score: ");
                                minPS = keyboard.nextInt();
                                peptideSet1.setPeptideSearch(peptideSet1.peptidePrioritySearch(minPS, maxPS));
                                System.out.println("\nSelect option 5 to view peptide Priority Score search.");
                                flag4 = false;
                            } catch (InputMismatchException e) {
                                System.out.println("\nPlease enter the correct values.");
                                System.out.println(" ");
                            } catch (NumberFormatException e) {
                                System.out.println("\nPlease enter the correct values.");
                                System.out.println(" ");
                            }
                        }
                        break;      
            
                case 10:
                    peptideSet1.peptideReturnSort();
                    System.out.println("\nSelect option 5 to view.");

                    break;
                    
                case 0:  // Exit
                    menuFlag = false;
                    break;

                default:
                    System.out.println("\nPlease enter a valid numeric choice.");
                    break;

            } // end switch

        } // end while

    } // end main

    

    public static int menu() {

        Scanner keyboard = new Scanner(System.in);
        int option = 111;    // just initializing 'option' variable with 111

        try {
            System.out.print("\n\nDNA Coding Sequence Menu: "
                    + "\n\t1 -- Validate coding sequence"
                    + "\n\t2 -- Generate peptides "
                    + "\n\t3 -- Calculate molecular weights(MWs) of peptides "
                    + "\n\t4 -- Calculate priority scores of peptides "
                    + "\n\t5 -- List peptides "
                    + "\n\t6 -- Sort peptides by MW "
                    + "\n\t7 -- Sort peptides by priority score "
                    + "\n\t8 -- Search for peptides by MW "
                    + "\n\t9 -- Search for peptides by priority score "
                    + "\n\t10 -- Return to full peptide set ordered by start position"
                    + "\n\t0 -- Exit "
                    + "\nPlease enter your choice: ");
            option = keyboard.nextInt();
            keyboard.nextLine();    // consuming residual newline
        } catch (InputMismatchException e) {
            System.out.println("\nPlease enter a valid numeric choice.");
        }

        return option;
    }

} // end class
