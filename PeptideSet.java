/**
 *  @author:    Doug Plager and Ajalon Corcoran
 *  @version:   5-5-2019
 * 
 *  Course:     CS341 - Data Structures
 *  Assignment: Final Project
 *  File Name:  PeptideSet.java
 *
 *  Purpose:   An implementing class of a "PeptideSet" abstract data type (ADT)
 *             that implements several abstract methods of the PeptideSetInterface,
 *             which are important for identifying and working with peptides
 *             derived from a gene sequence.
 *  Constants: None.
 *  Input:     None.
 *  Output:    None.
 *
 *  Exceptions:   [[IllegalArgumentException, OTHERS??]]
 *  Associated Major Classes: PeptideSetInterface.java, Peptide.java, AminoAcid.java, 
 *                            PeptideSetClient.java
 */ 

import java.util.*;

public class PeptideSet implements PeptideSetInterface {

    // Data fields and/or CONSTANTS
    private String ncbiId;        // National Center for Biotechnology Information identifier

    private String geneName;      // name of the gene from which the set of peptides was generated

    private String codingSeq;     // DNA coding sequence of the designated gene

    private LinkedHashMap<String, AminoAcid> dnaCodonTable;  // rapid lookup 'map' for 
         // translating each DNA codon 'key' to its corresponding AminoAcid object 'value'
/* NOTE: Seems like dnaCodonTable might be more 'static' than non-static (object-assoc'd) in nature */

    private int peptideLength;    // user-input length of peptides to be generated
         // Invariant: peptideLength >=1 and < encoded protein length.

    private int aminoAcidShift;    // user-input number of amino acids to advance from one peptide to the next
         // Invariant: 0 < aminoAcidStep <= peptideLength value.

    private LinkedList<Peptide> peptides;  // collection of the peptides generated from
         // the designated gene's coding sequence

    private LinkedList<Peptide> peptideSearch;

    // Constructor(s)
    public PeptideSet() {

        ncbiId = "unknown";
        geneName = "unknown";
        codingSeq = "unknown";
        dnaCodonTable = new LinkedHashMap<String, AminoAcid>();
        peptideLength = 1;
        aminoAcidShift = 1;
        peptides = new LinkedList<Peptide>();
        peptideSearch = new LinkedList<Peptide>();
    }

    public PeptideSet(String ncbiId, String geneName, String codingSeq,
            LinkedHashMap<String, AminoAcid> dnaCodonTable, int peptideLength,
            int aminoAcidShift) {

        this.ncbiId = ncbiId;   // read in these three from one of the gene sequence .txt files
        this.geneName = geneName;
        this.codingSeq = codingSeq;

        this.dnaCodonTable = dnaCodonTable;  // read in from "Codon-AminoAcidPairs.txt"

        this.peptideLength = peptideLength;
        this.aminoAcidShift = aminoAcidShift;
        peptides = new LinkedList<Peptide>();
        peptideSearch = new LinkedList<Peptide>();
    }

    public PeptideSet(String ncbiId, String geneName, String codingSeq, LinkedHashMap<String, AminoAcid> dnaCodonTable, int peptideLength, int aminoAcidShift, LinkedList<Peptide> peptides, LinkedList<Peptide> peptideSerch) {
        this.ncbiId = ncbiId;
        this.geneName = geneName;
        this.codingSeq = codingSeq;
        this.dnaCodonTable = dnaCodonTable;
        this.peptideLength = peptideLength;
        this.aminoAcidShift = aminoAcidShift;
        this.peptides = peptides;
        this.peptideSearch = peptideSerch;
    }

    // Getters and Setters
    public String getNCBI_Id() {
        return ncbiId;
    }

    public void setNCBI_Id(String ncbiId) {
        this.ncbiId = ncbiId;
    }

    public String getGeneName() {
        return geneName;
    }

    public void setGeneName(String geneName) {
        this.geneName = geneName;
    }

