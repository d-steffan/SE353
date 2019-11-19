import java.util.ArrayList;

public class StatementSeparator extends Token {

	public StatementSeparator(String val) {
		super(val, Token.type.StatementSeparator);
	}
	
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub

	}

}
