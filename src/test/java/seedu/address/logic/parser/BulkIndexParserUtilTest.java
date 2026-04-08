package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INDEX_TOO_LARGE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.Messages.MESSAGE_INVALID_RANGE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_TOKEN;
import static seedu.address.logic.Messages.MESSAGE_RANGE_INDEX_LARGE;
import static seedu.address.logic.Messages.MESSAGE_RANGE_TOO_LARGE;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Tests for {@code BulkIndexParserUtil}.
 */
public class BulkIndexParserUtilTest {

    private static final String USAGE = "usage";
    private static final String INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, USAGE);

    // VALID INPUTS

    // EP: valid single index
    @Test
    public void parse_single_success() throws Exception {
        assertEquals(List.of(Index.fromOneBased(1)),
                BulkIndexParserUtil.parseBulkIndexes("1", USAGE));
    }

    // EP: multiple indexes
    @Test
    public void parse_multiple_success() throws Exception {
        assertEquals(List.of(
                        Index.fromOneBased(1),
                        Index.fromOneBased(2),
                        Index.fromOneBased(3)),
                BulkIndexParserUtil.parseBulkIndexes("1 2 3", USAGE));
    }

    // EP: valid range
    @Test
    public void parse_range_success() throws Exception {
        assertEquals(List.of(
                        Index.fromOneBased(2),
                        Index.fromOneBased(3),
                        Index.fromOneBased(4)),
                BulkIndexParserUtil.parseBulkIndexes("2-4", USAGE));
    }

    // EP: mixed input
    @Test
    public void parse_mixed_success() throws Exception {
        assertEquals(List.of(
                        Index.fromOneBased(1),
                        Index.fromOneBased(3),
                        Index.fromOneBased(4),
                        Index.fromOneBased(5)),
                BulkIndexParserUtil.parseBulkIndexes("1 3-5", USAGE));
    }

    // EP: duplicates + unsorted
    @Test
    public void parse_duplicate_success() throws Exception {
        assertEquals(List.of(
                        Index.fromOneBased(1),
                        Index.fromOneBased(2),
                        Index.fromOneBased(3)),
                BulkIndexParserUtil.parseBulkIndexes("3 1 2 2", USAGE));
    }

    // BOUNDARY VALUES

    // BVA: single element range
    @Test
    public void parse_rangeSingle_success() throws Exception {
        assertEquals(List.of(Index.fromOneBased(3)),
                BulkIndexParserUtil.parseBulkIndexes("3-3", USAGE));
    }

    // BVA: max allowed range
    @Test
    public void parse_rangeMax_success() throws Exception {
        assertEquals(100,
                BulkIndexParserUtil.parseBulkIndexes("1-100", USAGE).size());
    }

    // BVA: range too large
    @Test
    public void parse_rangeTooLarge_failure() {
        assertThrows(ParseException.class, MESSAGE_RANGE_TOO_LARGE, () ->
                BulkIndexParserUtil.parseBulkIndexes("1-101", USAGE));
    }

    // BVA: max integer
    @Test
    public void parse_maxInt_success() throws Exception {
        assertEquals(List.of(Index.fromOneBased(2147483647)),
                BulkIndexParserUtil.parseBulkIndexes("2147483647", USAGE));
    }

    // BVA: integer overflow
    @Test
    public void parse_overflow_failure() {
        assertThrows(ParseException.class, MESSAGE_INDEX_TOO_LARGE, () ->
                BulkIndexParserUtil.parseBulkIndexes("2147483648", USAGE));
    }

    // BVA: range max integer
    @Test
    public void parse_rangeMaxInt_success() throws Exception {
        assertEquals(List.of(Index.fromOneBased(2147483647)),
                BulkIndexParserUtil.parseBulkIndexes("2147483647-2147483647", USAGE));
    }

    // BVA: range overflow
    @Test
    public void parse_rangeOverflow_failure() {
        assertThrows(ParseException.class, MESSAGE_RANGE_INDEX_LARGE, () ->
                BulkIndexParserUtil.parseBulkIndexes("2147483647-2147483648", USAGE));
    }

    // BVA: zero index
    @Test
    public void parse_zero_failure() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () ->
                BulkIndexParserUtil.parseBulkIndexes("0", USAGE));
    }

    // BVA: negative index
    @Test
    public void parse_negative_failure() {
        assertThrows(ParseException.class, MESSAGE_INVALID_INDEX, () ->
                BulkIndexParserUtil.parseBulkIndexes("-1", USAGE));
    }

    // INVALID INPUTS

    // EP: empty input
    @Test
    public void parse_empty_failure() {
        assertThrows(ParseException.class, INVALID_FORMAT, () ->
                BulkIndexParserUtil.parseBulkIndexes("", USAGE));
    }

    // EP: invalid token
    @Test
    public void parse_invalidToken_failure() {
        assertThrows(ParseException.class, MESSAGE_INVALID_TOKEN, () ->
                BulkIndexParserUtil.parseBulkIndexes("a", USAGE));
    }

    // EP: invalid format (comma)
    @Test
    public void parse_invalidFormat_failure() {
        assertThrows(ParseException.class, MESSAGE_INVALID_TOKEN, () ->
                BulkIndexParserUtil.parseBulkIndexes("1,2", USAGE));
    }

    // EP: invalid token with plus sign
    @Test
    public void parse_plusSign_failure() {
        assertThrows(ParseException.class, MESSAGE_INVALID_TOKEN, () ->
                BulkIndexParserUtil.parseBulkIndexes("+1", USAGE));
    }

    // EP: invalid range
    @Test
    public void parse_invalidRange_failure() {
        assertThrows(ParseException.class, MESSAGE_INVALID_RANGE, () ->
                BulkIndexParserUtil.parseBulkIndexes("5-3", USAGE));
    }

    // EP: malformed range token
    @Test
    public void parse_malformedRange_failure() {
        assertThrows(ParseException.class, MESSAGE_INVALID_TOKEN, () ->
                BulkIndexParserUtil.parseBulkIndexes("1--3", USAGE));
    }

    // EP: non-numeric range part
    @Test
    public void parse_nonNumericRange_failure() {
        assertThrows(ParseException.class, MESSAGE_INVALID_TOKEN, () ->
                BulkIndexParserUtil.parseBulkIndexes("1-a", USAGE));
    }

    // EDGE / HEURISTIC

    // Heuristic: mixed valid + invalid
    @Test
    public void parse_mixedInvalid_failure() {
        assertThrows(ParseException.class, MESSAGE_INVALID_TOKEN, () ->
                BulkIndexParserUtil.parseBulkIndexes("1 a 3", USAGE));
    }

    // Heuristic: whitespace
    @Test
    public void parse_whitespace_success() throws Exception {
        assertEquals(List.of(
                        Index.fromOneBased(1),
                        Index.fromOneBased(2),
                        Index.fromOneBased(3)),
                BulkIndexParserUtil.parseBulkIndexes("  1   2   3  ", USAGE));
    }
}
