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
		// make all character in one String
		String seqBin = "";
		for (String s : super.bin) 
			seqBin +=  s;
		// count capacity the new expand arr
		int maxSize = (int)Math.ceil(seqBin.length() / 3.0);
		expandBin = new String[maxSize];
		// traiter
		int j = 0, nbr = 0;
		expandBin[j] = "";
		for (int i = 0; i < seqBin.length(); ) {
			if (nbr == 3) {
				expandBin[j] += "..";
				j++;
				if (j < expandBin.length)
					expandBin[j] = "";
				nbr = 0;
			}
			else if (j < expandBin.length){
				expandBin[j] += String.valueOf(seqBin.charAt(i)) + String.valueOf(seqBin.charAt(i));
				nbr++;
				i++;
			}
		}
		// traiter last case
		String s = expandBin[expandBin.length - 1];
		for (int i = s.length(); i < 8; i++)
			expandBin[expandBin.length - 1] += ".";
	}
	void parity() {
		System.out.print("\nparity: ");
		parityBin = new String[expandBin.length];
		int[] arr = new int[3];
		for (int i = 0; i < expandBin.length; i++) {
			arr[0] = Integer.parseInt(expandBin[i].substring(0, 1));
			
			if (expandBin[i].substring(2, 3).equals("."))
				arr[1] = 0;
			else
				arr[1] = Integer.parseInt(expandBin[i].substring(2, 3));
			if (expandBin[i].substring(4, 5).equals("."))
				arr[2] = 0;
			else
			arr[2] = Integer.parseInt(expandBin[i].substring(4, 5));
			
			int res = arr[0] ^ arr[1] ^ arr[2];
			String s = expandBin[i].substring(0,6) + String.valueOf(res) + String.valueOf(res);
			parityBin[i] = s;
		}
		String s = parityBin[parityBin.length - 1].replace('.', '0');
		parityBin[parityBin.length - 1] = s;
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