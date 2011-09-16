import scala.util.continuations._
import org.jboss.netty.handler.codec.http.HttpResponseStatus._
import org.slf4j._
import java.io._
import javax.imageio._
import java.awt._
import java.awt.image._
import RichBufferedImage._

class NounsDSLHandler extends DSLHandler {
  val http:HttpClient = new HttpClient()
  val logger:Logger = LoggerFactory.getLogger(classOf[NounsDSLHandler]);

  get("/nouns")  { implicit ctx =>
      val term = "foo"
      val json = http.json("http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + term).get
      val imageUrl = json.string("responseData.results[0].url").get
      logger.info("Received imageUrl: {}", imageUrl)

      logger.info("Requesting image")
      val (imageBytes, _) = http.bytes(imageUrl).get
      val bufferedImage = parseImage(imageBytes).get

      val bytes = processImage(bufferedImage, term)
         
      new ServerResponse(OK, bytes, "image/png")
  }
  
  def parseImage(bytes:Array[Byte]) = {
    try {
      Some(ImageIO.read(new ByteArrayInputStream(bytes)))
    } catch {
      case ioe: IOException => None
    }
  }

  def processImage(image:BufferedImage, term:String) = {
    val caption = "FUCK YEAH " + term.toUpperCase()
    image.withGraphics { graphics =>
      val font = new Font("Helvetica", Font.BOLD, 48)
      graphics.setFont(font)
      graphics.setColor(Color.WHITE)
      val bounds = font.getStringBounds(caption, graphics.getFontRenderContext())
      graphics.drawString(caption, image.getWidth() / 2 - (bounds.getWidth() / 2).toInt, image.getHeight())
    }

    image.toBytes("png")
  }
  
}

