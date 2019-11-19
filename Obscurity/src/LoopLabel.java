import java.util.ArrayList;

public class LoopLabel extends Token {

	public LoopLabel(String val) {
		super(val, Token.type.LoopLabel);
	}
	
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub

	}

}
