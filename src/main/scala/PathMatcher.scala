import collection.immutable.Map
import collection.mutable.ListBuffer
import util.matching.Regex

class PathMatcher private (val regex:Regex, val names:Seq[String]) {
  def matchPath(path:String) = {

    regex.unapplySeq(path) match {
      case None => None
      case Some(list) =>  {
        Some((names zip list).toMap)
      }

    }
  }
}

object PathMatcher {
  def apply(path:String) = {
    val names = ListBuffer[String]()
    val pathRegex = """(:[A-Za-z][A-Za-z0-9]*)""".r.replaceAllIn(path, { mtch =>
      names.append(mtch.group(1).substring(1))
      "(.*)"
    })
    new PathMatcher(pathRegex.r, names)
  }
}