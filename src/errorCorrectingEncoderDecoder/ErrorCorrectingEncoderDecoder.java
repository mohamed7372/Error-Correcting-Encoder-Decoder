package errorCorrectingEncoderDecoder;

import java.util.Scanner;

public class ErrorCorrectingEncoderDecoder {
	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Write a mode: ");
		String mode = sc.next();
		System.out.println();
		Mode modeTxt = null;
		
		switch (mode) {
		case "encode":
			modeTxt = new Encode("send.txt", "encoded.txt");
			Encode d1 = (Encode)modeTxt;
			d1.code();
			break;
		case "send":
			modeTxt = new Send("encoded.txt", "received.txt");
			Send d2 = (Send)modeTxt;
			d2.code();
			break;
		case "decode":
			modeTxt = new Decode("received.txt", "decoded.txt");
			Decode d3 = (Decode)modeTxt;
			d3.code();
			break;
		default:
			System.out.println("error");
		}
		sc.close();
	}
}
