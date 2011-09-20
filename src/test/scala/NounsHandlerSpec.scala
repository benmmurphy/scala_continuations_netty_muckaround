import org.scalatest._
import org.scalatest.matchers._
import java.io._
import javax.imageio._
import org.apache.commons.io._

class NounsHandlerSpec extends FlatSpec with ShouldMatchers {
    it should "caption an image" in {
        val handler = new NounsHandler
        val image = ImageIO.read(new File("test.png"))
        val bytes = handler.processImage(image, "Coke")
        FileUtils.writeByteArrayToFile(new File("output.png"), bytes)
        
    }
}
