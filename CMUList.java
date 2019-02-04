import java.util.*; 
import java.net.*;
import java.io.*;



public class CMUList 
{
  private String[] data;
  private String[][] procData;

  public CMUList() throws Exception{
  
    URL url = new URL("http://svn.code.sf.net/p/cmusphinx/code/trunk/cmudict/cmudict-0.7b");
    Scanner s = new Scanner(url.openStream());
     List<String> input = new ArrayList<String>();
     //int limit = 0;
     boolean atA = false;
     while (s.hasNextLine()){// && limit<200){
      String currentLine = s.nextLine();
      //System.out.println(currentLine);
      //System.out.println(currentLine.substring(0,3));
      if (currentLine.substring(0,1).equals("A")){
        atA=true;
      }
      if (!currentLine.substring(0,3).equals(";;;")&&atA){
      input.add(currentLine);
      }
      //limit++;
    }

    
  data = input.toArray(new String[0]);
  
  int j = 0;
  procData = new String[data.length][];
  while (j<data.length){

    String[] temp = (data[j].split("  ?"));
    procData[j]=temp;

    j++;
  }
  //System.out.println(procData[4][0]);
  }
  public String[] findSounds(String input){
    //System.out.println(input);
    input = input.toUpperCase();
    int location = binarySearch(procData,input);
    //System.out.println(""+location);
    if (location == -1){
      return null;
    } 
    int locLength = procData[location].length;
    return Arrays.copyOfRange(procData[location],1,locLength);
    //return procData[location][0];
  }

 public int checkRhyme(String wordA, String wordB){
    if (wordA == null || wordB == null){
      return 0;
    }

    String[] soundA = this.findSounds(wordA);
    String[] soundB = this.findSounds(wordB);
    if(soundA == null){
      return -1;
    }
    if(soundB==null){
      return -2;
    }
    int accentA = this.findAccent(wordA);
    int accentB = this.findAccent(wordB);
    int lenA= soundA.length;
    int lenB= soundB.length;
    String[] endA = Arrays.copyOfRange(soundA,accentA,lenA);
    String[] endB = Arrays.copyOfRange(soundB,accentB,lenB);
    if(stringArrEq(endA,endB)){
      //System.out.println("yes");
      return 1;
    }
   //System.out.println("no");

   return 0;
 }
 
 private boolean stringArrEq(String[] inputA, String[] inputB){
 int lenA = inputA.length;
 int lenB = inputB.length;
 if (lenA!=lenB)
 {
   return false;
 }
 int i = 0;
 while (i<lenA){

   if (!inputA[i].equals(inputB[i])){
     return false;
   }
  i++;
 }
return true;
 }
  
public int findAccent(String inputWord){
String[] input = this.findSounds(inputWord);

  int i=input.length-1;
    for(boolean accent = false;i>=0&&!accent;i--){
      int blurbLen = input[i].length();
      if (input[i].substring(blurbLen-1,blurbLen).matches("\\d")){
      return i;
      //accent = true;
      }

    }
    return -1;
}

 public String lastSounds(String word){
  
   return "";
 }
  //source https://stackoverflow.com/questions/32260445/implementing-binary-search-on-an-array-of-strings
  private static int binarySearch(String[][] a, String x) {
    int low = 0;
     int high = a.length - 1;
     int mid;

    while (low <= high) {
         mid = (low + high) / 2;

         if (a[mid][0].compareTo(x) < 0) {
             low = mid + 1;
         } else if (a[mid][0].compareTo(x) > 0) {
             high = mid - 1;
         } else {
             return mid;
         }
     }

     return -1;
 }

  

}