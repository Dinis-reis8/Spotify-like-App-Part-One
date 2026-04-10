package com.spotify;

/**
 * Entry point for the Java Spotify-like music player app.
 *
 * Run from the project root directory (where pom.xml lives) so that
 * the relative path "data/songs.json" and your "wav/" folder resolve correctly.
 *
 * To compile and run:
 *   mvn compile
 *   mvn exec:java -Dexec.mainClass="com.spotify.App"
 *
 * Or build a JAR and run:
 *   mvn package
 *   java -jar target/spotify-app-1.0-SNAPSHOT.jar
 */
public class App {
    public static void main(String[] args) {
        System.out.println();
        System.out.println("  ============================================");
        System.out.println("           Java Spotify  —  Loading...  ");
        System.out.println("  ============================================");
        System.out.println();

        // Load the song library from JSON
        MusicLibrary library = new MusicLibrary();

        // Create the audio player
        MusicPlayer player = new MusicPlayer();

        // Launch the menu system
        Menu menu = new Menu(library, player);
        menu.showMainMenu();
    }
}