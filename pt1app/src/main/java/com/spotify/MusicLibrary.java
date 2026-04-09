package com.spotify;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Loads song metadata from a JSON file and provides search/retrieval methods.
 * Designed to scale — just add more songs to the JSON file, no code changes needed.
 */
public class MusicLibrary {

    // -----------------------------------------------------------------------
    // CONSTANT — change this path to wherever your songs.json file lives
    // -----------------------------------------------------------------------
    private static final String JSON_PATH = "data/songs.json";

    private List<Song> songs;

    public MusicLibrary() {
        songs = new ArrayList<>();
        loadSongs();
    }

    /**
     * Reads the JSON file and deserializes it into a List<Song>.
     */
    private void loadSongs() {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Song>>() {}.getType();

        try (FileReader reader = new FileReader(JSON_PATH)) {
            songs = gson.fromJson(reader, listType);
            System.out.println("  Loaded " + songs.size() + " songs from library.\n");
        } catch (IOException e) {
            System.out.println("  [WARNING] Could not load songs.json: " + e.getMessage());
            System.out.println("  Make sure '" + JSON_PATH + "' exists relative to where you run the app.\n");
        }
    }

    /**
     * Returns all songs in the library.
     */
    public List<Song> getAllSongs() {
        return songs;
    }

    /**
     * Searches songs by title (case-insensitive partial match).
     */
    public List<Song> searchByTitle(String query) {
        List<Song> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();
        for (Song song : songs) {
            if (song.getTitle().toLowerCase().contains(lowerQuery)) {
                results.add(song);
            }
        }
        return results;
    }

    /**
     * Returns the total number of songs.
     */
    public int size() {
        return songs.size();
    }
}