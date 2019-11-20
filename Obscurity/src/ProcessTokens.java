import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

/**Utility class to process the script tokens gathered by {@link ReadCSV}.
 * 
 * @author Dominik Steffan
 *
 */
public class ProcessTokens {

	private static ArrayList<Token> Tokens;
	private static HashMap<String, String> vars;
	/**Iterates through all tokens and calls their respective obfuscate method as declared in {@link Token}.<br>
	 * If the user chose to also obfuscate variable names, this will be done in this method, since gathering of similar variable names has to be done from a higher level of scope.<br>
	 * Depending on how obfuscation of variable names was selected either 1 or 8 digits will be used for obfuscated varaiable names.<br>
	 * If 1 digit was selected, variable names will ascend from 'a'.<br>
	 * If 8 digits were selected, variable names will be obfuscated with the first 8 digits of their respective MD5 hash.<br>
	 * Variable names will always be converted to lower-case in their respective obfuscate() to match similar variables with their hash. PowerShell is not case sensitive so $var and $Var would be the same variable but has different hash values.
	 * 
	 * @param tokens ArrayList of {@link Token}
	 * @param options options bitmask as described in {@link ApplicationWindow}.
	 */
	public static void Process(ArrayList<Token> tokens, int options) {
		Tokens = tokens;
		vars = new HashMap<String, String>();
		for (Token t:Tokens) {
			t.obfuscate(options, Tokens);
			if (t.getMytype()==Token.type.Variable)
				if (!vars.containsKey(t.getValue()) & !t.getValue().equals("$_") & !t.getValue().equalsIgnoreCase("$host") & !t.getValue().equalsIgnoreCase("$false") & !t.getValue().equalsIgnoreCase("$true") & !t.getValue().contains("$env:") & (((options & 0b100) == 0b100 ) | ((options & 0b1000) == 0b1000 )))
					if ((options & 0b100) == 0b100)
						vars.put(t.getValue(),"$"+HashFactory.hashString(t.getValue(),"MD5").substring(0,8));
					else
						vars.put(t.getValue(), t.getValue());
		}
		ArrayList<String> sorted = new ArrayList<String>(vars.keySet());
		
		if ((options & 0b1000)==0b1000)
			for (int i=0;i<vars.size();i++)
				vars.put(vars.get(sorted.get(i)),"$"+(char)(i+97));
		// Sort descending so the longest string gets matched first, otherwise $d would break $domains
		if (((options & 0b1000)==0b1000) | ((options & 0b100)==0b100))
		{
			int pos = 0;
			int longest = 0;
			int poslongest = sorted.size()-1;
			while (pos<sorted.size()) {
				for (int i=pos;i<sorted.size();i++) {
					if (sorted.get(i).length()>longest) {
						longest = sorted.get(i).length();
						poslongest = i;
					}
				}
				String tmp = sorted.get(poslongest);
				sorted.set(poslongest, sorted.get(pos));
				sorted.set(pos, tmp);
				pos++;
				poslongest = sorted.size()-1;
				longest = 0;
			}
	
			for (Token t:Tokens) {
				if (t.getMytype()==Token.type.Variable & sorted.contains(t.getValue()))
					t.setValue(vars.get(t.getValue()));
				if (t.getMytype()==Token.type.String)
					for (String s:sorted)
						t.setValue(t.getValue().replace(s, vars.get(s)));
			}
		}
	}
	/**Writes the processed tokens into a new File.
	 * 
	 * @param filename Output path as chosen in {@link ApplicationWindow}
	 * @throws Exception
	 */
	public static void WriteProcessedTokens(String filename) throws Exception {
		BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
		for (Token t:Tokens) {
			//System.out.println(t.getValue());
			writer.write(t.getValue());
		}
		
	    writer.close();
	}
	
	/**Static method to be called from tokens {@link Member} and {@link TokenString}<br>
	 * This technique makes use of the fact that PowerShell evaluates Strings when using members and of course normal strings by using the formatted String from .NET.<br>
	 * e.g. $MyObject.doSomething may be obfuscated as $MyObject.("{2}{1}{0}" -f "thing","Some","do") 
	 *   
	 * @param val Input to be obfuscated
	 * @return Obfuscated String of val
	 */
    public static String ObfuscateFormat(String val) {
		//System.out.println("Member: "+val);
		String res = "";
		String resFront = "";
		int count = 0;
		while (val.length()>3)
		{
			int cut = (int)(Math.random()*Math.min(4, val.length()));
			//System.out.println(val.substring(val.length()-cut));
			res += "\""+val.substring(val.length()-cut)+"\",";
			val = val.substring(0,val.length()-cut);
			count++;
		}
		//System.out.println("Rest: "+val);
		res += "\""+val+"\"";
		while (count>-1) {
			resFront += "{"+count+"}";
			count--;
		}
		//System.out.println("Result: (\""+resFront+"\" -f "+res+")");
		return "(\""+resFront+"\" -f "+res+")";
    }
	
}
