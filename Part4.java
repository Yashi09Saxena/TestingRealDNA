
/**
 * Write a description of Part4 here.
 * real gene testing from dna in file.
 * @author (Yashi Saxena) 
 * @version (27/07/2020)
 */
import edu.duke.*;
import java.io.File;
public class Part4 {
  public int findStopCodon(String dna, int StartIndex, String StopCodon)
  {
      int currIndex = dna.indexOf(StopCodon, StartIndex+3);
      while(currIndex!=-1)
      {
          int dff= currIndex-StartIndex;
          if(dff%3==0)
          {
              return currIndex;
          }
          else{
             currIndex = dna.indexOf(StopCodon, currIndex+1);
            }
        }
        return dna.length();   
    }
   public String findGene(String dna, int where)
   {
       int StartIndex= dna.indexOf("ATG",where);
       if(StartIndex==-1)
       {
           
           return "";
        }
        int taaIndex= findStopCodon(dna, StartIndex, "TAA");
        int tagIndex= findStopCodon(dna, StartIndex, "TAG");
        int tgaIndex= findStopCodon(dna, StartIndex, "TGA");
        int minIndex=0;
        if(taaIndex==-1||(tgaIndex!=-1&&tgaIndex<taaIndex))
        {
           minIndex=tgaIndex;
        }
        else
        {
            minIndex=taaIndex;
        }
        if(minIndex==-1||(tagIndex!=-1&&tagIndex<minIndex))
        {
           minIndex=tagIndex;
        }
        if(minIndex==-1||minIndex==dna.length())
        {
            return "";
        }
        return  dna.substring(StartIndex, minIndex+3);
    }
   public StorageResource getAllGenes(String dna)
   {
        StorageResource geneList= new StorageResource();
        int StartIndex=0;
        while(true){
            String currGene =findGene(dna,StartIndex);
            if(currGene.isEmpty())
            {
                break;
            }
            geneList.add(currGene);
            StartIndex= dna.indexOf(currGene,StartIndex)+currGene.length();
        }
        return geneList;
    }
   public void processGenes(StorageResource sr)
   {
       int co=0; int cal=0;   
    for(String s: sr.data())
    {
        co= co+1; 
        if(s.length()>cal)
        {
            cal= s.length();
        }
    }
     System.out.println("Total Number of valid genes :" +co);
     System.out.println("The length of the longest valid gene : " +cal);
     
     int count=0;
    for(String g: sr.data())
    {
        if(g.length()>60)
        {
            System.out.println("Gene is longer than 60 :" +g);
            count= count+1;
        }
    }
     System.out.println("Total number of genes longer than 60 are " +count);
  
       
        int count2=0;
    for(String g:sr.data())
    {
           float Ratio=0; float count1=0;
           int cIndex = g.indexOf("C");  
           int gIndex = g.indexOf("G");       
             while(true)
             {
                if((cIndex==-1)&&(gIndex==-1))
                 {
                     break ;
                 }
                 
                if(cIndex!=-1)
                 {
                     count1= count1+1; 
                     cIndex=g.indexOf("C",cIndex+1);
                  
                 }
                if(gIndex!=-1)
                 {
                     count1= count1+1;
                     gIndex=g.indexOf("G",gIndex+1);
                  
                 }
             }
          float length= g.length();
          Ratio= count1/length;
          if(Ratio>0.35)
          {
           System.out.println("The cgRatio of this " + g + " is higher than 0.35 -> " +Ratio );
           count2 = count2+1;
          }
    }
        System.out.println("Total number of genes whose cgratio higher than 0.35 are " +count2);
        
    }  
    public int CountCTG(String dna)
    {
        int ctgIndex= dna.indexOf("CTG");
        int count=0;
        while(true)
        {
          if(ctgIndex==-1)
            {
                break;
            }
          count =count+1;
          ctgIndex= dna.indexOf("CTG",ctgIndex+1);
          
        }
        return count;
    }
   public void testProcessGenes(String dna)
    {
       StorageResource gene = getAllGenes(dna);
       int resctg= CountCTG(dna);
       System.out.println("Total Number of CTG codons in long DNA strand: " +resctg);
       processGenes(gene);
    }
    public void test()
    {
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString();
        testProcessGenes(dna);
    }
    
}