    public String getCodingSeq() {
        return codingSeq;
    }

    public void setCodingSeq(String codingSeq) {
        this.codingSeq = codingSeq;
    }

    public int getPeptideLength() {
        return peptideLength;
    }

    public void setPeptideLength(int peptideLength) {
        this.peptideLength = peptideLength;
    }

    public int getAminoAcidShift() {
        return aminoAcidShift;
    }

    public void setAminoAcidShift(int aminoAcidShift) {
        this.aminoAcidShift = aminoAcidShift;
    }

    public LinkedList<Peptide> getPeptides(){
        return peptides;
    }
    
    public LinkedList<Peptide> getPeptideSearch() {
        return peptideSearch;
    }

    public void setPeptideSearch(LinkedList<Peptide> peptideSerch) {
        this.peptideSearch = peptideSerch;
    }

    // toString()
    @Override
    public String toString() {
        return "\nNCBI Identifier: " + ncbiId
                + "\nGene/Protein Name: " + geneName
                + "\nUser-input Peptide Length: " + peptideLength + " amino acids"
                + "\nUser-input Amino Acid Shift: " + aminoAcidShift + " amino acids";
    }

    // Other Interface Methods
    public String validateCodingSeq(String startCodon) {

        // Identify the index of the expected "start" codon;  
        // NOTE: -1 returned if start codon not found.
        int startIndex = codingSeq.indexOf(startCodon);

        if (startIndex == -1) {   // 'startCodon' not found in 'codingSeq'
            return "Expected start codon, " + startCodon + ", not found.";
        }

        int startNt = startIndex + 1; // corresponding nt number if startIndex found 

        // Check subsequent three-nt, in-frame codons for a stop codon (taa, tag, or tga)
        int tempIndex = startIndex;
        int endIndex, endNt, totalNts;
        String tempCodon;
        boolean flag1 = true;

        while (flag1 && tempIndex < (codingSeq.length() - 3)) {
            tempIndex += 3;
            tempCodon = codingSeq.substring(tempIndex, tempIndex + 3).toLowerCase();

            if (tempCodon.equals("taa") || tempCodon.equals("tag")
                    || tempCodon.equals("tga")) {
                endIndex = tempIndex + 2;
                endNt = endIndex + 1;

                totalNts = endNt - startNt + 1;

                if (totalNts % 3 != 0) {
                    return "\nImproper length for the actual coding sequence (i.e., not divisible by 3)";
                }

                return "\nStart Nt#: " + startNt
                        + "\nEnd Nt#:   " + endNt
                        + "\nTotal Nt#: " + totalNts
                        + "\nStop codon: " + tempCodon;
            }

        }

        return "No stop codon found.";  // exit program from Client menu
    }

