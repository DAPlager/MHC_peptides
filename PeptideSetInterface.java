/**
 *  @author:    Doug Plager and Ajalon Corcoran
 *  @version:   5-5-2019
 * 
 *  Course:     CS341 - Data Structures
 *  Assignment: Final Project
 *  File Name:  PeptideSetInterface.java
 *
 *  Purpose:   An interface component of a "PeptideSet" abstract data type (ADT)
 *             that defines several abstract methods important for identifying and
 *             working with peptides derived from a gene sequence.
 *  Constants: None.
 *  Input:     None.
 *  Output:    None.
 *
 *  Exceptions:   [[IllegalArgumentException, OTHERS??]]
 *  Associated Major Classes: PeptideSet.java, Peptide.java, AminoAcid.java, 
 *                            PeptideSetClient.java
 */ 
 
// Package

// Imports
import java.util.*;  // for LinkedList<E>


public interface PeptideSetInterface {

   /**
    *  Purpose: Retrieves the National Center for Biotechnology Information (NCBI)
    *           identifier information associated with the calling peptide set object.     
    *  @ensure Returns the NCBI gene identifier information associated with the 
    *          calling peptide set object. 
    *  @return String, NCBI gene identifier information.
    */
   public String getNCBI_Id(); 

   /**
    *  Purpose: Sets the NCBI identifier field associated with the calling peptide set object.
    *  @param String ncbiId, NCBI gene identifier information. 
    *  @require Calling peptide set object should have an appropriate identifying information 
    *           field that can be set by this method.
    *  @ensure The identifying information field is changed to the String value passed
    *          to the 'ncbiId' parameter.
    */
   public void setNCBI_Id( String ncbiId ); 
   
   /**
    *  Purpose: Retrieves the National Center for Biotechnology Information (NCBI)
    *           gene name information associated with the calling peptide set object.
    *  @ensure Returns the NCBI gene name information associated with the 
    *          calling peptide set object.
    *  @return String, gene name information.
    */
   public String getGeneName(); 
   
   /**
    *  Purpose: Sets the gene name field associated with the calling peptide set object.
    *  @param String geneName, gene name information. 
    *  @require Calling peptide set object should have an appropriate gene name 
    *           field that can be set by this method.
    *  @ensure The gene name field is changed to the String value passed
    *          to the 'geneName' parameter.
    */
   public void setGeneName( String geneName ); 
   
   /**
    *  Purpose: Retrieves the 5' to 3' single-letter nucleotide sequence associated
    *           with the calling peptide set object.
    *  @ensure Returns the 5' to 3' single-letter nucleotide sequence associated with 
    *          the calling peptide set object.
    *  @return String, 5' to 3' consecutive nucleotide sequence (e.g., of a, g, t, 
    *          or c letters).
    */
   public String getCodingSeq();  
   
   /**
    *  Purpose: Sets the 5' to 3' single-letter nucleotide coding sequence field 
    *           associated with the calling peptide set object.
    *  @param String codingSeq, a consecutive single-letter nucleotide sequence.
    *  @require Calling peptide set object should have an appropriate coding sequence
    *           field that can be set by this method with a consecutive 5' to 3'
    *           single-letter nucleotide sequence string (e.g., of a, g, t, or 
    *           c letters).
    *  @ensure The coding sequence field is changed to the String value passed to
    *          the 'codingSeq' parameter.
    *  @throws [[while our .txt file read-in handles the likely anomalies for such 
    *            a coding sequence, this (currently unused) method does not attempt
    *            to handle sequence anomalies such as a non-a, g, t, or c letter.]]
    */
   public void setCodingSeq( String codingSeq ); 
   

// The following getter and setter deemed to be unnecessary components of this interface   
/*
   public LinkedHashMap<String, AminoAcid> getDNACodonTable();
   
   public void setDNACodonTable( LinkedHashMap<String, AminoAcid> dnaCodonTable ); 
*/
   
