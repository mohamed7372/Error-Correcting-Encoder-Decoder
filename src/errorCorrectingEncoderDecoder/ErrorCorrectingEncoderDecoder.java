package errorCorrectingEncoderDecoder;

import java.util.Random;
import java.util.Scanner;

public class ErrorCorrectingEncoderDecoder {

	static final Scanner sc = new Scanner(System.in);
	static final Random random = new Random();
	
	public static void main(String[] args) {
		String message = sc.nextLine();
		System.out.println(message);
		// encode message
		String msgSend = encodeMsg(message);
		System.out.println(msgSend);
		// msg with errors
		msgSend = createError(msgSend);
		System.out.println(msgSend);
		// decode msg + correction
		String msgCorrect = decodeMsg(msgSend);
		System.out.println(msgCorrect);
	}

	static String createError(String input) {
		StringBuilder res = new StringBuilder();
		int i = 0;
		while(i + 3 <= input.length()) {
			String word = input.substring(i, i + 3);
			res.append(newWord(word));
			i += 3;
		}

		int rest = res.toString().length();
		if (input.length() != rest)
			res.append(input.substring(rest));
		return res.toString();
	}
	static char randomChar() {
		char charErr;
		int n = random.nextInt(26);
		boolean lowerCase = random.nextBoolean();

		if (lowerCase)
			charErr = (char)(n + 97);
		else
			charErr = (char)(n + 65);
			
		return charErr;
	}
	static String newWord(String subWord) {
		char[] arr = subWord.toCharArray(); 
		int posErr = random.nextInt(3);
		arr[posErr] = randomChar();
		return new String(arr);
	}
	
	static String encodeMsg(String msg) {
		String s = "";
		int j;
		for (int i = 0; i < msg.length(); i++) {
			j = 1;
			while(j <= 3) {
				s += msg.charAt(i);
				j++;
			}
		}
		return s;
	}
	static String decodeMsg(String msgError) {
		String s = "";
		int i = 0;
		while(i + 3 <= msgError.length()) {
			String word = msgError.substring(i, i + 3);
			s += String.valueOf(correction(word));
			i += 3;
		}
		return s;
	}
	static char correction(String triplet) {
		if (triplet.charAt(0) == triplet.charAt(1))
			return triplet.charAt(0);
		else if (triplet.charAt(0) == triplet.charAt(2))
			return triplet.charAt(0);
		else
			return triplet.charAt(1);
	}
}
