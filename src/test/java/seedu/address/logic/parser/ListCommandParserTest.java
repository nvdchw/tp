package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_noSort_success() {
        assertParseSuccess(parser,
                "",
                new ListCommand(""));
    }

    @Test
    public void parse_validSortName_success() {
        assertParseSuccess(parser,
                " " + PREFIX_SORT + "name",
                new ListCommand("name"));
    }

    @Test
    public void parse_invalidSort_failure() {
        assertParseFailure(parser,
                " " + PREFIX_SORT + "invalid",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_caseInsensitiveSort_success() {
        assertParseSuccess(parser,
                " " + PREFIX_SORT + "Name",
                new ListCommand("name"));
    }
}

