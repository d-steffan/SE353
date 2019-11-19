import java.util.ArrayList;

public class Keyword extends Token {

	public Keyword(String val) {
		super(val, Token.type.Keyword);
	}
	
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub
		if (this.getValue().equals("in"))
			this.setValue(" in ");
	}

}
