package model;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalog implements Serializable {
    private List<AudioItem> items;
    private List<Playlist> playlists;

    public Catalog() {
        this.items = new ArrayList<>();
        this.playlists = new ArrayList<>();
    }

    public List<AudioItem> getItems() {
        return items;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }
}
