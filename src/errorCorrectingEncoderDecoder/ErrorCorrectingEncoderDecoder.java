package errorCorrectingEncoderDecoder;

import java.util.Random;
import java.util.Scanner;

public class ErrorCorrectingEncoderDecoder {

	static final Scanner sc = new Scanner(System.in);
	static final Random random = new Random();
	
	public static void main(String[] args) {
		String input = sc.nextLine();
		String output = createError(input);
		System.out.println(output);
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
}
