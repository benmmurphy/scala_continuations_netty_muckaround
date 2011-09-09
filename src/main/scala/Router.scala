import scala.collection.immutable._

trait RouterDSL  { 
  val _router = new Router()

  def get(path:String)(block : (Context  => Unit)) = {
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

class Router {
  private var paths = List[(String, (Context) => Unit)]()

  def addGet(path:String, block : (Context => Unit)) = {
    paths =  (path,  block) :: paths
  }

  def firstPath = {
    paths.head
  }
}
  
