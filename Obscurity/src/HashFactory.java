import java.security.MessageDigest;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.ByteBuffer;
import java.util.Arrays;


/**
 * Factory that generates hashes as String objects.<br>
 * <br>
 * Various SHA-256 Hashes for some standard users:<br>
 * 	SE354		2a859255d5713bed4a05f0d5e91824f751684388fcc765f13f52c492838f400c<br>
 * P@ssw0rd	b03ddf3ca2e714a6548e7495e2a03f5e824eaac9837cd7f159c67b90fb4b7342<br>
 * 12345 5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5<br>
 * 6e0f345e25 e36efef6b45862c70d02a258f8215b93a240f2965fa6d919be5e286bc553ce82<br>
 *
 * 
 * @author Dominik Steffan
 *
 */

public class HashFactory {
	/**Calculates the hash for the given message using the set algorithm.
	 * 
	 * @param message Input that will be hashed
	 * @param algorithm Hashing Algorithm, can be MD5, SHA-1 or SHA-256
	 * @return Hash of message using given algorithm
	 */

	public static String hashString(String message, String algorithm) {
        
    	byte[] hashedBytes = null;
		try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
        
			hashedBytes = digest.digest(message.getBytes("UTF-8"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        return convertByteArrayToHexString(hashedBytes);
   
	}
	
	/**Calculates the hash for the given message using the set algorithm.<br>
	 * Overload of hashString using char[] for more security.
	 * 
	 * @param chars message that will be hashed
	 * @param algorithm Hashing Algorithm, can be MD5, SHA-1 or SHA-256
	 * @return Hash of message using given algorithm
	 */
	
	public static String hashString(char[] chars, String algorithm) {
		CharBuffer charBuffer = CharBuffer.wrap(chars);
		ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
		byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),byteBuffer.position(), byteBuffer.limit());
		byte[] hashedBytes = null;
		try {
		MessageDigest digest = MessageDigest.getInstance(algorithm);   
		hashedBytes = digest.digest(bytes);
		Arrays.fill(chars, '\u0000');
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return convertByteArrayToHexString(hashedBytes);
	}
	/**
	 * Helper method for converting Byte Arrays to Hexadecimal-Strings
	 * @param arrayBytes byte[] to convert
	 * @return Hexadecimal string representation of input byte[]
	 */
	private static String convertByteArrayToHexString(byte[] arrayBytes) {
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < arrayBytes.length; i++) {
	        stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
	                .substring(1));
	    }
	    return stringBuffer.toString();
	}
	
}
