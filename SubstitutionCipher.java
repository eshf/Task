import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class SubstitutionCipher {


//create HashMap for encryption
public static ArrayList<Character> createHashMap(String keywordOffsetString)
{

    String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    LinkedHashSet<Character> map= new LinkedHashSet<Character>(); //creating HashMap


    for (int i = 0; i < keywordOffsetString.length(); i++) 
    {
    	map.add(keywordOffsetString.charAt(i));
    }

    for (int j = 0; j < a.length(); j++) 
    {
    	map.add(a.charAt(j));
    }
    
    ArrayList<Character> hashlist = new ArrayList<Character>();
    hashlist.addAll(map);
    
    return hashlist;
}


//create HashSet for decryption
public static ArrayList<Character> createDecryptedMap(String keywordOffsetString)
{

    String a = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    LinkedHashSet<Character> map= new LinkedHashSet<Character>();


    for (int i = 0; i < keywordOffsetString.length(); i++) 
    {
    	map.add(keywordOffsetString.charAt(i));
    }
    for (int j = 0; j < a.length(); j++) 
    {
    	map.add(a.charAt(j));
    }
    
    //keywordOffsetalpha list
    ArrayList<Character> hashlist = new ArrayList<Character>();
    hashlist.addAll(map);
    
    //alphabet list
    ArrayList<Character> alist = new ArrayList<Character>();
    for(int letter = 0; letter < a.length(); letter++)
    {
    	alist.add(a.charAt(letter));
    }
 
    
    ArrayList<Character> officialmap = new ArrayList<Character>();
    for (int k = 0; k < 26; k++)
    {
    	officialmap.add('a');
    }
    for (int l = 0; l < 26; l++)
    {
    	int hashno = (int)hashlist.get(l) - 65;
    	officialmap.set(hashno, alist.get(l));
    }
    
    
   return officialmap;

}

public static void encode(String keywordOffsetString, String inputFile, String outputFile) throws FileNotFoundException
{

   
    ArrayList<Character> hashlist = new ArrayList<Character>();

   
	hashlist = SubstitutionCipher.createHashMap(keywordOffsetString);
    
    
    int keyOffset= keywordOffsetString.length();
	
	//take in the message file as input(in task specification)
	//encryptPlaintext method takes in inputFile as parameter and called in command-line as args[2], output file as args[3]
    BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    StringBuilder stringBuilder = new StringBuilder();
    String line = null;
    String ls = System.getProperty("line.separator");
    try {
		while ((line = reader.readLine()) != null) 
		{
			if(line.trim().length() > 0) 
			{
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    // delete the last new line separator
    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    try {
		reader.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    //accept uppercase characters
    String cipher = stringBuilder.toString().toUpperCase();
    System.out.println("Plain Text is:"+ cipher);
    
    String encrypted = "";
    
    char [] plaintext = cipher.toCharArray();
    for(int i = 0; i < plaintext.length; i++)
    {
    	int alphabet = (int) plaintext[i];
    	if(alphabet <= 90 && alphabet >= 65)
		{
    		alphabet = alphabet - 65;
			alphabet = hashlist.get(alphabet);
			plaintext[i] = (char) alphabet;
		}
    }
    cipher = String.copyValueOf(plaintext);
  
    System.out.println("Encrypted text is:" + cipher);
    //write file 
	try
	{
        //output(write) CipherText as a file (in assignment specification)
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		 for (int i = 0; i < cipher.length(); i++) 
		 {
            writer.write(cipher.substring(i, i+keyOffset));
        }
        writer.close();
		 }
	catch (IOException ex) 
	{
		System.out.println("Error: File cannot be output."); 
	}
	
	System.out.println("File has been encrypted.");	
    
    }


public static void decode(String keywordOffsetString, String inputFile, String outputFile) throws FileNotFoundException
{
	

	ArrayList<Character> alphalist = new ArrayList<Character>();


	alphalist = SubstitutionCipher.createDecryptedMap(keywordOffsetString);

    int keyOffset= keywordOffsetString.length();
    
	System.out.println(alphalist);
	//HashMap<String, String> hm= SubstitutionCipher.createHashMap(decryptedString);
	//take in the ciphertext file as input(in assignment specification)
	//decryptPlaintext method takes in inputFile as parameter and called in command-line as args[2], output file as args[3]
	BufferedReader reader = new BufferedReader(new FileReader(inputFile));
    StringBuilder stringBuilder = new StringBuilder();
    String line = null;
    String ls = System.getProperty("line.separator");
    try {
		while ((line = reader.readLine()) != null) 
		{
			if(line.trim().length() > 0) 
			{
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    // delete the last new line separator
    stringBuilder.deleteCharAt(stringBuilder.length() - 1);
    try {
		reader.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
    //accept uppercase characters
    String cipher = stringBuilder.toString().toUpperCase();
    System.out.println("Encrypted Text is:"+ cipher);
    
    String encrypted = "";
    
    char [] plaintext = cipher.toCharArray();
    for(int i = 0; i < plaintext.length; i++)
    {
    	int thealphabet = (int) plaintext[i];
    	if(thealphabet <= 90 && thealphabet >= 65)
		{
    		thealphabet = thealphabet - 65;
    		thealphabet = alphalist.get(thealphabet);
    		plaintext[i] = (char) thealphabet;
		}
    }
    cipher = String.copyValueOf(plaintext);
  
    System.out.println("Decrypted text is:" + cipher);
    //write file 
	try
	{
        //output(write) CipherText as a file (in assignment specification)
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		 for (int i = 0; i < cipher.length(); i++) {
            writer.write(cipher.substring(i, keyOffset +i));
        }
        writer.close();
		 }
	catch (IOException ex) 
	{
		System.out.println("Error: File cannot be output."); 
	}
	
	System.out.println("File has been decrypted.");	
    
    }

public static void main(String[] args) throws FileNotFoundException{ 
	System.out.println(args[1] + " " + args[2] + " " + args[3]);
    if (args.length!=4)
	{
		System.out.println("Invalid parameters entered.");
	}
    else {
		if (args[0].equalsIgnoreCase("-e"))
		{
			SubstitutionCipher.encode(args[1], args[2], args[3]);
		}
		else if(args[0].equalsIgnoreCase("-d"))
		{
			SubstitutionCipher.decode(args[1], args[2], args[3]);
		
		}
	}   
}
}
