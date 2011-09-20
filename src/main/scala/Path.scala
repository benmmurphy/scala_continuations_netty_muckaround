import util.continuations._

case class Path (
  val method:String,
  val pathMatcher:PathMatcher,
  val action:Action) {

  def matches(method:String, path:String) = {
    if (method == method) {
      pathMatcher.matchPath(path)
    } else {
      None
    }
  }
}