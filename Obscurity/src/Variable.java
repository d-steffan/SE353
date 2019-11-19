import java.util.ArrayList;

public class Variable extends Token {

	public Variable(String val) {
		super(val, Token.type.Variable);
	}
	
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub
		if (this.getValue().equalsIgnoreCase("$psitem") | this.getValue().equals("$_"))
			this.setValue("$_");
		else
			this.setValue("$"+this.getValue().toLowerCase());
		// COnverting to lower case ensures we won't have duplicates, since PowerShell is case-insensitive
	}
}
