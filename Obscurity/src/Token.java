import java.util.ArrayList;

abstract class Token {
	private String value;
	private type mytype;
	
	//PSToken Types according to https://docs.microsoft.com/en-us/dotnet/api/system.management.automation.pstokentype?view=pscore-6.2.0
	public enum type {
		Attribute,
		Command,
		CommandArgument,	
		CommandParameter,
		Comment,
		GroupEnd,
		GroupStart,
		Keyword,
		LineContinuation,
		LoopLabel,
		Member,
		NewLine,
		Number,
		Operator,
		Position,
		StatementSeparator,
		String,
		Type,
		Unknown,
		Variable
	}
	
	
	
	public Token(String Value, Token.type type) {
		this.setValue(Value);
		this.setMytype(type);
		//System.out.println("DEBUG: New Token: Type: "+this.mytype+" Value: "+this.value);
	}
	
	public abstract void obfuscate (int options, ArrayList<Token> Tokens);

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public type getMytype() {
		return mytype;
	}

	public void setMytype(type mytype) {
		this.mytype = mytype;
	}
	
	public String toString() {
		return this.mytype+":"+this.value;
	}
	
}
