package encryption;

import java.util.*;
import java.io.*;

class InvalidKeyException extends Exception {
	public InvalidKeyException(String e) {
		new Exception(e);
	}
}

public class Encryption {
	Scanner sc;
	String key, message;

	public Encryption() {
		sc = new Scanner(System.in);
		key = message = "";
	}

	public void work() {
		try {
			while (true) {
				char choice;
				System.out.println("\n*****************************************************");
				System.out.println("Enter your choice." + "\nEnter n for entering key." + "\nEnter e to encrypt."
						+ "\nEnter d to decrypt." + "\nEnter q to quit.");
				choice = Character.toLowerCase(sc.nextLine().charAt(0));
				switch (choice) {
				case 'n': {
					System.out.println("Enter the key: ");
					key = sc.nextLine().toUpperCase();
					if (keyCheck(key))
						throw new InvalidKeyException(key);
					break;
				}
				case 'e': {
					System.out.println("Enter the message: \n");
					message = sc.nextLine().toUpperCase();
					if (decrypt(message, key).equalsIgnoreCase("-1"))
						System.out.println("ERROR!!!\nNO KEY ENTERED!!!");
					else
						System.out.println("\nEncrypted message: \n" + encrypt(message, key));
					break;
				}
				case 'd': {
					System.out.println("Enter the encrypted message: \n");
					message = sc.nextLine().toUpperCase();
					if (decrypt(message, key).equalsIgnoreCase("-1"))
						System.out.println("ERROR!!!\nNO KEY ENTERED!!!");
					else
						System.out.println("Decrypted message: " + decrypt(message, key));
					break;
				}
				case 'q': {
					return;
				}
				default:
					System.out.print("Invalid Input!!\nPlease enter again!!");
				}
			}

		} catch (InvalidKeyException e) {
			System.out.print("Invalid key!!!!");
		}
	}

	private String encrypt(String mes, String key) {
		key = keyExtend(mes, key);
		if (key.equalsIgnoreCase("-1"))
			return "-1";
		int i, temp;
		String res = "";
		String check = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (i = 0; i < mes.length(); i++) {
			if (Character.isDigit(mes.charAt(i))) {
				res += mes.charAt(i);
				continue;
			}
			if (Character.isWhitespace(mes.charAt(i))) {
				res += mes.charAt(i);
				continue;
			}
			temp = (check.indexOf(mes.charAt(i)) + check.indexOf(key.charAt(i)));
			if (temp >= 26)
				temp -= 26;
			res += check.charAt(temp);
		}
		return res;
	}

	private String decrypt(String mes, String key) {
		key = keyExtend(mes, key);
		if (key.equalsIgnoreCase("-1"))
			return "-1";
		int i, temp;
		String res = "";
		String check = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (i = 0; i < mes.length(); i++) {
			if (Character.isDigit(mes.charAt(i))) {
				res += mes.charAt(i);
				continue;
			}
			if (Character.isWhitespace(mes.charAt(i))) {
				res += mes.charAt(i);
				continue;
			}
			temp = (check.indexOf(mes.charAt(i)) - check.indexOf(key.charAt(i)));
			if (temp < 0)
				temp += 26;
			res += check.charAt(temp);
		}
		return res;
	}

	private boolean keyCheck(String key) {
		for (int i = 0; i < key.length(); i++) {
			if ("0123456789".indexOf(key.charAt(i)) != -1)
				return true;
			if (!Character.isLetterOrDigit(key.charAt(i)) && !Character.isWhitespace(key.charAt(i)))
				return true;
		}
		return false;
	}

	private String keyExtend(String mes, String key) {
		if (key == "")
			return "-1";
		int i = 0;
		String k = "";
		for (;;) {
			if (i == key.length()) {
				i = 0;
				continue;
			}
			if (k.length() == mes.length())
				break;
			k += key.charAt(i);
			i++;
		}
		return k;
	}

}
