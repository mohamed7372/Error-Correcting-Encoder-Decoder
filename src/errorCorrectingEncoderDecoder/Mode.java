package errorCorrectingEncoderDecoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class Mode {
	private String pathName = "C:\\Users\\HP\\eclipse-work\\site jet brains\\src\\errorCorrectingEncoderDecoder\\";
	private String nameFileInput;
	String nameFileOutput;
	private byte[] inputFile;
	String[] bin;
	String[] hexInput;
	private Message message;
	
	Mode (String namefileInput, String nameFileOutput) {
		this.nameFileInput = namefileInput;
		this.nameFileOutput = nameFileOutput;
		try {
			inputFile = Files.readAllBytes(Paths.get(this.pathName + nameFileInput));
			bin = new String[inputFile.length];
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void work() {
		printTitle();
		if (nameFileInput.equals("send.txt")) 
			printContentFile();
		hexView();
		binView();
		
		System.out.println("\n" + nameFileOutput + ": ");
	}
	
	void printTitle() {
		System.out.println(this.nameFileInput + ":");
	}
	void printContentFile() {
		try {
			
			System.out.println("text view: " + Files.readString(Paths.get(this.pathName + nameFileInput)));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	void hexView() {
		System.out.print("hex view: ");
		message = new Message(inputFile);
		message.convertDecToHex();
		String[] arr = message.getHex();
		hexInput = new String[arr.length];
		for (int j = 0; j < arr.length; j++) {
			if(arr[j].length() == 9) 
				hexInput[j] = arr[j].substring(7);
			else
				hexInput[j] = arr[j];
		}
		for (String b : hexInput) 
			System.out.print(b + " ");
		System.out.println();
	}
	void hexView2() {
		System.out.print("hex view: ");
		message = new Message(inputFile);
		message.convertDecToHex();
		String[] arr = message.getHex();
		hexInput = new String[arr.length - 1];
		for (int j = 0; j < arr.length - 1; j++) {
			if(arr[j].length() == 9) 
				hexInput[j] = arr[j].substring(7);
			else
				hexInput[j] = arr[j];
		}
		for (String b : hexInput) 
			System.out.print(b + " ");
		System.out.println();
	}
	
	void binView() {
		System.out.print("bin view: ");
		message = new Message(hexInput, 16);
		message.convertHexToBin();
		bin = message.getBin();
		for (String b : bin) 
			System.out.print(b + " ");
		System.out.println();
	}
	
	String msgInFile() {
		String s = "";
		try {
			s = Files.readString(Paths.get(this.pathName + nameFileInput));
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	void saveDataToFile(int[] data) {
		try {
			FileOutputStream fo = new FileOutputStream(new File(pathName + nameFileOutput));
			for (int i : data) {
				fo.write(i);
			}
			fo.close();
			//FileWriter fw = new FileWriter(new File(pathName + nameFileOutput));
			//fw.write(data);
			//fw.write("\n");
			//fw.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
