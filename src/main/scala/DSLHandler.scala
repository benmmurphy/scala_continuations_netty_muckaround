import scala.util.continuations._

trait DSLHandler extends Handler with RouterDSL {
  def handle(request:ServerRequest) : ServerResponse @suspendable = {
    shiftUnit(null)
  }    
}

