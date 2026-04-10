package com.spotify;

/**
 * Represents a single audio track with its metadata.
 * Field names (name, fileName) match the JSON file exactly so Gson maps them correctly.
 */
public class Song {
    private String name;      // matches "name" in songs.json
    private String artist;
    private int year;
    private String genre;
    private String fileName;  // matches "fileName" in songs.json (just the file, e.g. "biggie-crop.wav")

    // Constructor
    public Song(String name, String artist, int year, String genre, String fileName) {
        this.name     = name;
        this.artist   = artist;
        this.year     = year;
        this.genre    = genre;
        this.fileName = fileName;
    }

    // --- Getters ---
    public String getName()     { return name; }
    public String getArtist()   { return artist; }
    public int    getYear()     { return year; }
    public String getGenre()    { return genre; }
    public String getFileName() { return fileName; }

    /**
     * Builds the full relative path to the WAV file.
     * e.g. "biggie-crop.wav" -> "wav/biggie-crop.wav"
     */
    public String getFilePath() {
        return "C:\\Users\\816re\\OneDrive\\Desktop\\SJCC\\Java\\GitHub\\SpotifyApp\\pt1app\\WAV\\wav\\" + fileName;
    }

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
            name, artist, year, genre
        );
    }

    @Override
    public String toString() {
        return String.format("%-35s | %-22s | %d | %s", name, artist, year, genre);
    }
}