import java.util.ArrayList;

public class NewLine extends Token {

	public NewLine(String val) {
		super(val, Token.type.NewLine);
	}
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub
		
		if ((options & 0b10) == 0b10)
		{
			int myPos = Tokens.indexOf(this);
			if (myPos>0) {
				if ((Tokens.get(myPos-1).getMytype().equals(Token.type.GroupStart)) | (Tokens.get(myPos-1).getMytype().equals(Token.type.Keyword)) | (Tokens.get(myPos-1).getMytype().equals(Token.type.NewLine)))
					this.setValue("");
				if (Tokens.get(myPos-1).getMytype().equals(Token.type.GroupEnd)) {
					// Special Case: $var = @() generates an Error -> add separator after array declaration
					int backPos = myPos-2;
					// find the beginning of the group to determine if it is an operation (if, while, foreach) or an array definition
					int bracketLevel = 1;
					while (bracketLevel>0) {
						if (Tokens.get(backPos).getMytype().equals(Token.type.GroupStart)) {
							bracketLevel--;
							if (bracketLevel<1)
								break;
						}
						if (Tokens.get(backPos).getMytype().equals(Token.type.GroupEnd))
							bracketLevel++;
						if (Tokens.get(backPos-1).getMytype().equals(Token.type.GroupEnd)) {
							bracketLevel++;
							backPos--;
						}
						backPos--;
					}
					if (Tokens.get(backPos-1).getMytype().equals(Token.type.Operator) | Tokens.get(backPos-1).getMytype().equals(Token.type.Member))
						this.setValue(";");
					else
						this.setValue("");
				}
				if ((Tokens.get(myPos-1).getMytype().equals(Token.type.Variable)) | (Tokens.get(myPos-1).getMytype().equals(Token.type.Command)) | (Tokens.get(myPos-1).getMytype().equals(Token.type.Number)) | (Tokens.get(myPos-1).getMytype().equals(Token.type.Operator)) | (Tokens.get(myPos-1).getMytype().equals(Token.type.CommandArgument)) | (Tokens.get(myPos-1).getMytype().equals(Token.type.String)) | (Tokens.get(myPos-1).getMytype().equals(Token.type.CommandParameter)))
					this.setValue(";");
				if  (Tokens.get(myPos-1).getMytype().equals(Token.type.Comment) & (options & 0b1)==0b0)
					if (Tokens.get(myPos-1).getValue().substring(Tokens.get(myPos-1).getValue().length()-3).contentEquals("#>"))
							this.setValue(";");
			}
			if (myPos+1<Tokens.size())
				if (this.getValue()!=";" & !(Tokens.get(myPos-1).getMytype().equals(Token.type.Comment)) & ((Tokens.get(myPos+1).getMytype().equals(Token.type.NewLine)) | (Tokens.get(myPos+1).getMytype().equals(Token.type.Comment))))
					this.setValue("");
			// Remove NewLine bit is set -> substitute NewLine with Separator ; this.setValue("");
		}
	}

}
