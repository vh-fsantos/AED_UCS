import java.io.IOException;
import java.util.Scanner;


public class Main {
	
	public static void main(String[] args) throws IOException {
		Archive a = new Archive();
		Scanner keyboard = new Scanner(System.in);
		 
		int option;

		a.executeFunctions();
		menu();

		do {
			option = keyboard.nextInt();
			keyboard.nextLine();
			switch (option) {
				case 0:
					break;
				case 1:
					System.out.println("Please enter a date (yyyymmdd):");
					String date = keyboard.next();
					a.generateDataFromDate(date);
					System.out.println();	
					menu();
					break;
				case 2:
					System.out.println("Please enter an id:");
					String idTweet = keyboard.next();
					a.searchIdInIndexFile(idTweet);
					System.out.println();
					menu();
					break;
				case 3:
					System.out.println("Please enter a hashtag:");
					String hashtag = keyboard.next();
					a.searchHashtag(hashtag);
					System.out.println();
					menu();
					break;
				case 4:
					System.out.println("Please enter an user:");
					String user = keyboard.next();
					a.allTweetsRegistries.central();
					Node n = a.allTweetsRegistries.pesqRec(user);
					if(n!=null) {
						System.out.print(n.getTr().id_tweet);
						System.out.print("\t");
						System.out.print(n.getTr().tweet_text);
						System.out.print("\t");
						System.out.print(n.getTr().usuario);
						System.out.print("\t");
						System.out.print(n.getTr().date);
						System.out.print("\t");
						System.out.print(n.getTr().localizacao);
						System.out.print("\t");
						System.out.print(n.getTr().hashtags);
					}
					else {
						System.out.println("User not found!");
					}
					System.out.println();
					menu();
					break;
				}
		} while (option != 0);

		System.out.println("Goodbye!");
		keyboard.close();
	}

	private static void menu() {
		System.out.println("CHOOSE AN OPTION:\n" + "   1 - Search tweets by date\n" + "   2 - Search by id (index file)\n"
				+ "   3 - Search by hashtag (index file)\n"
				+ "   4 - Search by user\n" + "   0 - Leave Program");
	}
}
		
		


