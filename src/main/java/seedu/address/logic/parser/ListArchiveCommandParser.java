package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ListArchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ListArchiveCommand object.
 */
public class ListArchiveCommandParser implements Parser<ListArchiveCommand> {

    @Override
    public ListArchiveCommand parse(String args) throws ParseException {
        if (args != null && !args.trim().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListArchiveCommand.MESSAGE_USAGE));
        }
        return new ListArchiveCommand();
    }

}
