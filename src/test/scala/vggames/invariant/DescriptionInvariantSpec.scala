package vggames.invariant
import java.util.regex.Pattern
import net.vidageek.invariant._
import org.junit.Assert._
import org.junit.runner.RunWith
import net.vidageek.invariant.Invariant
import net.vidageek.invariant.InvariantRunner
import org.junit.runner.RunWith

@RunWith(classOf[InvariantRunner])
class DescriptionInvariantTest {
  val ALLOWED_CHARS = "[\\w\\s\\Q<>&/.,();+=^:\\?[]-${}!|*\"'\\E]*"

  @Invariant(affects = ".*\\.html", folder = "src/main/resources")
  def allDescriptionsMustUseHtmlEntities(data : FileData) = {
    val content = data.getContent()
    assertFalse("Found invalid chars: [" + content.replaceAll(ALLOWED_CHARS, "") + "]",
      containsInvalidChars(content))
  }

  @Invariant(affects = ".*\\.html", folder = "src/main/resources")
  def regexMustBeProperlyWritten(data : FileData) = {
    val matcher = Pattern.compile("(?i)(regex)[^\"]").matcher(data.getContent())
    while (matcher.find()) {
      assertTrue("Found invalid way of writing RegEx", "RegEx".equals(matcher.group(1)))
    }
  }

  def containsInvalidChars(content : String) = !content.matches(ALLOWED_CHARS)
}
