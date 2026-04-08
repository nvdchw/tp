package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INDEX_TOO_LARGE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_RANGE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TOKEN;
import static seedu.address.logic.Messages.MESSAGE_RANGE_INDEX_LARGE;
import static seedu.address.logic.Messages.MESSAGE_RANGE_TOO_LARGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;

public class DeleteCommandParserTest {

    private static final String MESSAGE_USAGE = DeleteCommand.MESSAGE_USAGE;

    private final DeleteCommandParser parser = new DeleteCommandParser();

    // VALID INPUTS

    // EP: valid single index
    @Test
    public void parse_single_success() {
        assertParseSuccess(parser, "1",
                new DeleteCommand(List.of(Index.fromOneBased(1))));
    }

    // EP: valid multiple indexes
    @Test
    public void parse_multiple_success() {
        assertParseSuccess(parser, "1 2 3",
                new DeleteCommand(List.of(
                        Index.fromOneBased(1),
                        Index.fromOneBased(2),
                        Index.fromOneBased(3)
                )));
    }

    // EP: valid range
    @Test
    public void parse_range_success() {
        assertParseSuccess(parser, "2-4",
                new DeleteCommand(List.of(
                        Index.fromOneBased(2),
                        Index.fromOneBased(3),
                        Index.fromOneBased(4)
                )));
    }

    // EP: valid mixed input
    @Test
    public void parse_mixed_success() {
        assertParseSuccess(parser, "1 3-5",
                new DeleteCommand(List.of(
                        Index.fromOneBased(1),
                        Index.fromOneBased(3),
                        Index.fromOneBased(4),
                        Index.fromOneBased(5)
                )));
    }

    // EP: duplicates removed
    @Test
    public void parse_duplicates_success() {
        assertParseSuccess(parser, "1 1 2",
                new DeleteCommand(List.of(
                        Index.fromOneBased(1),
                        Index.fromOneBased(2)
                )));
    }

    // EP: overlapping indexes
    @Test
    public void parse_overlap_success() {
        assertParseSuccess(parser, "1 2-4 3",
                new DeleteCommand(List.of(
                        Index.fromOneBased(1),
                        Index.fromOneBased(2),
                        Index.fromOneBased(3),
                        Index.fromOneBased(4)
                )));
    }

    // BOUNDARY VALUES

    // BVA: single element range
    @Test
    public void parse_rangeSingle_success() {
        assertParseSuccess(parser, "3-3",
                new DeleteCommand(List.of(Index.fromOneBased(3))));
    }

    // BVA: max valid integer
    @Test
    public void parse_maxInt_success() {
        assertParseSuccess(parser, "2147483647",
                new DeleteCommand(List.of(Index.fromOneBased(2147483647))));
    }

    // BVA: integer overflow
    // EP: index too large
    @Test
    public void parse_overflow_failure() {
        assertParseFailure(parser, "2147483648",
                MESSAGE_INDEX_TOO_LARGE);
    }

    // BVA: zero index
    @Test
    public void parse_zero_failure() {
        assertParseFailure(parser, "0", MESSAGE_INVALID_INDEX);
    }

    // BVA: negative index
    @Test
    public void parse_negative_failure() {
        assertParseFailure(parser, "-1", MESSAGE_INVALID_INDEX);
    }

    // BVA: range too large
    @Test
    public void parse_largeRange_failure() {
        assertParseFailure(parser, "1-101", MESSAGE_RANGE_TOO_LARGE);
    }

    // BVA: range overflow
    @Test
    public void parse_rangeOverflow_failure() {
        assertParseFailure(parser, "2147483647-2147483648",
                MESSAGE_RANGE_INDEX_LARGE);
    }

    // INVALID INPUTS

    // EP: empty input
    @Test
    public void parse_empty_failure() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    // EP: invalid token
    @Test
    public void parse_invalidToken_failure() {
        assertParseFailure(parser, "a", MESSAGE_INVALID_TOKEN);
    }

    // EP: invalid format
    @Test
    public void parse_invalidFormat_failure() {
        assertParseFailure(parser, "1,2", MESSAGE_INVALID_TOKEN);
    }

    // EP: invalid range
    @Test
    public void parse_invalidRange_failure() {
        assertParseFailure(parser, "5-3", MESSAGE_INVALID_RANGE);
    }

    // EDGE / HEURISTIC

    // Heuristic: mixed valid + invalid token
    @Test
    public void parse_mixedInvalidToken_failure() {
        assertParseFailure(parser, "1 a", MESSAGE_INVALID_TOKEN);
    }

    // Heuristic: mixed valid + invalid range
    @Test
    public void parse_mixedInvalidRange_failure() {
        assertParseFailure(parser, "1 5-3", MESSAGE_INVALID_RANGE);
    }

    // Heuristic: whitespace handling
    @Test
    public void parse_whitespace_success() {
        assertParseSuccess(parser, "  1   2-3  ",
                new DeleteCommand(List.of(
                        Index.fromOneBased(1),
                        Index.fromOneBased(2),
                        Index.fromOneBased(3)
                )));
    }
}
