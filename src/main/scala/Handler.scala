import scala.util.continuations._
trait Handler {
  def handle(request:ServerRequest) : ServerResponse @suspendable
}
