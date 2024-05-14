import java.util.LinkedList;
import java.util.Scanner;

public class MainTest {
    public static void main (String[] args){
     
     	MusicApp iMusic = new MusicApp();
        Scanner scanner = new Scanner(System.in);
        
        int zgjedhja;
        
        do {
        	
            System.out.println(" \n************ Welcome to CommandTune ************");
            System.out.println("\n\t1. Add a song:");
            System.out.println("\t2. Delete a song:");
            System.out.println("\t3. Change a song:");
            System.out.println("\t4. Search by genre:");
            System.out.println("\t5. Search by artist:");
            System.out.println("\t6. Listen to music:");
            System.out.println("\t7. Listen to your favourite music:");
            System.out.println("\t8. Add a song to your favourites:");
            System.out.println("\t9. Delete a song from your favourites:");
            System.out.println("\t10. Print your song list:");
            System.out.println("\t11. Print your favourite");
            System.out.println("\t12. Undo ");
            System.out.println("\t13. Redo ");
            System.out.println("\t14. Save your song's list to a file: ");
            System.out.println("\t15. Read songs from a file: ");
            System.out.println("\t16. Save your favourite songs to a file: ");
            System.out.println("\t17. Read your favourite songs from a file: ");
            System.out.println("\t18. Exit");
            System.out.print("\n\n\tChoose your option: ");
            zgjedhja = scanner.nextInt();

            switch (zgjedhja) 
            {
                case 1:

                    scanner.nextLine(); 
                    System.out.print("\tSelect the title: ");
                    String title = scanner.nextLine();
                    System.out.print("\tSelect the artist: ");
                    String artist = scanner.nextLine();
                    System.out.print("\tSelect the genre: ");
                    String genre = scanner.nextLine();
                    System.out.print("\tSet the duration (in seconds): ");
                    int duration = scanner.nextInt();
                    CommandTune.addSong(title, artist, genre, duration);
                    System.out.println("\tSong added successfully!");
                    break;
                    
                    
                case 2:
                    System.out.print("\tEnter the track number of the song you want to delete: ");
                    int index = scanner.nextInt()-1;
                    CommandTune.removeSong(index);
                    System.out.println("\tSong deleted successfully!");
                    break;
                    
                    
                case 3:
                    System.out.print("\tEnter the track number ");
                    int updateIndex = scanner.nextInt() -1;
                    scanner.nextLine();  
                    System.out.print("\tSelect the new title: ");
                    String newTitle = scanner.nextLine();
                    System.out.print("\tSelect the new artist: ");
                    String newArtist = scanner.nextLine();
                    System.out.print("\tSelect the new genre: ");
                    String newGenre = scanner.nextLine();
                    System.out.print("\tSelect the new duration (in seconds): ");
                    int newDuration = scanner.nextInt();
                    CommandTune.editSong(updateIndex, newTitle, newArtist, newGenre, newDuration);
                    System.out.println("\tSong updated successfully!");
                    break;
                    
                    
                case 4:
                    scanner.nextLine();  
                    System.out.print("\tEnter the search genre: ");
                    String zhanri = scanner.nextLine();
                    LinkedList<Song> matchingSongs = CommandTune.searchByGenre(zhanri);
                    CommandTune.printList(matchingSongs);
                    break;
                    
                    
                case 5:
                    scanner.nextLine();  
                    System.out.print("\tEnter the name of the artist: ");
                    String artisti = scanner.nextLine();
                    LinkedList<Song> matchingSongsArtist = CommandTune.searchByArtist(artisti);
                    CommandTune.printList(matchingSongsArtist);
                    break;
                    
                case 6:
                    CommandTune.playSongs();
                    break;
                    
                
                case 7:
                	CommandTune.playFavSongs();
                	break;
                    
                    
                    
                    
                case 8:
                    System.out.print("\tEnter the track number of the song you want to add to the favourites: ");
                    int addFavoriteIndex = scanner.nextInt() - 1;
                    CommandTune.addFavoriteSong(addFavoriteIndex);
                    System.out.println("\tSong added to your favourites successfully!");
                    break;
                    
                case 9:
                    System.out.print("\tEnter the track number of the song you want to remove to the favourites: ");
                    int removeFavoriteIndex = scanner.nextInt() - 1;
                    CommandTune.removeFavoriteSong(removeFavoriteIndex);
                    System.out.println("\tSong removed from your favourites!");
                    break;
                    
                case 10:
                	CommandTune.printSongList();
                    break;
                    
                case 11:
                	CommandTune.printFavoriteSongs();
                    break;
                    
                case 12:
                    CommandTune.undoLastAction();
                    break;
                    
                    
                case 13:
                    CommandTune.redoLastAction();
                    break;
                    
                    
                    
                case 14:
                    System.out.print("\tEnter the file name to save your song list: ");
                    scanner.nextLine();
                    String saveFilename = scanner.nextLine();
                    CommandTune.saveSongsToFile(saveFilename);
                    break;

                case 15:
                	System.out.print("\tEnter the file name to read your song list: ");
                    scanner.nextLine(); 
                    String readFilename = scanner.nextLine();
                    CommandTune.readSongsFromFile(readFilename);
                    break;

                
                case 16:
                	System.out.print("\tEnter the file name to save your favourites: ");
                    scanner.nextLine(); 
                    String favfile = scanner.nextLine();
                    CommandTune.saveFavSongsToFile(favfile);
                    break;

                case 17:
                	System.out.print("\tEnter the file name to read your favourites: ");
                    scanner.nextLine(); 
                    String readFavFile = scanner.nextLine();
                    CommandTune.readFavSongsFromFile(readFavFile);
                    break;
                    
                case 18:
                    System.out.println("\n\tExiting CommandTune...");
                    System.exit(0);
                    break;
                    
                default:
                    System.out.println("\tWrong track. Please choose a correct track!");
            }
    }
}
