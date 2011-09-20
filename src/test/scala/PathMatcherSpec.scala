import org.scalatest.FlatSpec
import org.scalatest.matchers.ShouldMatchers

class PathMatcherSpec extends FlatSpec with ShouldMatchers {

  it should "be able to match terms" in {
    val matcher = PathMatcher("/foo/:term")

    matcher.matchPath("/foo/bah") should equal (Some(Map("term" -> "bah")))

  }

  it should "be able to match multiple terms" in {
    val matcher = PathMatcher("/foo/:term/:term2")
    matcher.matchPath("/foo/bah/bah2") should equal (Some(Map("term" -> "bah", "term2" -> "bah2")))
  }

  it should "compile to a regex that matches terms" in {
    val matcher = PathMatcher("/foo/:term")
    matcher.regex.toString should equal ("""/foo/(.*)""".r.toString)
  }

  it should "compile to a regex that matches multiple terms" in {
    val matcher = PathMatcher("/foo/:term/:term2")
    matcher.regex.toString should equal ("""/foo/(.*)/(.*)""".r.toString)
  }
}