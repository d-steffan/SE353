import java.util.ArrayList;

public class LineContinuation extends Token {

	public LineContinuation(String val) {
		super(val, Token.type.LineContinuation);
	}
	
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub

	}

}
