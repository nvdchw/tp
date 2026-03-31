package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INDEX_TOO_LARGE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_INDEX;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Phone;
import seedu.address.model.person.VisitDateTime;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not a non-zero unsigned integer)
     *         or if the value exceeds the allowable integer range.
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();

        // First check format (digits only)
        // Allow "-?\\d+" so negative numbers reach parseIndex().
        // This lets us return a specific "invalid index" error instead of a generic format error.
        if (!trimmedIndex.matches("\\d+")) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        int value;
        try {
            value = Integer.parseInt(trimmedIndex);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INDEX_TOO_LARGE);
        }

        if (value <= 0) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }

        return Index.fromOneBased(value);
    }

    /**
     * Parses {@code rawInput} into a single {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * Ensures that the input contains exactly one token representing an index. Inputs that are empty, contain multiple
     * tokens, or are not numeric will result in a {@code ParseException} with the provided {@code usageMessage}.
     * Delegates to {@link #parseIndex(String)} for validation of the index value.
     *
     * @throws ParseException if the input format is invalid or does not represent a valid index.
     */
    public static Index parseSingleIndexOrThrow(String rawInput, String usageMessage)
            throws ParseException {

        String trimmed = rawInput.trim();

        // Case 1: missing index
        if (trimmed.isEmpty()) {
            throw new ParseException(usageMessage);
        }

        String[] parts = trimmed.split("\\s+");

        // Case 2: more than one token
        if (parts.length != 1) {
            throw new ParseException(usageMessage);
        }

        String token = parts[0];

        // Case 3: not number-like (reject abc, -, etc.)
        if (!token.matches("-?\\d+")) {
            throw new ParseException(usageMessage);
        }

        return parseIndex(token);
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses a {@code String note} into a {@code Note}.
     * Lead and trail whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code note} is invalid.
     */
    public static Note parseNote(String note) throws ParseException {
        requireNonNull(note);
        String trimmedNote = note.trim();
        if (!Note.isValidNote(trimmedNote)) {
            throw new ParseException(Note.MESSAGE_CONSTRAINTS);
        }
        return new Note(trimmedNote);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String visitDateTime} into a {@code VisitDateTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code visitDateTime} is invalid.
     */
    public static VisitDateTime parseVisitDateTime(String visitDateTime) throws ParseException {
        requireNonNull(visitDateTime);
        String trimmedVisitDateTime = visitDateTime.trim();
        if (trimmedVisitDateTime.isEmpty()) {
            return new VisitDateTime(); // Return empty if not provided
        }
        if (!VisitDateTime.isValidVisitDateTime(trimmedVisitDateTime)) {
            throw new ParseException(VisitDateTime.MESSAGE_CONSTRAINTS);
        }
        return new VisitDateTime(trimmedVisitDateTime);
    }

    /**
     * Parses a {@code String date} into a {@code LocalDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param date The date string to be parsed (Expected format: YYYY-MM-DD).
     * @return The parsed LocalDate object.
     * @throws ParseException if the given {@code date} is invalid or not in the correct format.
     */
    public static LocalDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        try {
            return LocalDate.parse(trimmedDate);
        } catch (DateTimeParseException e) {
            throw new ParseException(VisitDateTime.MESSAGE_DATE_CONSTRAINTS);
        }
    }
}
