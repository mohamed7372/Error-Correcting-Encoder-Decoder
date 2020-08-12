package errorCorrectingEncoderDecoder;

public class Message {
	private byte[] msg;
	
	private String[] hex;
	private String[] bin;
	
 	Message (byte[] msg) {
		this.msg = msg;
		this.hex = new String[msg.length];
		this.bin = new String[msg.length];
	}
	Message (String[] bin) {
		this.msg = null;
		this.bin = bin;
		this.hex = new String[bin.length];
	}
	Message (String[] hex, int base) {
		this.msg = null;
		this.hex = hex;
		this.bin = new String[hex.length];
	}
	
	
	// methode
	void convertDecToHex() {
		for (int i = 0; i < msg.length; i++) {
			String s = Integer.toHexString(msg[i]).toUpperCase();
			if (msg[i] <= 15)
				s = "0" + s;
			hex[i] = s;
		}
	}
	void convertDecToBin() {
		for (int i = 0; i < msg.length; i++) {
			String s = Integer.toBinaryString(msg[i]);
			bin[i] = remplirZero(s); 
		}
	}
	void convertBinToHex() {
		for (int i = 0; i < bin.length; i++) {
			String binary = bin[i];
			String f = "";
			int pow = 3, res = 0;
			for (int j = 0; j < binary.length(); j++) {
				res += Integer.parseInt(binary.substring(j, j + 1)) * Math.pow(2, pow);
				pow--;
				if (pow == -1) {
					if (res == 0)
						f = f + "0";
					else
						f = f + Integer.toHexString(res).toUpperCase(); 
					pow = 3;
					res = 0;
				}
			}
			hex[i] = f;
		}
	}
	void convertHexToBin() {
		for (int i = 0; i < hex.length; i++) {
			String binary = hex[i];
			int res =  convertToNbr(binary.substring(0, 1)) * 16 + convertToNbr(binary.substring(1, 2)); 
			bin[i] = remplirZero(Integer.toBinaryString(res));
		}
	}
	int[] convertHexToDec() {
		int[] arr = new int[hex.length];
		for (int i = 0; i < hex.length; i++) {
			String binary = hex[i];
			int res =  convertToNbr(binary.substring(0, 1)) * 16 + convertToNbr(binary.substring(1, 2));
			arr[i] = res;
		}
		return arr;
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
	private int convertToNbr(String val) {
		switch (val) {
		case "A": 
			return 10;
		case "B": 
			return 11;
		case "C": 
			return 12;
		case "D": 
			return 13;
		case "E": 
			return 14;
		case "F": 
			return 15;
		default:
			return Integer.parseInt(val);
		}
	}
	
	// setters & getters
	public String[] getHex() {
		return hex;
	}
	public String[] getBin() {
		return bin;
	}
}