    @Override
    public LinkedList<Peptide> generatePeptides(String startCodon) {
    /*NOTE: RECURSIVE form of generatePeptides( int peptideStartNtIndex ) seems feasible*/

        int startIndex = codingSeq.indexOf(startCodon);

        // If startCodon not found; (-1) returned
        int tempIndex;
        String tempCodon = "";
        AminoAcid tempAA;
        ArrayList<AminoAcid> tempPeptide;
        Peptide peptide;
        /*    LinkedList<Peptide> peptides = new LinkedList<>();  */  // REM: available from calling PeptideSet object

        /*    for( index = startIndex; index <= (endIndex - (peptideLength * 3));
                  index + (aminoAcidShift * 3) ) {  // go until last full-length peptide 
        */
        
        // Generating peptides until the last full-length peptide 
        // (NOTE: may also want to capture some peptides back from the C-terminus)
        for (int j = startIndex; j < codingSeq.length(); j += (aminoAcidShift * 3)) {
            // go until stop codon reached [[handling absence of stop codon]]
            tempIndex = j;

            tempPeptide = new ArrayList<>(peptideLength);

            for (int i = 1; i <= peptideLength; i++) {

                tempCodon = codingSeq.substring(tempIndex, tempIndex + 3).toUpperCase();

                if (tempCodon.equals("TAA") || tempCodon.equals("TAG")
                        || tempCodon.equals("TGA")) {
                    break;
                }

                // Get amino acid object 'value' associated with the tempCodon 'key' of
                // the LinkedHashMap 'codonTable'
                tempAA = dnaCodonTable.get(tempCodon);

                // Add aminoAcidPosition and codonKey to tempAA object w/ Setters [[undone]]

                // Add the AminoAcid object to the end of 'peptide' ArrayList
                tempPeptide.add(tempAA);

                tempIndex += 3;

            // [[StringIndexOutOfBoundsException if stop codon not encountered??]]
            }

            if (tempCodon.equals("TAA") || tempCodon.equals("TAG")
                    || tempCodon.equals("TGA")) {
                break;
            }

            peptide = new Peptide(j + 1, tempPeptide);

            peptides.add(peptide);
            // REM: 'peptides' is a LL<Peptide> data field of a PeptideSet object.

        }

        return peptides;
    }

    
    public void calcMWsOfPeptides() {

        double peptideMW;

        for (Peptide eachPeptide : peptides) { // each peptide of the 'peptides' LL<Peptide>
            peptideMW = eachPeptide.calcPeptideMW();  // calculate each peptide's MW
            eachPeptide.setPeptideMW(peptideMW);    // 'set' each peptide's MW in 'peptides' LL<Peptide>
        }
    }

    public void calcPrioritiesOfPeptides(String consensusSeq) throws IllegalArgumentException {

        int peptidePriority;
        boolean bool = true;

        String[] consensusArray = consensusSeq.replaceAll("\\s", "").split(",");
        //System.out.print(consensusArray.toString());
        while (bool) {
            // Check that consensusArray is the same length as the calling object's
            // 'peptideLength'
            if (!(consensusArray.length == peptideLength)) {
                throw new IllegalArgumentException("The entered 'consensus sequence' length "
                        + "does not match the length of each peptide in this set.");
            }

            // Check that each element of 'consensusArray' is either np, p, +, or -;
            // Enumeration set of np, p, +, or -, perhaps??
            for (String charge : consensusArray) {
                if (charge.equals("np") || charge.equals("p") || charge.equals("+") || charge.equals("-")) {
                    if(!(consensusArray.length == peptideLength-1)){
                    bool = false;
                    continue;
                }}
                throw new IllegalArgumentException("Invalid amino acid charge type (i.e., "
                        + "not np, p, +, or -).");
            }

            for (Peptide eachPeptide : peptides) { // each peptide of the 'peptides' LL<Peptide>
                peptidePriority = eachPeptide.calcPeptidePriority(consensusArray);  // calculate each peptide's MW
                eachPeptide.setPeptidePriority(peptidePriority);    // 'set' each peptide's priority score in 'peptides' LL<Peptide>
            }  // NOTE: returned -222 peptidePriority is an error signal.
        }
    }

    public void peptideMWSort() {

        calcMWsOfPeptides();

        Collections.sort(peptides, (Peptide o1, Peptide o2) -> {
            if (o1.getPeptideMW() < o2.getPeptideMW()) {
                return 1;
            }
            if (o1.getPeptideMW() > o2.getPeptideMW()) {
                return -1;
            } else {
                return 0;
            }
        });

    }

    public void peptidePrioritySort() {
        Collections.sort(peptides, (Peptide o1, Peptide o2) -> {
            if (o1.getPeptidePriority() < o2.getPeptidePriority()) {
                return 1;
            }
            if (o1.getPeptidePriority() > o2.getPeptidePriority()) {
                return -1;
            } else {
                return 0;
            }
        });

    }

