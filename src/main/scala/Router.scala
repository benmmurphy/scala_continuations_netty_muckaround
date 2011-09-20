import scala.collection.immutable._
import util.continuations._

class Router {
  private var paths = List[Path]()

  def addGet(path:String, action: Action) = {
    paths =  new Path("GET", PathMatcher(path),  action) :: paths
  }

  def firstPath = {
    paths.head
  }

  def matchRequest(request:ServerRequest) = {

    var params:Option[Tuple2[Action, Map[String, String]]] = None

    for (path <- paths) {
      path.matches(request.method, request.path) match {
        case Some(p) => params = Some((path.action, p))
        case None => None
      }
    }

    params

  }
}
  
