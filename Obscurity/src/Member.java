import java.util.ArrayList;

public class Member extends Token {

	public Member(String val) {
		super(val, Token.type.Member);
	}
	
	@Override
	public void obfuscate(int options, ArrayList<Token> Tokens) {
		// TODO Auto-generated method stub
		
		int random = (int)(Math.random()*2);
		//System.out.println("Random: "+random);
		if ((options & 0b10000) == 0b10000) 
			switch (random) {
				case 0: {
					this.setValue(ProcessTokens.ObfuscateFormat(this.getValue()));
					break;
				}
				case 1:{
					//System.out.println("Implement case 1");
					break;
				}
				case 2: {
					//System.out.println("Is case 2 possible?");
					break;
				}
			}
	}

}
