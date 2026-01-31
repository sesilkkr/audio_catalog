package service;

import model.AudioItem;
import model.Catalog;
import model.Playlist;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CatalogService {

    private final Catalog catalog;

    public CatalogService(Catalog catalog) {
        this.catalog = catalog;
    }

    private boolean containsIgnoreCase(String text, String part) {
        if (text == null || part == null) return false;
        return text.toLowerCase().contains(part.toLowerCase());
    }

    private boolean equalsIgnoreCaseSafe(String a, String b) {
        if (a == null || b == null) return false;
        return a.equalsIgnoreCase(b);
    }

    public void addItem(AudioItem item) {
        if (item == null) return;
        catalog.getItems().add(item);
    }

    public List<AudioItem> items() {
        return catalog.getItems();
    }

    public AudioItem findByTitle(String title) {
        if (title == null) return null;
        for (AudioItem item : catalog.getItems()) {
            if (equalsIgnoreCaseSafe(item.getTitle(), title)) return item;
        }
        return null;
    }

    public boolean removeByTitle(String title) {
        AudioItem found = findByTitle(title);
        if (found == null) return false;

        boolean removed = catalog.getItems().remove(found);

        for (Playlist p : catalog.getPlaylists()) {
            p.getItems().remove(found);
        }
        return removed;
    }

    public List<AudioItem> searchTitle(String titlePart) {
        List<AudioItem> result = new ArrayList<>();
        for (AudioItem item : catalog.getItems()) {
            if (containsIgnoreCase(item.getTitle(), titlePart)) result.add(item);
        }
        return result;
    }

    public List<AudioItem> searchAuthor(String authorPart) {
        List<AudioItem> result = new ArrayList<>();
        for (AudioItem item : catalog.getItems()) {
            if (containsIgnoreCase(item.getAuthor(), authorPart)) result.add(item);
        }
        return result;
    }

    public List<AudioItem> filterGenre(String genre) {
        List<AudioItem> result = new ArrayList<>();
        for (AudioItem item : catalog.getItems()) {
            if (equalsIgnoreCaseSafe(item.getGenre(), genre)) result.add(item);
        }
        return result;
    }

    public List<AudioItem> searchGenre(String genre) {
        return filterGenre(genre);
    }


    public List<AudioItem> filterCategory(String category) {
        List<AudioItem> result = new ArrayList<>();
        for (AudioItem item : catalog.getItems()) {
            if (equalsIgnoreCaseSafe(item.getCategory(), category)) result.add(item);
        }
        return result;
    }

    public List<AudioItem> filterAuthor(String author) {
        List<AudioItem> result = new ArrayList<>();
        for (AudioItem item : catalog.getItems()) {
            if (equalsIgnoreCaseSafe(item.getAuthor(), author)) result.add(item);
        }
        return result;
    }

    public List<AudioItem> filterYear(int year) {
        List<AudioItem> result = new ArrayList<>();
        for (AudioItem item : catalog.getItems()) {
            if (item.getYear() == year) result.add(item);
        }
        return result;
    }

    public void sortByTitle() {
        catalog.getItems().sort(Comparator.comparing(
                AudioItem::getTitle,
                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
        ));
    }

    public void sortByYear() {
        catalog.getItems().sort(Comparator.comparingInt(AudioItem::getYear));
    }

    public boolean createPlaylist(String name) {
        if (name == null || name.isBlank()) return false;
        if (getPlaylist(name) != null) return false;

        catalog.getPlaylists().add(new Playlist(name));
        return true;
    }

    public List<Playlist> playlists() {
        return catalog.getPlaylists();
    }

    public Playlist getPlaylist(String name) {
        if (name == null) return null;
        for (Playlist p : catalog.getPlaylists()) {
            if (equalsIgnoreCaseSafe(p.getName(), name)) return p;
        }
        return null;
    }

    public boolean addToPlaylist(String playlistName, String itemTitleExact) {
        Playlist p = getPlaylist(playlistName);
        AudioItem item = findByTitle(itemTitleExact);
        if (p == null || item == null) return false;

        p.addItem(item);
        return true;
    }

    public boolean removeFromPlaylist(String playlistName, String itemTitleExact) {
        Playlist p = getPlaylist(playlistName);
        if (p == null || itemTitleExact == null) return false;

        return p.getItems().removeIf(it -> equalsIgnoreCaseSafe(it.getTitle(), itemTitleExact));
    }

    public boolean sortPlaylistByTitle(String playlistName) {
        Playlist p = getPlaylist(playlistName);
        if (p == null) return false;

        p.getItems().sort(Comparator.comparing(
                AudioItem::getTitle,
                Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
        ));
        return true;
    }

    public int playlistDuration(String playlistName) {
        Playlist p = getPlaylist(playlistName);
        if (p == null) return 0;

        int total = 0;
        for (AudioItem item : p.getItems()) {
            total += item.getDuration();
        }
        return total;
    }
}
