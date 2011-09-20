import util.continuations._

trait Action {
  def apply(context:Context) : ServerResponse @suspendable
}

case class SuspendableAction(val action:(Context) => ServerResponse @suspendable) extends Action {
  def apply(context:Context) = {
    action(context)
  }
}

case class NormalAction(val action:(Context) => ServerResponse) extends Action {
  def apply(context:Context) = {
    shiftUnit(action(context))
  }
}