import org.scalatest._
import org.scalatest.matchers._

class DSLHandlerSpec extends FlatSpec with ShouldMatchers {
  it should "Allow parameters to be passed to the get method" in {
    val handler = new NounsDSLHandler()
    val path = handler.router.firstPath
    //path.action(new Context())
  }

}

