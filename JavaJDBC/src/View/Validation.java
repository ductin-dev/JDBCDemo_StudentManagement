package View;

import java.util.Vector;

public class Validation {

    public static boolean checkIdFormat(String id) {
	if (id.matches("[A-Za-z0-9]+")) return true;
	return false;
    }

    public static boolean checkNameFormat(String name) {
	if (name.matches("[A-Za-z0-9 ]+")) return true;
	return false;
    }

    public static boolean checkIntFormat(String n) {
	if (n.matches("[0-9]+")) return true;
	return false;
    }

    public static boolean checkIntRange(int a, int b, String n) {
	int number = Integer.parseInt(n);
	if (number <= a || number >= b) return false;
	return true;
    }

    public static boolean checkIdExited(Vector<Vector<String>> data, String id) {
	for (Vector<String> i : data) {
	    if (i.get(0).equals(id)) return true;
	}
	return false;
    }

    public static boolean checkPhoneFormat(String phone) {
	if (phone.matches("[0\\d{9}]") && phone.length() == 10) return true;
	return false;
    }
}