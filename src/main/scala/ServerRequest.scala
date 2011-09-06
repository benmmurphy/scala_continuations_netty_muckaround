import org.jboss.netty.handler.codec.http._
import collection.JavaConversions._

class ServerRequest (
  val params: scala.collection.mutable.Map[String,java.util.List[String]]) {

  def param(name:String) : Option[String] = {
    params.get(name) match {
      case None => None
      case Some(list) => Some(list.head)
    }
  }
}

object ServerRequest {
  def apply(req:HttpRequest) = {
    val queryStringDecoder = new QueryStringDecoder(req.getUri());
    
    new ServerRequest(queryStringDecoder.getParameters())
  }
}
