package com.spotify;

import java.util.List;
import java.util.Scanner;

/**
 * Handles all text-based menu screens: Home, Search, and Library.
 */
public class Menu {

    private final MusicLibrary library;
    private final MusicPlayer  player;
    private final Scanner      scanner;

    public Menu(MusicLibrary library, MusicPlayer player) {
        this.library = library;
        this.player  = player;
        this.scanner = new Scanner(System.in);
    }

    // -----------------------------------------------------------------------
    // MAIN MENU
    // -----------------------------------------------------------------------

    /**
     * Shows the main menu and routes to sub-menus. Runs until the user quits.
     */
    public void showMainMenu() {
        boolean running = true;

        while (running) {
            printDivider();
            System.out.println("           ♫  JAVA SPOTIFY  ♫");
            printDivider();
            System.out.println("  [H] Home");
            System.out.println("  [S] Search by title");
            System.out.println("  [L] Library");
            System.out.println("  [Q] Quit");
            printDivider();
            System.out.print("  Enter choice: ");

            String input = scanner.nextLine().trim().toUpperCase();

            switch (input) {
                case "H" -> showHome();
                case "S" -> showSearch();
                case "L" -> showLibrary();
                case "Q" -> {
                    player.stop();
                    System.out.println("\n  Thanks for listening. Goodbye! ♫\n");
                    running = false;
                }
                default  -> System.out.println("  Invalid option. Please enter H, S, L, or Q.");
            }
        }
    }

    // -----------------------------------------------------------------------
    // HOME MENU
    // -----------------------------------------------------------------------

    /**
     * Home screen — shows a welcome banner and stats about the library.
     * Organized by genre for a nice browsing feel.
     */
    private void showHome() {
        printDivider();
        System.out.println("  ♫  HOME");
        printDivider();
        System.out.printf("  Welcome! You have %d songs in your library.%n", library.size());
        System.out.println();
        System.out.println("  Genres in your library:");

        // List unique genres
        library.getAllSongs().stream()
            .map(Song::getGenre)
            .distinct()
            .sorted()
            .forEach(genre -> System.out.println("    • " + genre));

        System.out.println();
        System.out.println("  Use [S] Search or [L] Library to play music!");
        printDivider();
        System.out.print("  Press ENTER to return to main menu...");
        scanner.nextLine();
    }

    // -----------------------------------------------------------------------
    // SEARCH MENU
    // -----------------------------------------------------------------------

    /**
     * Lets the user search songs by title (partial match).
     */
    private void showSearch() {
        boolean inSearch = true;

        while (inSearch) {
            printDivider();
            System.out.println("  ♫  SEARCH BY TITLE");
            printDivider();
            System.out.print("  Enter song title (or ENTER to go back): ");
            String query = scanner.nextLine().trim();

            if (query.isEmpty()) {
                inSearch = false;
                continue;
            }

            List<Song> results = library.searchByTitle(query);

            if (results.isEmpty()) {
                System.out.println("  No songs found matching \"" + query + "\".");
            } else {
                System.out.println("\n  Found " + results.size() + " result(s):\n");
                printSongList(results);
                System.out.println();
                playSongFromList(results);
            }
        }
    }

    // -----------------------------------------------------------------------
    // LIBRARY MENU
    // -----------------------------------------------------------------------

    /**
     * Shows the full song library and lets the user pick one to play.
     */
    private void showLibrary() {
        boolean inLibrary = true;

        while (inLibrary) {
            printDivider();
            System.out.println("  ♫  LIBRARY  —  All Songs");
            printDivider();
            System.out.printf("  %-4s %-30s %-20s %-6s %s%n",
                "#", "Title", "Artist", "Year", "Genre");
            System.out.println("  " + "-".repeat(72));

            List<Song> allSongs = library.getAllSongs();
            for (int i = 0; i < allSongs.size(); i++) {
                System.out.printf("  %-4d %s%n", (i + 1), allSongs.get(i));
            }

            printDivider();
            System.out.print("  Enter number to play (or 0 to go back): ");
            String input = scanner.nextLine().trim();

            if (input.equals("0") || input.isEmpty()) {
                inLibrary = false;
                continue;
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice < 1 || choice > allSongs.size()) {
                    System.out.println("  Please enter a number between 1 and " + allSongs.size());
                } else {
                    Song selected = allSongs.get(choice - 1);
                    player.play(selected);
                    // Wait for user to press ENTER, then stop
                    scanner.nextLine();
                    player.stop();
                    System.out.println("  Stopped.");
                }
            } catch (NumberFormatException e) {
                System.out.println("  Invalid input. Please enter a number.");
            }
        }
    }

    // -----------------------------------------------------------------------
    // SHARED HELPERS
    // -----------------------------------------------------------------------

    /**
     * Prints a numbered list of songs.
     */
    private void printSongList(List<Song> songs) {
        System.out.printf("  %-4s %-30s %-20s %-6s %s%n",
            "#", "Title", "Artist", "Year", "Genre");
        System.out.println("  " + "-".repeat(72));
        for (int i = 0; i < songs.size(); i++) {
            System.out.printf("  %-4d %s%n", (i + 1), songs.get(i));
        }
    }

    /**
     * Asks the user to pick a song from a list and plays it.
     */
    private void playSongFromList(List<Song> songs) {
        System.out.print("  Enter number to play (or 0 to go back): ");
        String input = scanner.nextLine().trim();

        if (input.equals("0") || input.isEmpty()) return;

        try {
            int choice = Integer.parseInt(input);
            if (choice < 1 || choice > songs.size()) {
                System.out.println("  Invalid selection.");
            } else {
                Song selected = songs.get(choice - 1);
                player.play(selected);
                // Block here; user presses ENTER to stop
                scanner.nextLine();
                player.stop();
                System.out.println("  Stopped.");
            }
        } catch (NumberFormatException e) {
            System.out.println("  Invalid input.");
        }
    }

    private void printDivider() {
        System.out.println("  " + "=".repeat(50));
    }
}