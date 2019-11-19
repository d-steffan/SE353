import java.util.ArrayList;

public class CommandParameter extends Token {

	public CommandParameter(String val) {
		super(val, Token.type.CommandParameter);
	}
	
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub
		this.setValue(" "+this.getValue()+" ");
	}

}
