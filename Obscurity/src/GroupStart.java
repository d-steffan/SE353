import java.util.ArrayList;

public class GroupStart extends Token {

	public GroupStart(String val) {
		super(val, Token.type.GroupStart);
	}
	
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub

	}

}
