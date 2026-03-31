package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandHistoryTest {

    private CommandHistory history;

    @BeforeEach
    public void setUp() {
        history = new CommandHistory();
    }

    // EP Group: Input Validity for add()

    @Test
    public void add_nullCommand_notStored() {
        // EP: null input
        history.add(null);
        assertEquals(0, history.size());
    }

    @Test
    public void add_blankCommand_notStored() {
        // EP: blank/whitespace-only input
        history.add("  ");
        assertEquals(0, history.size());
    }

    @Test
    public void add_validCommand_stored() {
        // EP: valid non-blank command
        history.add("list");
        assertEquals(1, history.size());
    }

    // EP Group: Empty History Navigation

    @Test
    public void navigateUp_emptyHistory_returnsEmpty() {
        // EP: navigateUp on empty history
        assertEquals("", history.navigateUp());
    }

    @Test
    public void navigateDown_emptyHistory_returnsEmpty() {
        // EP: navigateDown on empty history
        assertEquals("", history.navigateDown());
    }

    // EP Group: Single Entry Navigation

    @Test
    public void navigateUp_singleEntry_returnsThatEntry() {
        // EP: single entry in history
        history.add("list");
        assertEquals("list", history.navigateUp());
    }

    @Test
    public void navigateDown_withoutPriorNavigateUp_returnsEmpty() {
        // EP: navigateDown at default pointer position (fresh history)
        history.add("list");
        assertEquals("", history.navigateDown());
    }

    // EP Group: Multiple Entry Navigation

    @Test
    public void navigateUp_multipleEntries_returnsInReverseOrder() {
        // EP: multiple entries navigate in reverse order
        history.add("cmd1");
        history.add("cmd2");
        history.add("cmd3");
        assertEquals("cmd3", history.navigateUp());
        assertEquals("cmd2", history.navigateUp());
        assertEquals("cmd1", history.navigateUp());
    }

    @Test
    public void navigateUp_repeatedAtOldest_returnsOldestEntry() {
        // Boundary Value: pressing UP at oldest entry stays at oldest
        history.add("cmd1");
        history.add("cmd2");
        history.navigateUp(); // "cmd2"
        history.navigateUp(); // "cmd1"
        assertEquals("cmd1", history.navigateUp());
    }

    @Test
    public void navigateDown_afterNavigateUpToOldest_returnsNewerThenEmpty() {
        // EP: navigateDown after reaching oldest entry
        history.add("cmd1");
        history.add("cmd2");
        history.add("cmd3");
        history.navigateUp(); // "cmd3"
        history.navigateUp(); // "cmd2"
        history.navigateUp(); // "cmd1"
        assertEquals("cmd2", history.navigateDown());
        assertEquals("cmd3", history.navigateDown());
        // Boundary Value: navigateDown past newest returns empty
        assertEquals("", history.navigateDown());
    }

    @Test
    public void navigateDown_pastNewest_returnsEmpty() {
        // Boundary Value: repeated navigateDown past newest stays empty
        history.add("list");
        history.navigateUp(); // "list"
        history.navigateDown(); // past newest -> ""
        assertEquals("", history.navigateDown());
    }

    // EP Group: Consecutive Identical Commands (Collapse)

    @Test
    public void add_consecutiveIdenticalCommands_collapsedIntoOne() {
        // EP: multiple consecutive identical commands
        history.add("list");
        history.add("list");
        history.add("list");
        assertEquals(1, history.size());
    }

    @Test
    public void add_nonConsecutiveIdenticalCommands_bothStored() {
        // EP: identical commands separated by different command
        history.add("list");
        history.add("list s/name");
        history.add("list");
        assertEquals(3, history.size());
    }

    @Test
    public void navigateUp_withConsecutiveIdenticalCommandsCollapsed_skipsRepeats() {
        // EP: full collapse scenario with mixed consecutive and non-consecutive duplicates
        history.add("list");
        history.add("list");
        history.add("list");
        history.add("list s/name");
        history.add("list");

        // After collapse, history contains: ["list", "list s/name", "list"]
        assertEquals(3, history.size());

        assertEquals("list", history.navigateUp());
        assertEquals("list s/name", history.navigateUp());
        assertEquals("list", history.navigateUp());
    }

    // EP Group: Pointer Reset After Navigation

    @Test
    public void add_afterNavigation_resetsPointer() {
        // EP: adding command after navigation sequence should reset pointer
        history.add("cmd1");
        history.add("cmd2");
        history.navigateUp(); // "cmd2"
        history.navigateUp(); // "cmd1"

        history.add("cmd3");
        // Pointer should be reset; UP hits newest command first
        assertEquals("cmd3", history.navigateUp());
    }
}
