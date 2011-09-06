import org.jboss.netty.channel._
import org.jboss.netty.handler._
import org.jboss.netty.handler.codec.http._
import HttpHeaders._
import HttpHeaders.Names._
import HttpResponseStatus._
import HttpVersion._
import scala.util.continuations._
import org.slf4j._

class HttpRequestHandler (val handler: Handler)  extends SimpleChannelUpstreamHandler {
  val logger:Logger = LoggerFactory.getLogger(classOf[NounsHandler]);  

  override def  messageReceived(ctx: ChannelHandlerContext, e: MessageEvent) = {
    val request = e.getMessage().asInstanceOf[HttpRequest]
    reset {
    
      val response = getResponse(request)
      response.setHeader(CONNECTION, "Close")
      val future = e.getChannel().write(response)
      future.addListener(ChannelFutureListener.CLOSE)  
    }
    logger.info("worker returning to the pool")
    /* we fall off here when we block */

  }

  def errorResponse() = {
    new DefaultHttpResponse(HTTP_1_1, INTERNAL_SERVER_ERROR)
  }

  def getResponse(request:HttpRequest) = {
    try {
      var serverResponse = handler.handle(ServerRequest(request))
      if (serverResponse == null) errorResponse() else serverResponse.toNettyResponse()
    } catch {
      case throwable:Throwable => errorResponse() 
    }
  }
}
