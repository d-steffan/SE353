import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class ReadCSV {

	static String buffer = "";
	static ArrayList<Token> Tokens;
	static boolean inQuotes = false;
	static int columnCounter = 0;
	static String Value = "";
	static String Type = "";
	static String line;
	static char[] chars;
	static int i;
	
	public static ArrayList<Token> GetTokens(String filename) throws Exception
	{
		Tokens  = new ArrayList<Token>();
		File file = new File(filename);
		Scanner scanner = new Scanner(file);
		
		// Skip first line since in only contains column headers
		scanner.nextLine();
		
		while (scanner.hasNext()) {
			
			line = scanner.nextLine();
			if (inQuotes)
				buffer+='\n';
			chars = line.toCharArray();
			for (i=0;i<chars.length;i++)
			{
				if (i==0 & chars.length>2)
					if (chars[0]=='"' & chars[1]=='"' & chars[2]==',')
					{
						//System.out.println("i: "+i+" char: "+chars[i]+" String: "+line+" Note:// Empty String ");
						i+=2;
						buffer="";
						EndQuotes();
					}
				if (chars[i]=='"')
				{
					if (i+1<chars.length)
					{
						if (chars[i+1]=='"')
						{
							//System.out.println("i: "+i+" char: "+chars[i]+" String: "+line+" Note:// Double quotes skip ");
							buffer+='"';
							i++;
						}
						else
						{
							if (inQuotes) {
								EndQuotes();
							} else {
								//System.out.println("i: "+i+" char: "+chars[i]+" String: "+line+" Note://Begin of normal quotes");
								inQuotes = true;
							}
							
							
						}
					} else {
						if (inQuotes) {
							EndQuotes();
						} else {
							//System.out.println("i: "+i+" char: "+chars[i]+" String: "+line+" Note://Just \" in Line, probably NewLine");
							inQuotes = true;
						}
					}
				} else {
					if (inQuotes)
						buffer+=chars[i];
				}
				if (chars[i]=='\n')
					if (!inQuotes)
					{
						//System.out.println("i: "+i+" char: "+chars[i]+" String: "+line+" Note:// End of Line outside of Quote -> make new CSVItem and Token");
						// Never gets reached because nextLine probably truncates \n
					}
			}
        }
        scanner.close();
        return Tokens;
	}


	private static void EndQuotes() {
		//System.out.println("i: "+i+" char: "+chars[i]+" Column: "+columnCounter+" String: "+line+" Note://End of normal quotes, do something with buffer");
		inQuotes = false;
		switch(columnCounter) {
			case 0: {
				Value = buffer;
				break;
			}
			case 1: {
				Type = buffer;
				break;
			}
			case 7: {
				switch(Type) {
					case "Comment": {
						Tokens.add(new Comment(Value));
						break;
					}
					case "NewLine": {
						Tokens.add(new NewLine(Value));
						break;
					}
					case "Attribute": {
						Tokens.add(new Attribute(Value));
						break;
					}
					case "Command": {
						Tokens.add(new Command(Value));
						break;
					}
					case "CommandArgument": {
						Tokens.add(new CommandArgument(Value));
						break;
					}
					case "CommandParameter": {
						Tokens.add(new CommandParameter(Value));
						break;
					}
					case "GroupEnd": {
						Tokens.add(new GroupEnd(Value));
						break;
					}
					case "GroupStart": {
						Tokens.add(new GroupStart(Value));
						break;
					}
					case "Keyword": {
						Tokens.add(new Keyword(Value));
						break;
					}
					case "LineContinuation": {
						Tokens.add(new LineContinuation(Value));
						break;
					}
					case "LoopLabel": {
						Tokens.add(new LoopLabel(Value));
						break;
					}
					case "Member": {
						Tokens.add(new Member(Value));
						break;
					}
					case "Number": {
						Tokens.add(new Number(Value));
						break;
					}
					case "Operator": {
						Tokens.add(new Operator(Value));
						break;
					}
					case "Position": {
						Tokens.add(new Position(Value));
						break;
					}
					case "StatementSeparator": {
						Tokens.add(new StatementSeparator(Value));
						break;
					}
					case "String": {
						Tokens.add(new TokenString(Value));
						break;
					}
					case "Type": {
						Tokens.add(new Type(Value));
						break;
					}
					case "Unknown": {
						Tokens.add(new Unknown(Value));
						break;
					}
					case "Variable": {
						Tokens.add(new Variable(Value));
						break;
					}
					default : {
						System.out.println("Unknown Token: "+Type+" Line: "+line);
					}
				}
			}
		}
		columnCounter = (columnCounter+1)%8;
		buffer="";
	}

}
