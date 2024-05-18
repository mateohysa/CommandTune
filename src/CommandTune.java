import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class CommandTune {


    private LinkedList<Song> SongList;
    private LinkedList<Song> favoriteSongs;
    private Stack<SongActions> operationStack;
    private Stack<SongActions> redoStack;
    private LinkedList<Song> selectedSongs;

    public CommandTune() {
        SongList = new LinkedList<>();
        favoriteSongs = new LinkedList<>();
        operationStack = new Stack<>();
        redoStack = new Stack<>();
        selectedSongs = new LinkedList<>();
    }

    public void addSong(String title, String artist, String genre, int duration) {
        Song song = new Song(title, artist, genre, duration);
        SongList.add(song);
        SongActions operation = new SongActions(SongActionType.ADD, song);
        operationStack.push(operation);

    }

    public void removeSong(int index)
    {
        if(index >= 0 && index < SongList.size())
        {
            Song song = SongList.get(index);
            SongList.remove(index);
            operationStack.push(new SongActions(SongActionType.REMOVE, song));

        }

        else
                System.out.println("\t Please select a valid index");
    }
    public void editSong(int index, String newTitle, String newArtist, String newGenre, int newDuration) {
        Song song = SongList.get(index);
        Song oldSong = new Song(song.getTitle(), song.getArtist(), song.getGenre(), song.getDuration());
        song.setTitle(newTitle);
        song.setArtist(newArtist);
        song.setGenre(newGenre);
        song.setDuration(newDuration);
        operationStack.push(new SongActions(SongActionType.EDIT, oldSong, song));
    }
    public void printList(LinkedList<Song> Songs)
    {

        for (Song song : Songs)
        {
            System.out.println(song.toString());
        }

    }

    public LinkedList<Song> searchByGenre(String genre)
    {
        LinkedList<Song> matchingSongs = new LinkedList<>();

        if(SongList.isEmpty())
            System.out.println("\tYour playlist is empty, please add some songs first.");

        else
        {
            for(Song song : SongList)
            {
                System.out.println("\t");
                if(song.getGenre().equalsIgnoreCase(genre))
                {
                    matchingSongs.add(song);
                }
            }
        }

        return matchingSongs;
    }

    public LinkedList<Song> searchByArtist(String artist)
    {
        LinkedList<Song> matchingSongs = new LinkedList<>();

        if(SongList.isEmpty())
            System.out.println("\tYour playlist is empty, please add some songs first.");

        else
        {
            for(Song song : SongList)
            {
                if(song.getArtist().equalsIgnoreCase(artist))
                {
                    matchingSongs.add(song);
                }
            }
        }

        return matchingSongs;
    }

    public void addFavoriteSong(int index)
    {
        if (index >= 0 && index < SongList.size()) {
            Song songToAdd = SongList.get(index);
            if (!favoriteSongs.contains(songToAdd)) {
                favoriteSongs.add(songToAdd);
                System.out.println("\tAdded to favorites: " + songToAdd.getTitle());
            } else {
                System.out.println("\tThe song is already in favorites.");
            }
        } else {
            System.out.println("\tInvalid index. No song added to favorites.");
        }
    }

    public void removeFavoriteSong(int index)
    {
        if (index >= 0 && index < favoriteSongs.size())
        {
            Song removedSong = favoriteSongs.remove(index);
            System.out.println("\tDeleted from favourite songs: " + removedSong.getTitle());
        }
        else {
            System.out.println("\tWrong index.");
        }
    }

    public void printSongList()
    {
        if (SongList.isEmpty())
        {
            System.out.println("\tYour playlist is empty, please add some songs first!");
        }

        else {
            System.out.println("\tLista e kengevet:");
            for (int i = 0; i < SongList.size(); i++) {
                System.out.println("\t"+(i + 1) + ". " + SongList.get(i).toString());
            }
        }
    }

    public void printFavoriteSongs()
    {
        if (favoriteSongs.isEmpty())
        {
            System.out.println("\tYour playlist is empty, please add some songs first!");
        }

        else {
            System.out.println("\tKenget e preferuara:");

            for (int i = 0; i < favoriteSongs.size(); i++)
            {
                System.out.println("\t"+(i + 1) + ". " + favoriteSongs.get(i).toString());
            }
        }
    }

    public void playSongs()
    {
        if(SongList.isEmpty())
            System.out.println("\tYour playlist is empty, please add some songs first!");

        else
        {
            for(Song song : SongList)
            {

                System.out.println("\n\t" + song.toString());
                try {

                    System.out.printf("\n\tListening .");

                    for(int i = 0; i < song.getDuration()/100; i++)
                    {
                        Thread.sleep(song.getDuration()*20);
                        System.out.printf(".");
                    }

                    System.out.println("");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void playFavSongs()
    {
        if(favoriteSongs.isEmpty())
            System.out.println("\tYour playlist is empty, please add some songs first!");

        else
        {
            for(Song song : favoriteSongs)
            {

                System.out.println("\n" + song.toString());
                try {

                    System.out.printf("\n\tListening .");

                    for(int i = 0; i < song.getDuration()/100; i++)
                    {
                        Thread.sleep(song.getDuration()*20);
                        System.out.printf(".");
                    }

                    System.out.println("");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void undoLastAction()
    {
        if (operationStack.isEmpty())
        {
            System.out.println("\tNo actions to undo.");
        }

        else
        {
                SongActions lastOperation = operationStack.pop();
            switch (lastOperation.getType())
            {
                case ADD:
                    SongList.remove(lastOperation.getSong());
                    redoStack.push(lastOperation);
                    System.out.println("\tUndo successfully!");
                    break;

                case REMOVE:
                    SongList.add(lastOperation.getSong());
                    redoStack.push(lastOperation);
                    System.out.println("\tUndo successfully!");
                    break;

                case EDIT:
                    Song oldSong = lastOperation.getOldSong();
                    Song newSong = lastOperation.getSong();
                    int index = SongList.indexOf(newSong);
                    SongList.set(index, oldSong);
                    redoStack.push(lastOperation);
                    System.out.println("\tUndo successfully!");
                    break;

                default:
                    break;
            }
        }
    }
    public void saveSongsToFile(String file)
    {
        try (PrintWriter write = new PrintWriter(new FileWriter(file, true)))
        {
            for (Song song : SongList)
            {
                write.println(song.getTitle());
                write.println(song.getArtist());
                write.println(song.getGenre());
                write.println(song.getDuration());
            }

            System.out.println("Songs saved on file: " + file);
        }
        catch (IOException e)
        {
            System.out.println("Songs not saved on file: " + file);
            e.printStackTrace();
        }
    }


    public void saveFavSongsToFile(String file)
    {
        try (PrintWriter write = new PrintWriter(new FileWriter(file)))
        {
            for (Song song : favoriteSongs)
            {
                write.println(song.getTitle());
                write.println(song.getArtist());
                write.println(song.getGenre());
                write.println(song.getDuration());
            }

            System.out.println("Favourite songs saved on file: " + file);
        }

        catch (IOException e)
        {
            System.out.println("Favourite songs not saved on file: " + file);
            e.printStackTrace();
        }
    }



    public void readSongsFromFile(String file) {
        try (Scanner scanner = new Scanner(new File(file))) {
            SongList.clear();
            System.out.println("\tReading songs from file: " + file);

            while (scanner.hasNextLine()) {
                String title = scanner.nextLine();

                if (!scanner.hasNextLine()) {
                    System.out.println("Incomplete song data after title: " + title);
                    break;
                }

                String artist = scanner.nextLine();

                if (!scanner.hasNextLine()) {
                    System.out.println("Incomplete song data after artist: " + artist);
                    break;
                }

                String genre = scanner.nextLine();

                if (!scanner.hasNextLine()) {
                    System.out.println("Incomplete song data after genre: " + genre);
                    break;
                }

                String durationLine = scanner.nextLine();

                int duration;

                try {
                    duration = Integer.parseInt(durationLine);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid duration format for song: " + title);
                    continue;
                }

                Song song = new Song(title, artist, genre, duration);
                SongList.add(song);
            }

            System.out.println("\tSongs from " + file);
            for (Song song : SongList) {
                System.out.println("\t" + song);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file);
            e.printStackTrace();
        }
    }


    public void readFavSongsFromFile(String file)
    {
        try (Scanner scanner = new Scanner(new File(file)))
        {
            favoriteSongs.clear();

            while (scanner.hasNextLine())
            {
                String title = scanner.nextLine();
                String artist = scanner.nextLine();
                String genre = scanner.nextLine();
                int duration = Integer.parseInt(scanner.nextLine());
                Song song = new Song(title, artist, genre, duration);
                favoriteSongs.add(song);
            }
            System.out.println("Favourite songs from CommandTune: " + file);
        }

        catch (FileNotFoundException e)
        {
            System.out.println("File not found: " + file);
            e.printStackTrace();
        }
    }


    public void redoLastAction() {
        if (redoStack.isEmpty()) {
            System.out.println("\tNo actions to redo.");
        } else {
            SongActions lastRedo = redoStack.pop();

            switch (lastRedo.getType()) {
                case ADD:
                    SongList.add(lastRedo.getSong());
                    operationStack.push(lastRedo);
                    System.out.println("\tRedo successfully!");
                    break;

                case REMOVE:
                    SongList.remove(lastRedo.getSong());
                    operationStack.push(lastRedo);
                    System.out.println("\tRedo successfully!");
                    break;

                case EDIT:
                    Song oldSong = lastRedo.getOldSong();
                    Song newSong = lastRedo.getSong();
                    int index = SongList.indexOf(oldSong);
                    SongList.set(index, newSong);
                    operationStack.push(lastRedo);
                    System.out.println("\tRedo successfully!");
                    break;

                default:
                    break;
            }
        }
    }




}
