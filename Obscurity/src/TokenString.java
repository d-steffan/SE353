import java.util.ArrayList;

public class TokenString extends Token {

	public TokenString(String val) {
		super(val, Token.type.String);
	}
	
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub
		this.setValue("\""+this.getValue()+"\"");
	}

}
