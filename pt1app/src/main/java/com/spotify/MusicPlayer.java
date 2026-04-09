package com.spotify;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

/**
 * Handles playing WAV audio files using javax.sound.
 * Runs playback on a background thread so the menu stays responsive.
 */
public class MusicPlayer {

    private Clip currentClip;
    private Thread playbackThread;
    private volatile boolean playing = false;

    /**
     * Plays the given Song's WAV file.
     * Stops any currently playing audio first.
     */
    public void play(Song song) {
        stop(); // stop whatever is currently playing

        System.out.println("\n" + song.getInfo());
        System.out.println("  [Press ENTER to stop and return to menu]");

        playbackThread = new Thread(() -> {
            try {
                File audioFile = new File(song.getFilePath());

                if (!audioFile.exists()) {
                    System.out.println("\n  [ERROR] Audio file not found: " + song.getFilePath());
                    System.out.println("  Make sure the .wav file exists at that path.");
                    return;
                }

                AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
                currentClip = AudioSystem.getClip();
                currentClip.open(audioStream);
                currentClip.start();
                playing = true;

                // Wait for clip to finish (or be stopped)
                currentClip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        playing = false;
                    }
                });

                // Block this thread until clip finishes naturally
                while (currentClip.isRunning()) {
                    Thread.sleep(200);
                }

            } catch (UnsupportedAudioFileException e) {
                System.out.println("\n  [ERROR] Unsupported audio format. Make sure it's a standard WAV file.");
            } catch (LineUnavailableException e) {
                System.out.println("\n  [ERROR] Audio line unavailable: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("\n  [ERROR] Could not read audio file: " + e.getMessage());
            } catch (InterruptedException e) {
                // Thread was interrupted — clip was stopped manually, this is fine
                Thread.currentThread().interrupt();
            }
        });

        playbackThread.setDaemon(true);
        playbackThread.start();
    }

    /**
     * Stops the currently playing audio clip.
     */
    public void stop() {
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.close();
        }
        if (playbackThread != null && playbackThread.isAlive()) {
            playbackThread.interrupt();
        }
        playing = false;
    }

    /**
     * Returns true if a song is currently playing.
     */
    public boolean isPlaying() {
        return playing;
    }
}