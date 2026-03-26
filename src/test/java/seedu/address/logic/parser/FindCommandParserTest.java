package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsPredicate;
import seedu.address.model.person.VisitContainsDatePredicate;

public class FindCommandParserTest {

    private static final String MESSAGE_MISSING_DATE_RANGE_PAIR = "Both sd/ and ed/ must be provided together.";
    private static final String MESSAGE_INVALID_DATE_RANGE = "Start date cannot be after end date!";
    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        FindCommand expectedFindCommand =
                new FindCommand(
                        new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));

        assertParseSuccess(parser, " n/Alice Bob", expectedFindCommand);
        assertParseSuccess(parser, "   n/Alice Bob   ", expectedFindCommand);
    }

    @Test
    public void parse_tagArgs_returnsFindCommand() {
        FindCommand expected =
                new FindCommand(new TagContainsPredicate("friends"));

        assertParseSuccess(parser, " t/friends", expected);
    }

    @Test
    public void parse_preamblePresent_throwsParseException() {
        assertParseFailure(parser, " randomText n/Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, " Alice Bob",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_datePresent_returnsFindCommand() throws Exception {
        // Test "today" keyword
        FindCommand expectedFindCommand = new FindCommand(
                new VisitContainsDatePredicate(LocalDate.now(), LocalDate.now()));
        assertParseSuccess(parser, " " + PREFIX_DATE + "today", expectedFindCommand);

        // Test specific date
        LocalDate target = LocalDate.of(2026, 12, 25);
        expectedFindCommand = new FindCommand(new VisitContainsDatePredicate(target, target));
        assertParseSuccess(parser, " " + PREFIX_DATE + "2026-12-25", expectedFindCommand);
    }

    @Test
    public void parse_dateRangePresent_returnsFindCommand() throws Exception {
        LocalDate start = LocalDate.of(2026, 1, 1);
        LocalDate end = LocalDate.of(2026, 1, 31);
        FindCommand expectedFindCommand = new FindCommand(new VisitContainsDatePredicate(start, end));
        assertParseSuccess(parser, " " + PREFIX_START_DATE + "2026-01-01 "
                + PREFIX_END_DATE + "2026-01-31", expectedFindCommand);
    }

    @Test
    public void parse_invalidDateRange_throwsParseException() {
        // Start date after End date
        assertParseFailure(parser, " " + PREFIX_START_DATE + "2026-12-31 "
                + PREFIX_END_DATE + "2026-01-01", MESSAGE_INVALID_DATE_RANGE);
    }

    @Test
    public void parse_missingDateRangePair_throwsParseException() {
        // Missing End Date (Orphaned sd/)
        assertParseFailure(parser, " " + PREFIX_START_DATE + "2026-01-01", MESSAGE_MISSING_DATE_RANGE_PAIR);

        // Missing Start Date (Orphaned ed/)
        assertParseFailure(parser, " " + PREFIX_END_DATE + "2026-01-01", MESSAGE_MISSING_DATE_RANGE_PAIR);
    }

    @Test
    public void parse_multiplePrefixes_throwsParseException() {
        String expectedMessage = FindCommandParser.MESSAGE_ONLY_ONE_SEARCH_TYPE;

        // Name and Date search
        assertParseFailure(parser, " n/John d/2026-01-01", expectedMessage);

        // Name and Date Range search
        assertParseFailure(parser, " n/John sd/2026-01-01 ed/2026-01-02", expectedMessage);

        // Date and Date Range search
        assertParseFailure(parser, " d/2026-01-01 sd/2026-01-01 ed/2026-01-02", expectedMessage);
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        // duplicate prefixes check
        String expectedMessage = String.format(
                seedu.address.logic.Messages.getErrorMessageForDuplicatePrefixes(
                        seedu.address.logic.parser.CliSyntax.PREFIX_NAME));

        assertParseFailure(parser, " n/Alice n/Bob", expectedMessage);
    }
}