   /**
    *  Purpose: Retrieves the user-input peptide length associated with the calling 
    *           peptide set object.
    *  @ensure Returns the user-input int value of the peptide length field of the
    *          calling peptide set object.
    *  @return int, designated peptide length.
    */
   public int getPeptideLength(); 
   
   /**
    *  Purpose: Sets the peptide length field associated with the calling peptide
    *           set object.
    *  @param int peptideLength, user-designated length for the peptides of the 
    *         calling peptide set.
    *  @require The user-designated peptide length should match that of the previously
    *           generated peptides or a new set of peptides of the designated length
    *           should be re-generated.  0 < peptideLength < full protein length.
    *  @ensure The peptide length field is changed to the int value passed to the
    *          'peptideLength' parameter.
    *  @throws [[could throw an Exception if not 0 < peptideLength < full protein length]]
    */
   public void setPeptideLength( int peptideLength );
   
   /**
    *  Purpose: Retrieves the user-input amino acid shift associated with the calling 
    *           peptide set object.
    *  @ensure Returns the user-input int value of the amino acid shift field of the
    *          calling peptide set object.
    *  @return int, designated amino acid shift from one peptide to the next.
    */
   public int getAminoAcidShift();
   
   /**
    *  Purpose: Sets the amino acid shift field associated with the calling peptide
    *           set object.
    *  @param int aminoAcidShift, user-designated amino acid shift from one peptide 
    *         to the next of the calling peptide set.
    *  @require The user-designated amino acid shift should match that of the previously
    *           generated peptides or a new set of peptides with the designated 
    *           amino acid shift should be re-generated.  
    *           0 < aminoAcidShift <= peptideLength.
    *  @ensure The amino acid shift field is changed to the int value passed to the
    *          'aminoAcidShift' parameter.
    *  @throws [[could throw an Exception if not 0 < aminoAcidShift <= peptideLength;
    *            partially handled with an IllegalArgumentException in PeptideSetClient]]
    */
   public void setAminoAcidShift( int aminoAcidShift );
   
   /**
    *  Purpose: Retrieves the collection of peptides generated from the coding
    *           sequence based on the user-designated peptide length and 
    *           amino acid shift.
    *  @ensure Returns the calling object's field value that stores the collection of 
    *          peptides generated by the generatePeptides(_) method.
    *  @return LinkedList<Peptide>, the collection of Peptide objects, each Peptide
    *          object representing one of the generated peptides, from the calling
    *          peptide set object.
    */
   public LinkedList<Peptide> getPeptides();
   

// The following setter deemed to be an unnecessary component of this interface   
/*
   public void setPeptides( LinkedList<Peptides> );
*/

   /**
    *  Purpose: Retrieves the current collection of peptides generated from the coding
    *           sequence and limited according to peptide MW or PS.
    *  @ensure Returns the calling object's field value that stores the collection of 
    *          peptides limited by peptide MW or PS.
    *  @return LinkedList<Peptide>, the collection of Peptide objects, each Peptide
    *          object representing one of the searched-for peptides, from the calling
    *          peptide set object.
    */
   public LinkedList<Peptide> getPeptideSearch();
   
   /**
    *  Purpose: Sets the searched-for peptide field associated with the calling 
    *           peptide set object.
    *  @param LinkedList<Peptide> peptideSerch, a linked list of Peptide objects
    *         from the full list of generated peptides after limiting to a 
    *         user-designated MW or PS range.
    *  @require The passed argument is of LinkedList<Peptide> type.
    *  @ensure The passed range-limited collection of peptides should be assigned to  
    *          the appropriate field of the calling peptide set object.
    */
   public void setPeptideSearch(LinkedList<Peptide> peptideSerch);

   /**
    *  Purpose: Retrieves the basic data field information associated with the 
    *           calling peptide set object.
    *  @ensure Returns a single string containing the four values for the NCBI identifier, 
    *          gene name, peptide length, and amino acid shift fields associated with the 
    *          calling peptide set object.
    *  @return String, basic information about the calling peptide set object.
    */
   public String toString();

