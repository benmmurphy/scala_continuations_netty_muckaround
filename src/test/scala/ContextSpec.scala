import org.scalatest._
import org.scalatest.matchers._

class ContextSpec extends FlatSpec with ShouldMatchers {
  it should "merge path params with query params" in {
    val request = new ServerRequest("GET", "/path", Map("query_only" -> Seq("query1", "query2"), "both" -> Seq("both_query1", "both_query2")))
    val context = Context(request, Map("path_only" -> "path1", "both" -> "both_path"))

    context.params should equal(Map("query_only" -> "query1", "path_only" -> "path1", "both" -> "both_path"))

  }
}
