package errorCorrectingEncoderDecoder;

public class Decode extends Mode{
	private String[] correct;
	private String[] decode;
	private String[] hexCorr;

	Decode(String nameFileInput, String nameFileOutput) {
		super(nameFileInput, nameFileOutput);
	}
	
	public void code() {
		super.work();

		//super.bin = new String[]{"01011010", "10001000", "10001100", "01001110", "00010110" ,"10100110", "00111110", "10010000"};
		System.out.print("correct: ");
		correctBin();
		printArr(correct);
		System.out.print("\ndecode: ");
		decode();
		printArr(decode);
		System.out.print("\nhex view: ");
		convert();
		printArr(hexCorr);
		String text = convertToText();
		System.out.print("\ntext view: " + text);
		
		super.saveDataToFile(dataByte());
	}
	
	void correctBin() {
		correct = new String[super.bin.length];
		for (int i = 0; i < super.bin.length; i++) {
			int p = 0, total = 0;
			for (int j = 1; j <= 8; ) {
				char res = findVal(super.bin[i], j);
				if (res != super.bin[i].charAt(j - 1))
					total += j;
				j = (int)Math.pow(2, ++p);
			}
			StringBuilder reslt = new StringBuilder(super.bin[i]);
			if(reslt.charAt(total - 1) == '1')
				reslt.setCharAt(total - 1, '0');
			else
				reslt.setCharAt(total - 1, '1');
			correct[i] = reslt.toString();
		}
	}
	private char findVal(String s, int nextSeq) {
		int nbr = 0, occ = nextSeq;
		for (int i = nextSeq - 1; i < s.length();i++) {
			if (i < s.length() && s.charAt(i) == '1') 
				nbr++;
			//System.out.print(s.charAt(i) + " ");
			occ--;
			if (occ == 0) {
				i += nextSeq;
				occ = nextSeq;
			}
		}
		if (s.charAt(nextSeq - 1) == '1')
			nbr--;
		char res = nbr % 2 == 0 ? '0' : '1';
		return res;
	}
	void decode() {
		StringBuilder sb = new StringBuilder("");
		decode = new String[correct.length / 2];
		for (int i = 0; i < correct.length; i++) {
			String s = correct[i];
			for (int j = 0; j < s.length(); j++) {
				if (j != 0 && j != 1 && j != 3 && j != 7)
					sb.append(s.charAt(j));
			}
		}
		int k = 0;
		for (int i = 0; i < sb.length(); i+=8) {
			decode[k] = sb.substring(i, i + 8);
			k++;
		}
	}
	void convert() {
		Message msg = new Message(decode);
		msg.convertBinToHex();
		hexCorr = msg.getHex();
	}
	String convertToText() {
		String s = "";
		Message msg = new Message(hexCorr, 16);
		msg.convertHexToDec();
		int[] arr = msg.convertHexToDec();
		for (int i = 0; i < arr.length; i++) {
			s += (char)arr[i];
		}
		return s;
	}
	void printArr(String[] arr) {
		for (String s : arr) 
			System.out.print(s + " ");
	}
	int[] dataByte() {
		Message m = new Message(hexCorr, 16);
		int[] arr = m.convertHexToDec();
		return arr;
	}
}