   /**
    *  Purpose: Identifies the anticipated start codon, its associated "in-frame"
    *           stop codon, and the overall nucleotide (nt) length from the first 
    *           nt of the start codon to the last nt of the stop codon of 
    *           the coding sequence associated with the calling peptide set object.
    *  @param String startCodon, String of the anticipated start codon (atg, ttg, or ctg).
    *  @require The calling peptide set object should contain an appropriately
    *           formatted coding sequence 
    *           and the user-input startCodon == atg, ttg, or ctg.
    *  @ensure Returns a single string containing the nt number of the coding sequence
    *          for the identified first nt of the start codon and for the identified 
    *          last nt of the stop codon, the overall nt length, and the three-letter
    *          nt codon of the identified stop codon associated with the
    *          calling peptide set object.
    *  @return String, string of start nt#, end nt#, total nt#, and stop codon.
    *  @throws [[handling of no coding sequence; invalid or absent start codon; 
    *            no stop codon]]
    */
   public String validateCodingSeq( String startCodon );

   /**
    *  Purpose: Generates a collection of peptides translated from the calling object's
    *           coding sequence based on the anticipated start codon and user-set peptide 
    *           length and amino acid shift.
    *  @param String startCodon, String of the anticipated start codon (atg, ttg, or ctg).
    *  @require The calling peptide set object should contain user-set peptide length
    *           and amino acid shift and an appropriately formatted coding sequence
    *           and codon table for translating each potential codon to its
    *           corresponding amino acid. 
    *           The user-input startCodon == atg, ttg, or ctg.
    *  @ensure Returns the collection of generated peptides and assigns this collection
    *          of peptides to a field of the calling peptide set object.
    *  @return LinkedList<Peptide>, collection of user-defined peptides.
    *  @throws [[handling of invalid or absent start codon; absence of in-frame
    *            stop codon; unassigned/abnormal codon]]
    */
   public LinkedList<Peptide> generatePeptides(String startCodon );
   
   /**
    *  Purpose: Sets calculated molecular weight (MW) for each peptide of the 
    *           calling peptide set object.
    *
    *  Methods called: Peptide.calcPeptideMW()
    *
    *  @require The calling peptide set object should have a field containing a   
    *           collection of generated peptides.
    *  @ensure Each generated peptide calls calcPeptideMW() and sets the calculated
    *          MW field for that peptide.
    */
   public void calcMWsOfPeptides();
   
   /**
    *  Purpose: Sets the calculated priority score for each peptide of the 
    *           calling peptide set object based on the user input consensus
    *           sequence.
    *
    *  Methods called: Peptide.calcPeptidePriority( String[] consensusSeq )
    *
    *  @param String consensusSeq, a consensus sequence of general amino acid 
    *         charge properties.
    *  @require The calling peptide set object should have a field containing a   
    *           collection of generated peptides.
    *           consensusSeq == peptide length for the calling peptide set object.
    *  @ensure Each generated peptide calls calcPeptidePriority() and sets the 
    *          calculated priority score field for that peptide.
    */
   public void calcPrioritiesOfPeptides( String consensusSeq );  
   
   /**
    *  Purpose: Sorts the full collection of generated peptides into descending
    *           order based on each peptide's molecular weight (MW).
    *
    *  Methods called: calcMWsOfPeptides(), Collections.sort(_)
    *
    *  @require The calling peptide set object should have a field containing a   
    *           collection of generated peptides.
    *  @ensure Molecular weights are calculated and set for the calling peptide set's
    *          collection of generated peptides and the peptides are subsequently
    *          sorted from highest to lowest MWs.
    */
   public void peptideMWSort();

