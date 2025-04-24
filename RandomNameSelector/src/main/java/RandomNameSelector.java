import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.awt.Toolkit;

public class RandomNameSelector {

    // Animation and style constants
    private static final int MIN_DELAY_MS = 5;
    private static final int MAX_DELAY_MS = 350;
    private static final int MAX_CYCLES = 50;
    private static final double EXPONENT = 2.5;  // Higher = steeper slowdown

    private static final String ANSI_BOLD = "\u001B[1m";
    private static final String ANSI_BLINK = "\u001B[5m";
    private static final String ANSI_RESET = "\u001B[0m";

    // Resource file names
    private static final String PARTICIPANTS_RESOURCE = "/participants.txt";
    private static final String EXCLUSIONS_RESOURCE = "/exclude.txt";

    public static void main(String[] args) {
        List<String> participants = readLinesFromResource(PARTICIPANTS_RESOURCE);
        Set<String> exclusions = new HashSet<>(readLinesFromResource(EXCLUSIONS_RESOURCE));

        List<String> allowed = new ArrayList<>(participants);
        allowed.removeAll(exclusions);

        if (allowed.isEmpty()) {
            System.err.println("No participants available for selection.");
            return;
        }

        int maxNameLength = allowed.stream().mapToInt(String::length).max().orElse(0);
        Random random = new Random();
        String currentName = "";

        for (int i = 0; i < MAX_CYCLES; i++) {
            currentName = allowed.get(random.nextInt(allowed.size()));
            String padded = String.format("%-" + maxNameLength + "s", currentName);
            System.out.print('\r' + padded);
            System.out.flush();

            // Play click sound (system beep)
            Toolkit.getDefaultToolkit().beep();

            double progress = (double) i / MAX_CYCLES;
            int delay = MIN_DELAY_MS + (int) ((MAX_DELAY_MS - MIN_DELAY_MS) * Math.pow(progress, EXPONENT));

            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                break;
            }
        }

        // Final name in bold and blinking ANSI formatting
        String finalName = ANSI_BOLD + ANSI_BLINK + currentName + ANSI_RESET;
        System.out.println('\r' + finalName);
    }

    private static List<String> readLinesFromResource(String resourcePath) {
        List<String> lines = new ArrayList<>();
        try (InputStream stream = RandomNameSelector.class.getResourceAsStream(resourcePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(stream)))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    lines.add(trimmed);
                }
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Failed to load resource: " + resourcePath + " - " + e.getMessage());
        }
        return lines;
    }
}
