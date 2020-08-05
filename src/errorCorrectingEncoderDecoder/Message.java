package errorCorrectingEncoderDecoder;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Message {
	private String path;
	private byte[] msg;
	private String[] binary;
	
	Message (String path) {
		this.path = path;
		try {
			msg = Files.readAllBytes(Paths.get(this.path));
			binary = new String[msg.length];
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void sendingMessage() {
		convertDecToBin();
		setError();
		convertBinToDec();
		loadMsgToFile();
	}
	
	private void convertDecToBin() {
		for (int i = 0; i < binary.length; i++) {
			String s = Integer.toBinaryString(msg[i]);
			binary[i] = remplirZero(s); 
		}
	}
	private String remplirZero(String s) {
		int len = s.length();
		int n = 8 - len;
		while (n > 0) {
			s = "0" + s;
			n--;
		}
		return s;
	}
	private void setError() {
		Random random = new Random();
		for (int i = 0; i < binary.length; i++) {
			int randPos = random.nextInt(8);
			StringBuilder res = new StringBuilder(binary[i]);
			char charPos = res.charAt(randPos) == '1' ? '0' : '1';
			res.setCharAt(randPos, charPos);
			binary[i] = res.toString();
		}
	}
	private void convertBinToDec() {
		for (int i = 0; i < binary.length; i++) {
			int nbr = 0;
			for (int j = 0; j < 8; j++) {
				if (binary[i].charAt(j) == '1')
					nbr += Math.pow(2, 7 - j);
			}
			msg[i] = (byte)nbr;
		}
	}
	private void loadMsgToFile() {
		try {
			FileOutputStream fout = new FileOutputStream("C:\\Users\\HP\\eclipse-work\\site jet brains\\src\\errorCorrectingEncoderDecoder\\received.txt");
			fout.write(msg);
			fout.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
