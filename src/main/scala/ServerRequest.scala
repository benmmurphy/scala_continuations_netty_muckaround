import org.jboss.netty.handler.codec.http._
import collection.JavaConversions._

class ServerRequest (
  val method: String,
  val path: String,
  val params: scala.collection.immutable.Map[String,Seq[String]]) {

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
    val params = mapAsScalaMap(queryStringDecoder.getParameters()).mapValues(asScalaBuffer _).toMap

    new ServerRequest(req.getMethod().getName(), queryStringDecoder.getPath(), params)
  }
}
