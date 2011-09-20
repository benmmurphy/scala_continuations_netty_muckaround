import util.continuations._
import scala.xml._
import org.jboss.netty.handler.codec.http.HttpResponseStatus._

trait RouterDSL  {
  val _router = new Router()

  def getAsync(path:String)(block : (Context)  => ServerResponse @suspendable) = {
    _router.addGet(path, new SuspendableAction(block))
  }

  def get(path: String)(block : (Context) => ServerResponse) = {
    _router.addGet(path, new NormalAction(block))
  }

  def router = {
    _router
  }

  def request(implicit ctx:Context) : ServerRequest = {
    ctx.request
  }

  def params (implicit ctx:Context) : Map[String, String] = {
    ctx.params
  }

  def params (name:String)(implicit ctx:Context) = {
    ctx.params.apply(name)
  }

  implicit def elemToServerResponse(elem : Elem) : ServerResponse = {
    new ServerResponse(OK, elem.toString)
  }

  object JSCDATA {
    def apply(in: String): Node = Unparsed("//<![CDATA[\n" + in + "\n//]]>") 
  }

}
