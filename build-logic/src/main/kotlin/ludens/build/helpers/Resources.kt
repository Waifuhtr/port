package ludens.build.helpers

/**
 * Kotlin reserved words that cannot be emitted as raw identifiers in generated source.
 *
 * When a resource name matches one of these values, the generator wraps it in backticks so
 * the resulting identifier remains valid Kotlin.
 */
val KotlinKeywords = setOf(
    "as",
    "break",
    "class",
    "continue",
    "do",
    "else",
    "false",
    "for",
    "fun",
    "if",
    "in",
    "interface",
    "is",
    "null",
    "object",
    "package",
    "return",
    "super",
    "this",
    "throw",
    "true",
    "try",
    "typealias",
    "typeof",
    "val",
    "var",
    "when",
    "while"
)


/**
 * Normalizes an arbitrary string into a Kotlin-safe resource name.
 *
 * Rules applied:
 * - lowercases the input
 * - replaces non `[A-Za-z0-9_]` characters with `_`
 * - prefixes `_` when the result starts with a digit
 * - wraps the result in backticks if it matches a Kotlin keyword
 */
fun parseResourceName(raw: String): String {
    return raw.lowercase().replace(Regex("[^a-zA-Z0-9_]"), "_").let {
        val result = if (it.firstOrNull()?.isDigit() == true) "_$it"
        else it

        if (result in KotlinKeywords) "`$result`" else result
    }
}

/**
 * Converts a filename into a Kotlin-safe resource name by removing the extension first.
 *
 * Example: `my-font.ttf` becomes `my_font`.
 */
fun parseResourceNameFromFilename(filename: String): String {
    return parseResourceName(filename.substringBeforeLast("."))
}
