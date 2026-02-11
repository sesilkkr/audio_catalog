package model;

import java.io.Serializable;

public class AudioItem implements Serializable {
    private String title;
    private String genre;
    private int duration;
    private String author;
    private int year;
    private String category;

public AudioItem(String title, String genre, int duration, String author, int year, String category) {
    this.title = title;
    this.genre = genre;
    this.duration = duration;
    this.author = author;
    this.year = year;
    this.category = category;
}
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
       this.title = title;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
       this.duration = duration;
    }

    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
    this.author = author;
    }

    public int getYear() {
        return year;
    }
    public void setYear(int year) {
    this.year = year;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
    this.category = category;
    }

    @Override
    public String toString() {
        return "Заглавие: " + title +
                ", Жанр: " + genre +
                ", Продължителност: " + duration + " сек" +
                ", Автор: " + author +
                ", Година: " + year +
                ", Категория: " + category;
    }

}
