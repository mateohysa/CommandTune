import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class CommandTune {
    // do ket data structures per song list / playlist q do jet linkedlist
    //linked list per favourite songs
    //linked list per selected songs qe te besh operations per iher pra te fshish shum kong per iher.
    // stack per undo redo dhe kshu veprimesh

    // akoma duhen diskutu


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
            System.out.println("\tYour playlist is empty, add some songs first.");

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
            System.out.println("\tYour playlist is empty, add some songs first.");

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
            System.out.println("\tU fshi nga kenget e preferuara: " + removedSong.getTitle());
        }
        else {
            System.out.println("\tIndeks i gabuar.");
        }
    }

    public void printSongList()
    {
        if (SongList.isEmpty())
        {
            System.out.println("\tPlaylista juaj eshte bosh, ju lutem shtoni ne fillim disa kenge!");
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
            System.out.println("\tPlaylista juaj eshte bosh, ju lutem shtoni ne fillim disa kenge!");
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
            System.out.println("\tPlaylista juaj eshte bosh, ju lutem shtoni ne fillim disa kenge!");

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
            System.out.println("\tPlaylista juaj eshte bosh, ju lutem shtoni ne fillim disa kenge!");

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
            System.out.println("\tNuk ka me veprime te meparshme.");
        }

        else
        {
                SongActions lastOperation = operationStack.pop();
            switch (lastOperation.getType())
            {
                case ADD:
                    SongList.remove(lastOperation.getSong());
                    redoStack.push(lastOperation);
                    System.out.println("\tVeprimi u be undo!");
                    break;

                case REMOVE:
                    SongList.add(lastOperation.getSong());
                    redoStack.push(lastOperation);
                    System.out.println("\tVeprimi u be undo!");
                    break;

                case EDIT:
                    Song oldSong = lastOperation.getOldSong();
                    Song newSong = lastOperation.getSong();
                    int index = SongList.indexOf(newSong);
                    SongList.set(index, oldSong);
                    redoStack.push(lastOperation);
                    System.out.println("\tVeprimi u be undo!");
                    break;

                default:
                    break;
            }
        }
    }

    public void redoLastAction() {
        if (redoStack.isEmpty()) {
            System.out.println("\tNuk ka veprime te kryera.");
        } else {
            SongActions lastRedo = redoStack.pop();

            switch (lastRedo.getType()) {
                case ADD:
                    SongList.add(lastRedo.getSong());
                    operationStack.push(lastRedo);
                    System.out.println("\tVeprimi u be redo!");
                    break;

                case REMOVE:
                    SongList.remove(lastRedo.getSong());
                    operationStack.push(lastRedo);
                    System.out.println("\tVeprimi u be redo!");
                    break;

                case EDIT:
                    Song oldSong = lastRedo.getOldSong();
                    Song newSong = lastRedo.getSong();
                    int index = SongList.indexOf(oldSong);
                    SongList.set(index, newSong);
                    operationStack.push(lastRedo);
                    System.out.println("\tVeprimi u be redo!");
                    break;

                default:
                    break;
            }
        }
    }




}