    public void peptideReturnSort() {
        Collections.sort(peptides, (Peptide o1, Peptide o2) -> {
            if (o1.getStartNt() > o2.getStartNt()) {
                return 1;
            }
            if (o1.getStartNt() < o2.getStartNt()) {
                return -1;
            } else {
                return 0;
            }
        });
    }

    public LinkedList<Peptide> peptideMWSearch(double lowMW, double highMW) {
        peptideMWSort();  // probably not necessary
        LinkedList<Peptide> temp = new LinkedList<>();
        //System.out.println(peptides.size());
        //System.out.println(lowMW + " \n" + highMW);
        for (int i = 0; i < peptides.size(); i++) {

            if (peptides.get(i).getPeptideMW() >= lowMW & peptides.get(i).getPeptideMW() <= highMW) {
                temp.add(peptides.get(i));

            }
        }
        return temp;
    }

    public LinkedList<Peptide> peptidePrioritySearch(int lowPS, int highPS) {//don't need return
        peptidePrioritySort();  // probably not necessary
        LinkedList<Peptide> temp = new LinkedList<>();
        for (int i = 0; i < peptides.size(); i++) {
            if (peptides.get(i).getPeptidePriority() >= lowPS & peptides.get(i).getPeptidePriority() <= highPS) {
                temp.add(peptides.get(i));

            }

        }

        return temp;   // 'searchResultPeptides' in PeptideSetClient
    }

    public String listPeptides() {   // OPTION A:  Instance method using  
                                     // 'peptides' LL<Peptide> data field 
        Peptide tempPeptide;
        AminoAcid tempAminoAcid;
        String peptideAAs = "";

        String finalList = "";

/* NOTE: Is index-position-based stepping through LinkedList inefficient?? */
        int count1 = 1;
        for (int j = 0; j < peptides.size(); j++) {
            tempPeptide = peptides.get(j);   // 'tempPeptide' is a Peptide object

            for (int i = 0; i < tempPeptide.getPeptide().size(); i++) {
                // .getPeptide() returns ArrayList<AminoAcid> of Peptide object
                tempAminoAcid = tempPeptide.getPeptide().get(i);
                peptideAAs += tempAminoAcid.getOneLtrName();  // building one-letter amino acid string for each peptide
            }

            finalList += "\npeptide #" + count1 + ": " + peptideAAs
                    + "    Start Nt: " + peptides.get(j).getStartNt()
                    + "    MW: " + String.format("%.1f", peptides.get(j).getPeptideMW())
                    + "    Priority:  " + peptides.get(j).getPeptidePriority();

            peptideAAs = "";
            count1++;
        }

        return finalList;
    }

    public String listPeptidesB() {  // OPTION B:  PeptideSet-object called instance
                              // method using 'peptideSearch' LL<Peptide> data field 
        
        Peptide tempPeptide;
        AminoAcid tempAminoAcid;
        String peptideAAs = "";

        String finalList = "";

  /*NOTE: Is index-position-based stepping through LinkedList inefficient?? */
        int count1 = 1;
        for (int j = 0; j < peptideSearch.size(); j++) {
            tempPeptide = peptideSearch.get(j);   // 'tempPeptide' is a Peptide object

            for (int i = 0; i < tempPeptide.getPeptide().size(); i++) {
                // .getPeptide() returns ArrayList<AminoAcid> of Peptide object
                tempAminoAcid = tempPeptide.getPeptide().get(i);
                peptideAAs += tempAminoAcid.getOneLtrName();  // building one-letter amino acid string for each peptide
            }

            finalList += "\npeptide #" + count1 + ": " + peptideAAs
                    + "    Start Nt: " + peptideSearch.get(j).getStartNt()
                    + "    MW: " + String.format("%.1f", peptideSearch.get(j).getPeptideMW())
                    + "    Priority:  " + peptideSearch.get(j).getPeptidePriority();

            peptideAAs = "";
            count1++;
        }

        return finalList;
    }

}  // end class
