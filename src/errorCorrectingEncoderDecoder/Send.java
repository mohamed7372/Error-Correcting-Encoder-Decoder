package errorCorrectingEncoderDecoder;

import java.util.Random;

public class Send extends Mode{
	private String[] binRec;
	private String[] hexRec;
	private String[] hexMsg;
	private String[] binMsg;
	private Message msg;
	
	Send(String nameFileInput, String nameFileOutput) {
		super(nameFileInput, nameFileOutput);
	}
	
	public void code() {
		super.work();

		System.out.print("bin view: ");
		makeError();
		printArr(binRec);
		System.out.print("\nhex view: ");
		msg = new Message(binRec);
		msg.convertBinToHex();
		hexRec = msg.getHex();
		printArr(hexRec);
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
	void makeError() {
		Random r = new Random(0);
		binRec = new String[super.hexInput.length];
		for (int i = 0; i < super.bin.length; i++) {
			StringBuilder s = new StringBuilder(super.bin[i]);
			int pos = r.nextInt(8);
			int newChar = 0;
			if (Integer.parseInt(s.substring(pos, pos + 1)) == 0)
				newChar = 1;
			s.setCharAt(pos, String.valueOf(newChar).charAt(0));
			binRec[i] = s.toString();
		}
	}
	void printArr(String[] arr) {
		for (String s : arr) 
			System.out.print(s + " ");
	}
	int[] dataByte() {
		Message m = new Message(hexRec, 16);
		int[] arr = m.convertHexToDec();
		return arr;
	}
}
