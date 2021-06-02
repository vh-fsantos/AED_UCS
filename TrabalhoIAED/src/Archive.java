import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Archive {
	public Tree allTweetsRegistries = new Tree(); //usuario
	
	public List<TweetRegistry> TweetRegistries = new ArrayList<>();
	
	public Map<String, Tweets> Indexes = new HashMap<>(); //data
	
	private final static int size_line_index = 19;
	
	public char[] id = new char[19];
	
	public Archive() {
		
	}
	
	public void executeFunctions() {
		File f1 = new File("FileHashtagsTweets.txt");
		File f2 = new File("FileIndexTweets.txt");
		
		if (!f1.exists() && !f2.exists()) {
			System.out.println("Reading database and generating files...");
			GenerateTweetRegistries();
			CreateFileTweetsHashtags();
			CreateFileTweetsIndexes();
		}
		else {
			System.out.println("Indexes Files already created, generating memory indexes");
			GenerateTweetRegistries();
		}
	}
	
	public void GenerateTweetRegistries() {
		File dataBase = new File("data.txt");
		
		if(dataBase.exists()) {
			try {
				FileReader readDataBase = new FileReader("data.txt");
				BufferedReader readArq = new BufferedReader(readDataBase);
				String line;
				do {
					line = readArq.readLine();
					TweetRegistry tr = new TweetRegistry();
					Tweets t = new Tweets();
					
					int indexText = 0;
					int indexUser = 0;
					int indexDate = 0;
					int indexLoc = 0;
					int indexHashtag = 0;
				
					for(int i = 0; i < line.length(); i++) {
						if(i < 19) {
							tr.id_tweet[i] = line.charAt(i);
						}
						else if(i < 299) {
							tr.tweet_text[indexText] = line.charAt(i);
							indexText++;
						}
						else if(i < 319) {
							tr.usuario[indexUser] = line.charAt(i);
							indexUser++;
						}
						else if(i < 327) {
							tr.date[indexDate] = line.charAt(i);
							indexDate++;
						}
						else if(i < 377) {
							tr.localizacao[indexLoc] = line.charAt(i);
							indexLoc++;
						}
						else {
							tr.hashtags[indexHashtag] = line.charAt(i);
							indexHashtag++;
						}
						
					}
					allTweetsRegistries.insertTr(tr);
					
					TweetRegistries.add(tr);
					
					String date = new String(tr.date);
					if(Indexes.containsKey(date)) {
						Indexes.get(date).allRegistries.add(tr);	
						Indexes.get(date).qtd_tweets++;
					}
					else {
						t.allRegistries.add(tr);
						t.qtd_tweets++;	
						Indexes.put(date,t);
					}
					
					id = tr.id_tweet;
	
				}while(line != null); 
				
					readArq.close();
					
				}catch(IOException ioe){
					System.out.println("File not Found");
				}catch(NullPointerException npe){
					System.out.println("All data file has been read!");
				}catch(ArrayIndexOutOfBoundsException ex) {
					System.out.println(id);
				}
			
		}
	}
	
	public void generateDataFromDate(String date) {
		try {
			System.out.println("On date " + date + " were found " + Indexes.get(date).qtd_tweets + " tweets about Covid-19.");
			
			
			System.out.println("Showing first 50 registries in this date");
			for (int i = 0; i < 50; i++) {
				System.out.println(Indexes.get(date).allRegistries.get(i).id_tweet);	
			}
			
		}
		catch (NullPointerException npe){
			System.out.println("The typed date doesn't exist in database! Please type another one.");
		}
	}
	
	public void CreateFileTweetsIndexes() {
		List<String> idList = new ArrayList<>();
		
		int i = 0;
		
		do {
			String idTweet = new String(TweetRegistries.get(i).id_tweet);
			idList.add(idTweet);

			i += 1;
			
		}while(i < TweetRegistries.size());
		
		File f = new File("FileIndexTweets.txt");
		if (f.exists()) {
			return; 
		} else {
			try {
				FileWriter fw = new FileWriter("FileIndexTweets.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				
				for(int j = 0; j < idList.size(); j++) {
					bw.append(idList.get(j));
					bw.append("\n");
				}
				
				bw.close();
				fw.close();
			} catch (IOException ioe) {
				System.out.println("File not found.");
			}
		}
	}
	
	public void CreateFileTweetsHashtags() {
		List<String> hashtags = new ArrayList<>();
		
		int i = 0;
		
		do {
			String hashtagTweet = new String(TweetRegistries.get(i).hashtags);
			
			String data[] = hashtagTweet.split(" ");
			
			String hashtagDetached[] = data[0].split("#");
			
			for (int j = 1; j < hashtagDetached.length; j++) {
				if(!hashtags.contains(("#").concat(hashtagDetached[j]).toUpperCase())) {
					hashtags.add(("#").concat(hashtagDetached[j]).toUpperCase());
				}
			}
			i++;
			
		}while(i < TweetRegistries.size());
		
		File f = new File("FileHashtagsTweets.txt");
		if (f.exists()) {
			return; 
		} else {
			try {
				FileWriter fw = new FileWriter("FileHashtagsTweets.txt");
				BufferedWriter bw = new BufferedWriter(fw);
				
				for(int j = 0; j < hashtags.size(); j++) {
					bw.append(hashtags.get(j));
					bw.append(" | ");
					for(int k = 0; k < TweetRegistries.size(); k++) {
						String hashtagTr = new String (TweetRegistries.get(k).hashtags);
						String teste = hashtagTr.toUpperCase();
						if (teste.indexOf(hashtags.get(j).concat("#")) != -1 || 
								teste.indexOf(hashtags.get(j).concat(" ")) != -1) {
							String idTweet = new String(TweetRegistries.get(k).id_tweet);
							bw.append(idTweet);
							bw.append(';');
						}
					}
					bw.append("\n");
				}
				
				bw.close();
				fw.close();
			} catch (IOException ioe) {
				System.out.println("File not found.");
			}
		}
	}
	
	public void searchIdInIndexFile(String idTweet) {
		long mid;
		int comp = 0;
		int middle_line, start_line, end_line;
		boolean found = false;

		try {
			RandomAccessFile archive = new RandomAccessFile("FileIndexTweets.txt", "r");
				start_line = 1;
				middle_line = (int) (archive.length() / (size_line_index * 2)) + 1;
				end_line = (int) (archive.length() / size_line_index);
				mid = (middle_line - 1) * (size_line_index + 1);
				found = searchKey(archive, mid, comp, start_line, middle_line, end_line, idTweet, found);

				if (!found) {
					System.err.println("Key not found.");
				}
				else {
					for (int i = 0; i < TweetRegistries.size(); i++) {
						String idTweetRun = new String(TweetRegistries.get(i).id_tweet);
						if (idTweetRun.equalsIgnoreCase(idTweet) ) {
							System.out.println("Tweet Registry:");
							System.out.print(TweetRegistries.get(i).id_tweet);
							System.out.print("\t");
							System.out.print(TweetRegistries.get(i).tweet_text);
							System.out.print("\t");
							System.out.print(TweetRegistries.get(i).usuario);
							System.out.print("\t");
							System.out.print(TweetRegistries.get(i).date);
							System.out.print("\t");
							System.out.print(TweetRegistries.get(i).localizacao);
							System.out.print("\t");
							System.out.print(TweetRegistries.get(i).hashtags);
						}
					}
				}

				archive.close();
			} catch (IOException ioe) {
				System.err.println("File not found!");
		}
	}
	
	private boolean searchKey(RandomAccessFile archive, long mid, int comp, int start_line, int middle_line,
			int end_line, String data, boolean found) throws IOException {
		do {
			try {
				comp += 1;
				archive.seek(mid);

				String read = archive.readLine();	
					
				if (read.compareToIgnoreCase(data) == 0) {
					found = true;

				} else if (read.compareToIgnoreCase(data) < 0) {
					start_line = middle_line;
					if (end_line - start_line == 1) {
						middle_line++;
					} else {
						middle_line = ((end_line - start_line) / 2) + start_line;
					}
					mid = (middle_line - 1) * (size_line_index + 1);
				} else if (read.compareToIgnoreCase(data) > 0) {
					end_line = middle_line;
					if (end_line - start_line == 1) {
						middle_line--;
					} else {
						middle_line = ((end_line - start_line) / 2) + start_line;
					}
					mid = (middle_line - 1) * (size_line_index + 1);
				}
			} catch (IOException ioe) {
				System.err.println("File not found!");
			}
		} while (comp < (int) archive.length() / size_line_index && found == false);
																						
		return found;
	}
	
	public void searchHashtag(String hashtag) {
		String hashtagIds = searchHashtagInIndexFile(hashtag);
		
		if (hashtagIds == "") {
			System.out.println("Hashtag not found!");
		}
		else {
			String[] dettachedHashtag = hashtagIds.split(";");
			System.out.println("Passou");
			for (int i = 0; i < dettachedHashtag.length; i++) {
				System.out.println(dettachedHashtag[i]);
				for (int j = 0; j < TweetRegistries.size(); j++) {
					String idTweetRunning = new String (TweetRegistries.get(j).id_tweet);
					if (idTweetRunning.equalsIgnoreCase(dettachedHashtag[i])) {
						System.out.println("Tweet Registry:");
						System.out.print(TweetRegistries.get(j).id_tweet);
						System.out.print("\t");
						System.out.print(TweetRegistries.get(j).tweet_text);
						System.out.print("\t");
						System.out.print(TweetRegistries.get(j).usuario);
						System.out.print("\t");
						System.out.print(TweetRegistries.get(j).date);
						System.out.print("\t");
						System.out.print(TweetRegistries.get(j).localizacao);
						System.out.print("\t");
						System.out.print(TweetRegistries.get(j).hashtags);
					}
				}
			}
		}
		
		

	}
	
	
	public String searchHashtagInIndexFile(String hashtag) {

		try {
			FileReader readDataBase = new FileReader("FileHashtagsTweets.txt");
			BufferedReader readArq = new BufferedReader(readDataBase);
			String[] line;
			do {
				line = readArq.readLine().split(" | ");
				if(line[0].equalsIgnoreCase(hashtag)) {
					System.out.println(hashtag);
					return line[2];
				}
				
			}while(line != null); 
			
			readArq.close();
				
			}catch(IOException ioe){
				System.out.println("File not Found");
			}catch(NullPointerException npe){
				return "";
			}
		
		return "";
	}
}

