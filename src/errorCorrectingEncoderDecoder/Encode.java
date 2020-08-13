package errorCorrectingEncoderDecoder;

public class Encode extends Mode{
	private String[] expandBin;
	private String[] parityBin;
	private String[] hexMsg;
	private Message msg;
	
	Encode(String nameFileInput, String nameFileOutput) {
		super(nameFileInput, nameFileOutput);
	}
	
	public void code() {
		super.work();

		expand();
		printArr(expandBin);
		parity();
		printArr(parityBin);
		resultat();
		printArr(hexMsg);
		super.saveDataToFile(dataByte());
	}
	
	void expand() {
		System.out.print("expand: ");
		expandBin = new String[super.bin.length * 2];
		
		// make all character in one String
				String seqBin = "";
				for (String s : super.bin) 
					seqBin +=  s;
		int k = 0, i=0;
		while (i < seqBin.length()) {
			String s = ".." + seqBin.charAt(i) + "." +
					seqBin.charAt(i+1) + seqBin.charAt(i+2) + seqBin.charAt(i+3) + ".";
			expandBin[k] = s;
			k++;
			i +=4;
		}
	}
	void parity() {
		System.out.print("\nparity: ");
		parityBin = new String[expandBin.length];
		for (int i = 0; i < expandBin.length; i++) {
			StringBuilder newStr = new StringBuilder(expandBin[i]);
			int p = 0;
			for (int j = 1; j <= 8; ) {
				char res = findVal(expandBin[i], j);
				newStr.setCharAt(j - 1, res);
				j = (int)Math.pow(2, ++p);
			}
			parityBin[i] = newStr.toString();
		}
	}
	private char findVal(String s, int nextSeq) {
		int nbr = 0, occ = nextSeq;
		for (int i = nextSeq - 1; i < s.length();i++) {
			occ--;
			if (s.charAt(i) == '1') 
				nbr++;
			if (occ == 0) {
				i += nextSeq;
				occ = nextSeq;
			}
		}
		char res = nbr % 2 == 0 ? '0' : '1';
		return res;
	}
	void resultat() {
		System.out.print("\nhex view: ");
		msg = new Message(parityBin);
		msg.convertBinToHex();
		hexMsg = msg.getHex();
	}
	void printArr(String[] arr) {
		for (String s : arr) 
			System.out.print(s + " ");
	}

	int[] dataByte() {
		Message m = new Message(hexMsg, 16);
		int[] arr = m.convertHexToDec();
		return arr;
	}
}