   /**
    *  Purpose: Sorts the full collection of generated peptides into descending
    *           order based on each peptide's priority score.
    *
    *  Methods called: Collections.sort(_)
    *  [[Methods called: calcPrioritiesOfPeptides( String consensusSeq )]]
    *
    *  @require The calling peptide set object should have a field containing a   
    *           collection of generated peptides.
    *  @ensure Priority scores are calculated and set for the calling peptide set's
    *          collection of generated peptides and the peptides are subsequently
    *          sorted from highest to lowest priority score.
    */
   public void peptidePrioritySort();
   
   /**
    *  Purpose: Sorts the full collection of generated peptides into ascending
    *           order based on each peptide's first coding nucleotide position.
    *
    *  Methods called: Collections.sort(_)
    *
    *  @require The calling peptide set object should have a field containing a   
    *           full collection of generated peptides and starting coding nucleotide  
    *           position should have been assigned for each peptide during the full 
    *           peptide set generation.
    *  @ensure The full peptide set is sorted from lowest to highest starting
    *          nucleotide position.
    */
   public void peptideReturnSort();
         
   /**
    *  Purpose: Searches the full collection of generated peptides for peptides 
    *           with molecular weights (MWs) greater than or equal to a lower MW
    *           cutoff and greater than or equal to a higher MW cutoff.
    *
    *  @param double lowMW, lower molecular weight cutoff.
    *  @param double highMW, higher molecular weight cutoff.
    *  @require The calling peptide set object should have a field containing a   
    *           collection of generated peptides and lowMW > 0, highMW > 0, and
    *           highMW > lowMW. 
    *  @ensure The calling peptide set's field for storing a collection of 
    *          searched-for peptides is populated with the peptides within the
    *          user-designated MW range.
    *  @throws [[handling of MWs <= 0 and lowMW > highMW]]
    */   
   public LinkedList<Peptide> peptideMWSearch( double lowMW, double highMW );

   /**
    *  Purpose: Searches the full collection of generated peptides for peptides 
    *           with priority scores (PSs) greater than or equal to a lower PS
    *           cutoff and greater than or equal to a higher PS cutoff.
    *
    *  @param int lowPS, lower priority score cutoff.
    *  @param int highPS, higher priority score cutoff.
    *  @require The calling peptide set object should have a field containing a   
    *           collection of generated peptides and lowPS >= -4, highPS <= 4,
    *           and highPS >= lowPS.
    *  @ensure The calling peptide set's field for storing a collection of 
    *          searched-for peptides is populated with the peptides within the
    *          user-designated PS range.
    *  @throws [[handling of lowPS < -4, highPS > 4, and lowPS > highPS]]
    */   
   public LinkedList<Peptide> peptidePrioritySearch( int lowPS, int highPS );

   /**
    *  Purpose: Provides a string of the calling peptide set's unsorted or sorted
    *           full collection of generated peptides.
    *  @require The calling peptide set object should have a field containing a   
    *           full collection of generated peptides.
    *  @ensure Returns a full list of each peptide's amino acid sequence, starting 
    *          coding sequence nucleotide position, molecular weight, and priority
    *          score for the calling peptide set object.
    *  @return String, full list of generated peptides.
    *  @throws [[No Exception thrown if empty peptide set, just no peptides listed;
    *            could output an "Empty peptide collection" message.]]
    */
   public String listPeptides();
   
   /**
    *  Purpose: Provides a string of the calling peptide set's collection of
    *           searched-for peptides.
    *  @require The calling peptide set object should have a field containing a   
    *           collection of searched-for peptides.
    *  @ensure Returns a list of each peptide's amino acid sequence, starting 
    *          coding sequence nucleotide position, molecular weight, and priority
    *          score for the peptides identified by a previous search.
    *  @return String, list of searched-for peptides.
    *  @throws [[No Exception thrown if empty peptide set, just no peptides listed;
    *            could output an "Empty peptide collection" message.]]
    */
   public String listPeptidesB();
   
}  // end interface