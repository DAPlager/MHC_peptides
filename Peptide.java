/**
 *  @author:    Doug Plager and Ajalon Corcoran
 *  @version:   5-5-2019
 * 
 *  Course:     CS341 - Data Structures
 *  Assignment: Final Project
 *  File Name:  Peptide.java
 *
 *  Purpose:   A peptide object "constructor" class for use with a "PeptideSet"
 *             abstract data type (ADT) to capture, calculate, and provide
 *             the peptide-specific start position, amino acid sequence, molecular
 *             weight, and priority score for individual peptides derived from 
 *             a gene sequence.
 *  Constants: None.
 *  Input:     None.
 *  Output:    None.
 *
 *  Exceptions:   [[IllegalArgumentException??]]
 *  Associated Major Classes: PeptideSetInterface.java, PeptideSet.java, AminoAcid.java, 
 *                            PeptideSetClient.java
 */ 
 
// package

// import
import java.util.*;


public class Peptide /*implements Comparator<Peptide>, Serializable */ {

   // DATA FIELDS and/or CONSTANTS
   private int startNt;        // the position number of the first nucleotide (nt) 
         // of the peptide's first codon (with the "A" of the start ATG == nt #1),
         // NOTE: first nt position of codon == (aminoAcidPosition * 3) - 2;
         // Invariant: startNt >= 0 (0 indicating non-meaningful default value)
         
   private ArrayList<AminoAcid> peptide;  // peptide comprised of amino acid objects
   
   private double peptideMW;    // peptide molecular weight (MW; g/mol units) 
   
/* private ??? consensusPeptide; */ // NOTE: PROBABLY NEED TO EITHER INCLUDE
      // BOTH consensusPeptide AND peptidePriority as DATA FIELDS OR NEITHER
      // AS DATA FIELDS (and then you would have only a temporary prioritization)          
   
   private int peptidePriority;  // calculated priority score relative to 
                                 // user-designated "consensus" sequence 
                                          
   // CONSTRUCTORS
   public Peptide() {
   
      startNt = 0; 
      peptide = new ArrayList<AminoAcid>();  // REM: want to directly set ArrayList 
            // length to user-input 'peptideLength' of the assoc'd PeptideSet instance
      peptideMW = 0.0;
      peptidePriority = -111;   
   }
   
   public Peptide( int startNt, ArrayList<AminoAcid> peptide ) {
   
      this.startNt = startNt;
      this.peptide = peptide;
      
      this.peptideMW = 0.0;   // setPeptideMW() via LL<Peptide>.calcMWsOfPeptides()
                              // and calcPeptideMW();
                              // Need to calculate from peptide amino acid sequence
            
      this.peptidePriority = -111;   // setPeptidePriority() via LL<Peptide>.calcPrioritiesOfPeptides()
                                     // calcPeptidePriority( String[] consensusArray );  
                                     // Need to calculate and set actual priority score     
   }
   
   public Peptide( int startNt, ArrayList<AminoAcid> peptide, double peptideMW,
         int peptidePriority ) {
   
      this.startNt = startNt;
      this.peptide = peptide;
      this.peptideMW = peptideMW;
      this.peptidePriority = peptidePriority;      
   }
   
   // GETTERS and SETTERS
   public int getStartNt() {
      return startNt;
   }
   
   public void setStartNt( int startNt ) {
      this.startNt = startNt;
   }
   
   public ArrayList<AminoAcid> getPeptide() {
      return peptide;
   }
   
/* NEEDED setter?? */
   public void setPeptide( ArrayList<AminoAcid> peptide ) {
      this.peptide = peptide;
   }
   
   public double getPeptideMW() {
      return peptideMW;
   }

   public void setPeptideMW( double peptideMW ) {
      this.peptideMW = peptideMW;
   }
   
   public int getPeptidePriority() {
      return peptidePriority;
   }

   public void setPeptidePriority( int peptidePriority ) {
      this.peptidePriority = peptidePriority;
   }
   
   // toString
   @Override
   public String toString() {
   
      String oneLtrPeptide = "";
      for( int i = 0; i < peptide.size(); i++ ) {
         oneLtrPeptide += peptide.get(i).getOneLtrName();
      }
      
      return "\nFirst coding nucleotide position " +
            "for this peptide: " + startNt +
            "\nPeptide Sequence (N- to C-terminus): " + oneLtrPeptide +
            "\nMolecular Weight (g/mol): " + peptideMW +
            "\nPriority relative to consensus sequence: " + peptidePriority;
            // NOTE: Have NOT been utilizing this 'Peptide' toString();
            // Probably would need to condense this toString() for its output
            // to be more useful and include the relevant 'consensusSeq'
   }
      
   // OTHER METHODS
   public double calcPeptideMW() {
      // Peptide Molecular Weight (MW) calc. for the calling object's ArrayList<AminoAcid> 'peptide'
      double peptideMW = 0.0;
      
      for( AminoAcid eachAA : peptide ) {
         peptideMW += eachAA.getMolWeight();
      }
      
      return peptideMW;
   }

   
   public int calcPeptidePriority( String[] consensusArray ) {  
      // Peptide priority score calc. for the calling object's ArrayList<AminoAcid> 'peptide'
      int peptideScore = 0;
      String chargeType;
      
      for( int i = 0; i < peptide.size()/*or consensusArray.length*/; i++ ) {
         chargeType = consensusArray[i];
         
         switch( chargeType ) {
         
            case "np":
               if( peptide.get(i).getAACharge().equals( "nonpolar" ) )
                  peptideScore += 4;
               else if( peptide.get(i).getAACharge().equals( "polar" ) )
                  peptideScore += 0;
               else if( peptide.get(i).getAACharge().equals( "positive" ) )
                  peptideScore += -2;
               else  // if aaCharge = "negative"
                  peptideScore += -2;
               break;
            
            case "p":
               if( peptide.get(i).getAACharge().equals( "nonpolar" ) )
                  peptideScore += 0;
               else if( peptide.get(i).getAACharge().equals( "polar" ) )
                  peptideScore += 4;
               else if( peptide.get(i).getAACharge().equals( "positive" ) )
                  peptideScore += 2;
               else  // if aaCharge = "negative"
                  peptideScore += 2;
               break;
               
            case "+":
               if( peptide.get(i).getAACharge().equals( "nonpolar" ) )
                  peptideScore += -2;
               else if( peptide.get(i).getAACharge().equals( "polar" ) )
                  peptideScore += 2;
               else if( peptide.get(i).getAACharge().equals( "positive" ) )
                  peptideScore += 4;
               else  // if aaCharge = "negative"
                  peptideScore += -4;
               break;
               
            case "-":
               if( peptide.get(i).getAACharge().equals( "nonpolar" ) )
                  peptideScore += -2;
               else if( peptide.get(i).getAACharge().equals( "polar" ) )
                  peptideScore += 2;
               else if( peptide.get(i).getAACharge().equals( "positive" ) )
                  peptideScore += -4;
               else  // if aaCharge = "negative"
                  peptideScore += 4;
               break;
               
            default:
               return -222; // error 'signal'

         } // end switch
      }
      
      // "Normalization" of peptideScores for peptides of different lengths
      peptidePriority = peptideScore / peptide.size();
      
      return peptidePriority;
   }

}  // end class