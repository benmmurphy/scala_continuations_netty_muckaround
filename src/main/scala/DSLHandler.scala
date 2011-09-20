import scala.util.continuations._
import org.jboss.netty.handler.codec.http.HttpResponseStatus._

trait DSLHandler extends Handler with RouterDSL {
  def handle(request:ServerRequest) : ServerResponse @suspendable = {
    val path = _router.matchRequest(request)
    path match {
        case None => shiftUnit(new ServerResponse(NOT_FOUND))
        case Some((action, params)) =>  action(Context(request, params))
    }
  }    
}

