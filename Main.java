import java.util.*; 
import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.charset.*;
import java.nio.file.*;



class Main {
public static void main(String[] args) throws Exception {
 CMUList test = new CMUList();
  String input = readFile("Poem", StandardCharsets.UTF_8);
  //String unknown = readFile("Unknown", StandardCharsets.UTF_8);
  String newLine = System.getProperty("line.separator");
  //System.out.println(unknown);
  
  //while (unknown.contains("  "))
  //{
    //unknown.replaceAll("[" + newLine + "]"+"[" + newLine + "]","[" + newLine + "]");
  //}

  String[] text = input.replaceAll("[^a-zA-Z\\s]", " ").split("\\s+");
  
  //remmoves all commas then replaces all newlines with commas
  String noComma = input.replaceAll("[^a-zA-Z\\s]", " ");
  String flipped = noComma.replaceAll("[" + newLine + "]", " , ").replaceAll("\\s+"," ").replaceAll("\\s\\,",",");
  //System.out.println(flipped);

  String[] lin = flipped.split(" ");
  
  //stores data for which line a word is on
  int[] line = new int[lin.length];
  int[] wordCount = new int[lin.length];

  for(int i = 0,j = 1,w=1;i<lin.length;i++,w++){
    line[i]=j;
    //wordCount[i]=w;


    while (lin[i].contains(",")){
      j++;
      lin[i]=lin[i].substring(0,lin[i].length()-1);
    }
    
  }
  //removes all bad chars
  String[] data = new String[text.length];
  for(int i = 0;i<text.length;i++){
    data[i] = text[i].toLowerCase();//.replaceAll("[^a-zA-Z]", "");
  }

  String unknown="";
  String known="";
  List<String> groups = new ArrayList<String>();

  int i =0;
  while (i<data.length){
    String temp = data[i]+ ":"+ line[i];
    int j = i+1;
    while (j<data.length){//&&!known.contains(data[i])){
      int doesRhyme = test.checkRhyme(data[i],data[j]);
      //if (doesRhyme == 1&&!known.contains(data[j]))//&&!data[i].equals(data[j]))
      //{
      //  known=known+" "+data[j];
      //  temp=temp+","+data[j]+":"+line[j];
      //}
       if (doesRhyme == 1)//&&!data[i].equals(data[j]))
      {
        if (!known.contains(data[j])){        
        temp=temp+","+data[j]+":"+line[j];
        }
      }
      else if (doesRhyme==-1&&!unknown.contains(data[i])){
        unknown = unknown + " "+data[i];
      }
      else if (doesRhyme==-2&&!unknown.contains(data[j])){
        unknown = unknown + " "+data[j];
      }

      j++;
    }
    if (temp.contains(",")){
      groups.add(temp);
      known=known +" "+temp;
    }
    //System.out.println(data[i]+" has no rhymes");
    i++;
  }
for (int k = 0; k<data.length; k++){
  System.out.println(data[k]+k);
}
 System.out.println("");
 //System.out.println("I have ryhmed:" + known);
 System.out.println("I do not know:" + unknown);
BufferedWriter writer = new BufferedWriter(new FileWriter("Output"));
String[] groupArr = groups.toArray(new String[0]);
for(int k = 0;k<groupArr.length;k++){
  if (isDifferent(groupArr[k])){
  System.out.println(groupArr[k]);
  writer.write(groupArr[k]);
  writer.newLine();}
  }

writer.close();


}


private static boolean isDifferent(String inputPS){
  String[] input = inputPS.split(",|\\:");
  String[] postProc;
  if(input.length>2){
  postProc = new String[input.length/2];
  for (int i = 0; i<input.length; i=i+2)
  {
    //System.out.println(i/2 + ":"+i);
    postProc[i/2] = input[i];
    
  }
  }
  else 
  {
    postProc = input;
  }

  String first = input[0];
  for (int i = 1; i<postProc.length; i++)
  {
    if (!first.equals(postProc[i])){
      //System.out.println(first+" "+postProc[i]);
      return true;
    }
  }
  return false;

}

//source https://stackoverflow.com/questions/326390/how-do-i-create-a-java-string-from-the-contents-of-a-file
public static String readFile(String path, Charset encoding) 
  throws IOException 
{
  byte[] encoded = Files.readAllBytes(Paths.get(path));
  return new String(encoded, encoding);
}
}