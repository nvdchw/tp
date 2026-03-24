package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsPredicate;
/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TAG);

        boolean hasName = argMultimap.getValue(PREFIX_NAME).isPresent();
        boolean hasTag = argMultimap.getValue(PREFIX_TAG).isPresent();

        if (hasName == hasTag) { // both true or both false
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (hasName) {
            String keyword = argMultimap.getValue(PREFIX_NAME).get().trim();
            NameContainsKeywordsPredicate predicate =
                    new NameContainsKeywordsPredicate(Arrays.asList(keyword.split("\\s+")));
            return new FindCommand(predicate);
        }

        String tag = argMultimap.getValue(PREFIX_TAG).get().trim();
        TagContainsPredicate predicate = new TagContainsPredicate(tag);
        return new FindCommand(predicate);
    }

}
