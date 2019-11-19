import java.util.ArrayList;

public class Number extends Token {

	public Number(String val) {
		super(val, Token.type.Number);
	}
	
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub

	}

}
