/**
 *  @author:    Doug Plager and Ajalon Corcoran
 *  @version:   5-5-2019
 * 
 *  Course:     CS341 - Data Structures
 *  Assignment: Final Project
 *  File Name:  AminoAcid.java
 *
 *  Purpose:   An amino acid object "constructor" class for use with a 
 *             "PeptideSet" abstract data type (ADT) to capture and provide
 *             amino acid-specific information for each amino acid of the 
 *             individual peptides derived from a gene sequence.
 *  Constants: None.
 *  Input:     None.
 *  Output:    None.
 *
 *  Exceptions:
 *  Associated Major Classes: PeptideSetInterface.java, PeptideSet.java, Peptide.java, 
 *                            PeptideSetClient.java
 */ 
 
// package

// import

public class AminoAcid /*implements Serializable*/ {

   // DATA FIELDS and/or CONSTANTS
   private String oneLtrName;    // one-letter amino acid symbol
   
   private String threeLtrName;  // three-letter amino acid abbreviation
   
   private String fullName;      // full amino acid name
   
   private double molWeight;     // Invariant: valid molWeight > 0.0
   
   private String aaCharge;      // Invariant: aaCharge == "nonpolar", "polar",
                                 // "positive", or "negative"
                                 
   private int aaPosition;       // amino acid position within the full, DNA-coded protein;
                                 // aaPosition calculated from ('startNt' + 2) / 3 within PeptideSet,java
                                 // Invariant: valid aminoAcidPostion >= 1 [[currently unused]]
   
   private String codonKey;      // the lowercase-, three-letter DNA codon that was translated  
                                 // to this instance's amino acid [[currently unused]]
   
   // CONSTRUCTORS
   public AminoAcid() {
      oneLtrName = "?";
      threeLtrName = "???";
      fullName = "Unknown";
      molWeight = 0.0;
      aaCharge = "unknown";
      aaPosition = 0;
      codonKey = "???";
   }
   
   public AminoAcid( String oneLtrName, String threeLtrName, String fullName,
         double molWeight, String aaCharge ) {
   
      this.oneLtrName = oneLtrName;
      this.threeLtrName = threeLtrName;
      this.fullName = fullName;
      this.molWeight = molWeight;
      this.aaCharge = aaCharge;
      this.aaPosition = 0;
      this.codonKey = "???";
   }

   
   public AminoAcid( String oneLtrName, String threeLtrName, String fullName,
         double molWeight, String aaCharge, int aaPosition, String codonKey ) {
   
      this.oneLtrName = oneLtrName;
      this.threeLtrName = threeLtrName;
      this.fullName = fullName;
      this.molWeight = molWeight;
      this.aaCharge = aaCharge;
      this.aaPosition = aaPosition;
      this.codonKey = codonKey;
   }

   // GETTERS and SETTERS
   public String getOneLtrName() {
      return oneLtrName;
   }
   
   public void setOneLtrName( String oneLtrName ) {
      this.oneLtrName = oneLtrName;
   }
   
   public String getThreeLtrName() {
      return threeLtrName;
   }
   
   public void setThreeLtrName( String threeLtrName ) {
      this.threeLtrName = threeLtrName;
   }
   
   public String getFullName() {
      return fullName;
   }
   
   public void setFullName( String fullName ) {
      this.fullName = fullName;
   }
   
   public double getMolWeight() {
      return molWeight;
   }
   
   public void setMolWeight( double molWeight ) {
      this.molWeight = molWeight;
   }
   
   public String getAACharge() {
      return aaCharge;
   }
   
   public void setAACharge( String aaCharge ) {
      this.aaCharge = aaCharge;
   }
   
   public int getAAPosition() {
      return aaPosition;
   }
   
/* NEEDED SETTER?? */
   public void setAAPosition() {
      this.aaPosition = aaPosition;
   }
   
   public String getCodonKey() {
      return codonKey;
   }
   
/* NEEDED SETTER?? */
   public void setCodonKey( String codonKey ) {
      this.codonKey = codonKey;
   }
   
   // toString
   public String toString() {
   
      return "\n" + oneLtrName + " " + threeLtrName + " " + fullName + " " +
            molWeight + " " + aaCharge +
            "\nAmino acid position in full protein: " + aaPosition +
            "\nCoded by: " + codonKey;
   }

}  // end class