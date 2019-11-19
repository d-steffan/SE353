import java.util.ArrayList;

public class GroupEnd extends Token {

	public GroupEnd(String val) {
		super(val, Token.type.GroupEnd);
	}
	
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub

	}

}
