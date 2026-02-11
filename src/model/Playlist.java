package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Playlist implements Serializable {
    private String name;
    private List<AudioItem> items;

    public Playlist(String name) {
        this.name = name;
        this.items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<AudioItem> getItems() {
        return items;
    }
    public void addItem(AudioItem item) {
        items.add(item);
    }
    public void removeItem(AudioItem item) {
        items.remove(item);
    }

    @Override
    public String toString() {
        return "Playlist: " + "име = " + name + " (" + items.size() + " обекта)" ;
    }
}
