import scala.collection.immutable._
import scala.util.continuations._

trait RouterDSL  { 
  val _router = new Router()

  def get(path:String)(block : (Context)  => ServerResponse @suspendable) = {
    _router.addGet(path, block)  
  }

  def router = {
    _router
  }

  def request(implicit ctx:Context) : ServerRequest = {
    null
  }

  def params(implicit ctx:Context) : Map[String, String] = {
    null
  }

}

case class Path (
  val method:String,
  val path:String,
  val action:(Context) => ServerResponse @suspendable) {
}

class Router {
  private var paths = List[Path]()

  def addGet(path:String, block : (Context) => ServerResponse @suspendable) = {
    paths =  new Path("GET", path,  block) :: paths
  }

  def firstPath = {
    paths.head
  }

  def matchRequest(request:ServerRequest) = {
    paths.find(path => path.method == request.method && path.path == request.path)
  }
}
  
