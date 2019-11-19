import java.util.ArrayList;

public class Comment extends Token {
	
	public Comment(String val) {
		super(val, Token.type.Comment);
	}

	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		
		if ((options & 0b1) == 0b1)
		{
			// Remove Comment bit is set -> remove comment
			this.setValue("");
		}

	}

}
