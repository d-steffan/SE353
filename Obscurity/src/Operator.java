import java.util.ArrayList;

public class Operator extends Token {

	public Operator(String val) {
		super(val, Token.type.Operator);
	}
	
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub

	}

}
