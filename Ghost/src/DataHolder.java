import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class DataHolder {
	private ArrayList<String> strings = new ArrayList<String>();
	private Set<String> stringSet = new HashSet<String>();
	
	public DataHolder() throws IOException {
		Scanner scanner = new Scanner(new File("US.dic"));
		while(scanner.hasNextLine()) {
			String s = scanner.nextLine();
			strings.add(s);
			stringSet.add(s);
		}
		scanner.close();
	}
	
	public boolean exactLookup(String lookup) {
		return stringSet.contains(lookup);
	}
	
	public ArrayList<String> databaseLookup(String lookup) {
		ArrayList<String> returnVal = new ArrayList<String>();
		for(String str : strings) {
			if (str.contains(lookup) && str.length() > lookup.length() + 1) {
				returnVal.add(str);
			}
		}
		return returnVal;
	}
	
	public ArrayList<String> exactDatabaseLookup(String lookup) {
		ArrayList<String> returnVal = new ArrayList<String>();
		for(String str : strings) {
			if(str.length() == lookup.length()) {
				boolean works = true;
				for(int i = 0; i < lookup.length(); i++) {
					if(lookup.charAt(i) != '*' && lookup.charAt(i) != str.charAt(i)) {
						works = false;
					}
				}
				if(works) {
					returnVal.add(str);
				}
			} else if (str.length() > lookup.length()) {
				return returnVal;
			}
		}
		return returnVal;
	}
}
