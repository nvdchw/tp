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
                + PREFIX_END_DATE + "2026-01-01", "Start date cannot be after end date!");
    }

    @Test
    public void parse_missingEndDate_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_START_DATE + "2026-01-01",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multiplePrefixes_throwsParseException() {
        // Name and Date search
        assertParseFailure(parser, " n/John d/2026-01-01", "Only one search type allowed.");

        // Name and Date Range search
        assertParseFailure(parser, " n/John sd/2026-01-01 ed/2026-01-02", "Only one search type allowed.");
    }
}
