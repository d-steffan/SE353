import java.util.ArrayList;

public class Command extends Token {

	public Command(String val) {
		super(val, Token.type.Command);
	}
	
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub
		this.setValue(this.getValue()+" ");
	}

}
