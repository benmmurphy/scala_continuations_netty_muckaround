import org.jboss.netty.handler.codec.http._
import org.jboss.netty.buffer._
import org.jboss.netty.util._
import org.jboss.netty.handler.codec.http.HttpVersion._

class ServerResponse private (
  val status: HttpResponseStatus,
  val body: Option[Either[String,Array[Byte]]] = None,
  val contentType: Option[String] = None) {

  def this(status:HttpResponseStatus, body:String) =  this(status, Some(Left(body)), Some("text/html;charset=utf8"))

  def this(status:HttpResponseStatus) = this(status, None, None)
  def this(status:HttpResponseStatus, body:Array[Byte], contentType:String) = this(status, Some(Right(body)), Some(contentType))
  
  def toNettyResponse() = {
    val response = new DefaultHttpResponse(HTTP_1_1, status) 
    body match {
      case Some(Left(str)) => response.setContent(ChannelBuffers.copiedBuffer(str, CharsetUtil.UTF_8))
      case Some(Right(bytes)) => response.setContent(ChannelBuffers.wrappedBuffer(bytes))
      case _ => Unit
    }
    if (!contentType.isEmpty) {
      response.setHeader("Content-Type", contentType.get)
    }
    response
  }
}

