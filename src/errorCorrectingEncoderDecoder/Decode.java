package errorCorrectingEncoderDecoder;

public class Decode extends Mode{
	private String[] correct;
	private String[] decode;
	private String[] remove;
	private String[] hexCorr;
	private String[] hexMsg;
	private String[] binMsg;

	Decode(String nameFileInput, String nameFileOutput) {
		super(nameFileInput, nameFileOutput);
	}
	
	public void code() {
		super.work();

		System.out.print("correct: ");
		correctBin();
		printArr(correct);
		System.out.print("\ndecode: ");
		decode();
		printArr(decode);
		System.out.print("\nremove: ");
		remove();
		printArr(remove);
		System.out.print("\nhex view: ");
		convert();
		printArr(hexCorr);
		String text = convertToText();
		System.out.print("\ntext view: " + text);
		
		super.saveDataToFile(dataByte());
	}
	
	@Override
	void work() {
		printTitle();
		hexMsg = super.msgInFile().trim().split(" ");
		System.out.print("hex view: ");
		printArr(hexMsg);
		Message m = new Message(hexMsg, 16);
		m.convertHexToBin();
		binMsg = m.getBin();
		System.out.print("\nbin view: ");
		printArr(binMsg);
	}
	void correctBin() {
		correct = new String[super.bin.length];
		int pos = 0;
		for (int i = 0; i < super.bin.length; i++) {
			String s = super.bin[i];
			int k =0;
			int[] arr = new int[3];
			int j =0;
			while (j < s.length()) {
				if (s.substring(j, j+1).equals(s.substring(j+1, j+2))) {
					int a = Integer.parseInt(s.substring(j, j+2));
					arr[k] = a;
					k++;
				}
				else {
					pos = j;
				}
				j+=2;
			}
			int res = arr[0] ^ arr[1] ^ arr[2];
			String resStr = "";
			if (res == 0)
				resStr = "00";
			else if (res == 1)
				resStr = "01";
			else 
				resStr = String.valueOf(res);
			StringBuilder sb = new StringBuilder(s);
			sb.replace(pos, pos + 2, resStr);
			correct[i] = sb.toString();
		}
	}
	void decode() {
		StringBuilder s = new StringBuilder("");
		for (int i = 0; i < correct.length; i++) {
			String b = correct[i];
			for (int j = 0; j < b.length() - 3; j+=2) {
				s.append(b.charAt(j));
			}
		}
		int size = (int)Math.ceil((correct.length * 3.0) / 8.0);
		decode = new String[size];
		int k = 0, i = 0;
		while(i < s.length()) {
			if (s.length() - i - 8 >= 0) {
				decode[k] = s.substring(i, i+8);
				i += 8;
				k++;
			}
			else
				break;
		}
		if (i < s.length())
			decode[k] = s.substring(i);
	}
	void remove() {
		int size = decode.length;
		if (decode[decode.length - 1].length() < 8)
			size--;
		remove = new String[size];
		for (int i = 0; i < remove.length; i++) {
			remove[i] = decode[i];
		}
	}
	void convert() {
		Message msg = new Message(remove);
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
