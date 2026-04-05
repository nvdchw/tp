package seedu.address.ui;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores and manages navigation through the history of commands entered by the user.
 */
public class CommandHistory {

    private static final String EMPTY_STRING = "";

    private final List<String> history = new ArrayList<>();
    private int pointer = 0;

    /**
     * Appends {@code command} to the history and resets the navigation pointer
     * past the newest entry so that subsequent UP presses start from the
     * most-recently added command.
     *
     * Blank commands are silently ignored. Consecutive identical commands are
     * also skipped to avoid duplicates in the history.
     */
    public void add(String command) {
        if (command == null || command.isBlank()) {
            return;
        }

        if (isDuplicateOfLastCommand(command)) {
            resetPointerToEnd();
            return;
        }

        history.add(command);
        resetPointerToEnd();
        assert pointer == history.size() : "Pointer should be past-the-end after reset";
    }

    /**
     * Moves the pointer one step toward older commands and returns the command
     * at that position. If the pointer is already at the oldest command it
     * stays there and that command is returned.
     *
     * @return the older command at the new pointer position, or {@code EMPTY_STRING} if
     *         history is empty.
     */
    public String navigateUp() {
        if (history.isEmpty()) {
            return EMPTY_STRING;
        }
        pointer = Math.max(0, pointer - 1);
        assert 0 <= pointer && pointer < history.size() : "Pointer must be within valid history bounds";
        return history.get(pointer);
    }

    /**
     * Moves the pointer one step toward newer commands and returns the command
     * at that position. If the pointer moves past the newest entry (i.e.
     * reaches {@code history.size()}), {@code EMPTY_STRING} is returned so the caller can
     * clear the text field.
     *
     * @return the newer command at the new pointer position, or {@code EMPTY_STRING} when
     *         past the newest entry.
     */
    public String navigateDown() {
        if (pointer < history.size()) {
            pointer++;
        }
        if (pointer >= history.size()) {
            return EMPTY_STRING;
        }
        assert 0 <= pointer && pointer < history.size() : "Pointer must be valid before access";
        return history.get(pointer);
    }

    /**
     * Returns the number of commands currently stored in the history.
     */
    public int size() {
        return history.size();
    }

    /**
     * Checks whether the given command is identical to the last command in history.
     */
    private boolean isDuplicateOfLastCommand(String command) {
        String lastCommand = getLastCommand();
        return lastCommand != null && lastCommand.equals(command);
    }

    /**
     * Retrieves the most recently added command.
     *
     * @return the last command in history, or {@code null} if history is empty.
     */
    private String getLastCommand() {
        if (history.isEmpty()) {
            return null;
        }

        return history.get(history.size() - 1);
    }

    /**
     * Resets the navigation pointer to the end of the history, preparing it for
     * the next navigation sequence.
     */
    private void resetPointerToEnd() {
        pointer = history.size();
    }
}
