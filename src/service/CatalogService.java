package service;

import model.AudioItem;
import model.Catalog;
import model.Playlist;

import java.util.ArrayList;
import java.util.List;

public class CatalogService {

    private final Catalog catalog;

    public CatalogService(Catalog catalog) {
        this.catalog = catalog;
    }

    private String norm(String s) {
        if (s == null) return null;
        return s.trim().replaceAll("\\s+", " ").toLowerCase();
    }

    private boolean equalsText(String firstText, String secondText) {

        String normalizedFirst = norm(firstText);
        String normalizedSecond = norm(secondText);

        if (normalizedFirst == null || normalizedSecond == null) return false;

        return normalizedFirst.equals(normalizedSecond);
    }

    private boolean containsText(String fullText, String searchText) {

        String normalizedFull = norm(fullText);
        String normalizedSearch = norm(searchText);

        if (normalizedFull == null || normalizedSearch == null) return false;

        return normalizedFull.contains(normalizedSearch);
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
            if (equalsText(item.getTitle(), title)) return item;
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
            if (containsText(item.getTitle(), titlePart)) result.add(item);
        }
        return result;
    }

    public List<AudioItem> searchAuthor(String authorPart) {
        List<AudioItem> result = new ArrayList<>();
        for (AudioItem item : catalog.getItems()) {
            if (containsText(item.getAuthor(), authorPart)) result.add(item);
        }
        return result;
    }

    public List<AudioItem> filterGenre(String genre) {
        List<AudioItem> result = new ArrayList<>();
        for (AudioItem item : catalog.getItems()) {
            if (equalsText(item.getGenre(), genre)) result.add(item);
        }
        return result;
    }

    public List<AudioItem> searchGenre(String genre) {
        return filterGenre(genre);
    }

    public List<AudioItem> filterCategory(String category) {
        List<AudioItem> result = new ArrayList<>();
        for (AudioItem item : catalog.getItems()) {
            if (equalsText(item.getCategory(), category)) result.add(item);
        }
        return result;
    }

    public List<AudioItem> filterAuthor(String author) {
        List<AudioItem> result = new ArrayList<>();
        for (AudioItem item : catalog.getItems()) {
            if (equalsText(item.getAuthor(), author)) result.add(item);
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
        catalog.getItems().sort(
                (a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle())
        );
    }

    public void sortByYear() {
        catalog.getItems().sort(
                (a, b) -> Integer.compare(a.getYear(), b.getYear())
        );
    }

    public boolean createPlaylist(String name) {
        if (name == null || name.isBlank()) return false;
        if (getPlaylist(name) != null) return false;

        catalog.getPlaylists().add(new Playlist(name));
        return true;
    }
    public boolean removePlaylist(String name) {
        Playlist p = getPlaylist(name);
        if (p == null) return false;
        return catalog.getPlaylists().remove(p);
    }

    public List<Playlist> playlists() {
        return catalog.getPlaylists();
    }

    public Playlist getPlaylist(String name) {
        if (name == null) return null;
        for (Playlist p : catalog.getPlaylists()) {
            if (equalsText(p.getName(), name)) return p;
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

        return p.getItems().removeIf(it -> equalsText(it.getTitle(), itemTitleExact));
    }

    public boolean sortPlaylistByTitle(String playlistName) {
        Playlist p = getPlaylist(playlistName);
        if (p == null) return false;

        p.getItems().sort(
                (a, b) -> a.getTitle().compareToIgnoreCase(b.getTitle())
        );
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
