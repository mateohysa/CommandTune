enum SongActionType {
    ADD,
    REMOVE,
    EDIT,
    UNDO,
    REDO,
    SELECT
}

public class SongActions {
    private final SongActionType type;
    private final Song song;
    private final Song oldSong;
    private boolean isSelected;

    public SongActions(SongActionType type, Song song) {
        this.type = type;
        this.song = song;
        this.oldSong = null;
        this.isSelected = false;
    }

    public SongActions(SongActionType type, Song oldSong, Song newSong) {
        this.type = type;
        this.song = newSong;
        this.oldSong = oldSong;
        this.isSelected = false;
    }

    public SongActionType getType() {
        return type;
    }

    public Song getSong() {
        return song;
    }

    public Song getOldSong() {
        return oldSong;
    }
}
