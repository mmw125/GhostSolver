import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Helper {

	public Helper() {
		System.out.println("Importing data file");
		DataHolder holder;
		try {
			holder = new DataHolder();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		Scanner sc = new Scanner(System.in);
		String current = "";
		while (true) {
			String next = sc.nextLine();
			if (next.isEmpty()) {
				current = "";
			} else {
				if (next.startsWith("^")) {
					current = next.charAt(next.length() - 1) + current;
				} else {
					current += next.charAt(next.length() - 1);
				}

				System.out.println(current);
				
				if (holder.exactLookup(current) && current.length() >= 4) {
					System.err.println("FINISHED A WORD");
					current = "";
				} else {
					System.err.print("POSSIBLE WORDS");
					Set<Character> badAppends = new HashSet<Character>();
					badAppends.add('ñ');
					for (String str : holder.exactDatabaseLookup(current + "*")) {
						System.out.print(str + " ");
						if (str.length() >= 4) {
							badAppends.add(str.charAt(str.length() - 1));
						}
					}

					Set<Character> badStartAppends = new HashSet<Character>();
					badStartAppends.add('ñ');
					for (String str : holder.exactDatabaseLookup("*" + current)) {
						System.out.print(str + " ");
						if (str.length() >= 4) {
							badStartAppends.add(str.charAt(0));

						}
					}
					System.out.println();
					
					HashMap<Character, String> possiblePrepends = new HashMap<Character, String>();
					HashMap<Character, String> possibleAppends = new HashMap<Character, String>();

					for (String str : holder.databaseLookup(current)) {
						if (str.length() < 4) {
							continue;
						}
						int prependIndex = str.indexOf(current) - 1;
						int appendIndex = prependIndex + current.length() + 1;

						if (prependIndex >= 0) {
							char prependChar = str.charAt(prependIndex);
							if (!badStartAppends.contains(prependChar)) {
								possiblePrepends.putIfAbsent(prependChar, str);
							}
						}
						
						if (appendIndex < str.length()) {
							char appendChar = str.charAt(appendIndex);
							if (!badAppends.contains(appendChar)) {
								possibleAppends.putIfAbsent(appendChar, str);
							}
						}
					}
					System.out.println("Prepend: ");
					for (Character c : possiblePrepends.keySet() ) {
						System.out.println("\t" + c + " " + possiblePrepends.get(c));
					}
					
					System.out.println("Append: ");
					for (Character c : possibleAppends.keySet() ) {
						System.out.println("\t" + c + " " + possibleAppends.get(c));
					}
						
					System.out.println();
				}
			}
		}
	}

	public static void main(String[] args) {
		new Helper();
	}
}
