package com.spotify;

/**
 * Represents a single audio track with its metadata.
 */
public class Song {
    private String title;
    private String artist;
    private int year;
    private String genre;
    private String filePath;

    // Constructor
    public Song(String title, String artist, int year, String genre, String filePath) {
        this.title    = title;
        this.artist   = artist;
        this.year     = year;
        this.genre    = genre;
        this.filePath = filePath;
    }

    // --- Getters ---
    public String getTitle()    { return title; }
    public String getArtist()   { return artist; }
    public int    getYear()     { return year; }
    public String getGenre()    { return genre; }
    public String getFilePath() { return filePath; }

    /**
     * Returns a nicely formatted summary of the song to display when it plays.
     */
    public String getInfo() {
        return String.format(
            "  ♪ Now Playing ♪\n" +
            "  Title  : %s\n" +
            "  Artist : %s\n" +
            "  Year   : %d\n" +
            "  Genre  : %s",
            title, artist, year, genre
        );
    }

    @Override
    public String toString() {
        return String.format("%-30s | %-20s | %d | %s", title, artist, year, genre);
    }
}