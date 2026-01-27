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

    public String getGenre() {
        return genre;
    }

    public int getDuration() {
        return duration;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return "Title: " + title +
                ", Genre: " + genre +
                ", Duration: " + duration + " sec" +
                ", Author: " + author +
                ", Year: " + year +
                ", Category: " + category;
    }
}
