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
    public void add_invalidInputs_notStored() {
        // EP: Invalid inputs (null, blank) are not stored
        history.add(null);
        assertEquals(0, history.size());
        history.add("  ");
        assertEquals(0, history.size());
    }

    @Test
    public void add_validCommand_stored() {
        // EP: Valid non-blank command is stored
        history.add("list");
        assertEquals(1, history.size());
    }

    // EP Group: Empty History Navigation

    @Test
    public void navigate_emptyHistory_returnsEmpty() {
        // EP: Navigation on empty history returns empty string
        assertEquals("", history.navigateUp());
        assertEquals("", history.navigateDown());
    }

    // EP Group: Single Entry Navigation

    @Test
    public void navigateUp_singleEntry_returnsThatEntry() {
        // EP: Single entry is retrieved by navigateUp
        history.add("list");
        assertEquals("list", history.navigateUp());
    }

    @Test
    public void navigateDown_atDefaultPointerPosition_returnsEmpty() {
        // EP: NavigateDown at default position (fresh history) returns empty
        history.add("list");
        assertEquals("", history.navigateDown());
    }

    // EP Group: Multiple Entry Navigation

    @Test
    public void navigateUp_multipleEntries_returnsInReverseOrder() {
        // EP: Multiple entries navigate in reverse chronological order
        history.add("cmd1");
        history.add("cmd2");
        history.add("cmd3");
        assertEquals("cmd3", history.navigateUp());
        assertEquals("cmd2", history.navigateUp());
        assertEquals("cmd1", history.navigateUp());
    }

    @Test
    public void navigateUp_atOldestEntry_staysAtOldest() {
        // BV: Repeated navigateUp at oldest entry boundary stays at oldest
        history.add("cmd1");
        history.add("cmd2");
        history.navigateUp(); // "cmd2"
        history.navigateUp(); // "cmd1"
        assertEquals("cmd1", history.navigateUp());
    }

    @Test
    public void navigateDown_afterNavigateUpSequence_returnsInOrder() {
        // EP: NavigateDown after reaching oldest entry returns entries in forward order
        history.add("cmd1");
        history.add("cmd2");
        history.add("cmd3");
        history.navigateUp(); // "cmd3"
        history.navigateUp(); // "cmd2"
        history.navigateUp(); // "cmd1"
        assertEquals("cmd2", history.navigateDown());
        assertEquals("cmd3", history.navigateDown());
    }

    @Test
    public void navigateDown_pastNewestEntry_returnsEmptyAndStaysEmpty() {
        // BV: NavigateDown past newest entry boundary returns empty and stays empty
        history.add("list");
        history.navigateUp(); // "list"
        assertEquals("", history.navigateDown()); // past newest
        assertEquals("", history.navigateDown()); // stays empty
    }

    // EP Group: Consecutive Identical Commands (Collapse)

    @Test
    public void add_consecutiveIdenticalCommands_collapsedIntoOne() {
        // EP: Multiple consecutive identical commands are collapsed into single entry
        history.add("list");
        history.add("list");
        history.add("list");
        assertEquals(1, history.size());
    }

    @Test
    public void add_nonConsecutiveIdenticalCommands_allStored() {
        // EP: Identical commands separated by different command are stored separately
        history.add("list");
        history.add("list s/name");
        history.add("list");
        assertEquals(3, history.size());
    }

    @Test
    public void add_mixedConsecutiveAndNonConsecutiveDuplicates_collapsesAndStores() {
        // EP: Mixed scenario: consecutive duplicates collapsed, non-consecutive stored separately
        history.add("list");
        history.add("list");
        history.add("list");
        history.add("list s/name");
        history.add("list");

        // After collapse: ["list", "list s/name", "list"]
        assertEquals(3, history.size());
        assertEquals("list", history.navigateUp());
        assertEquals("list s/name", history.navigateUp());
        assertEquals("list", history.navigateUp());
    }

    // EP Group: Pointer Reset After Navigation

    @Test
    public void add_afterNavigation_resetsPointerToNewest() {
        // EP: Adding command after navigation resets pointer to newest entry
        history.add("cmd1");
        history.add("cmd2");
        history.navigateUp(); // "cmd2"
        history.navigateUp(); // "cmd1"

        history.add("cmd3");
        // Pointer reset; navigateUp goes to newest first
        assertEquals("cmd3", history.navigateUp());
    }
}
