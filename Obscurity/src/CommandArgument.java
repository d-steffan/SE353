import java.util.ArrayList;

public class CommandArgument extends Token {

	public CommandArgument(String val) {
		super(val, Token.type.CommandArgument);
	}
		
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub

	}

}
