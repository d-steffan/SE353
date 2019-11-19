import java.util.ArrayList;

public class Attribute extends Token {

	public Attribute(String val) {
		super(val, Token.type.Attribute);
	}
	
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub

	}

}
