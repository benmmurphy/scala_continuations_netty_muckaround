import scala.util.continuations._
import org.jboss.netty.handler.codec.http.HttpResponseStatus._
import org.slf4j._
import java.io._
import javax.imageio._
import java.awt._
import java.awt.image._
import RichBufferedImage._

object NounsServer extends HttpServer {
  override def handler = new NounsDSLHandler()
}

class NounsDSLHandler extends DSLHandler {
  val http:HttpClient = new HttpClient()
  val logger:Logger = LoggerFactory.getLogger(classOf[NounsDSLHandler]);

  def js = {
          <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
          <script>{JSCDATA("""
                    $(function() {
                       $("form").submit(function() {
                          window.location = "nouns/" + $("#term").val();
                          return false;
                       })
                    });
          """)}
          </script>
  }

  def form = {
        <form id="form">
        <label for="term">FUCK YEAH</label><input id="term" type="text" name="term"/>
        </form>
  }
  get("/nouns") { implicit ctx =>
      <html>
        <head>
          <title>Nouns</title>
          {js}
        </head>
        <body>
          {form}
        </body>
      </html>
  }

  get("/nouns/:term") { implicit ctx =>
    val term = params("term")

    <html>
      <head>
        <title>FUCK YEAH {term}</title>
        {js}
      </head>
      <body>
        <img src={"/images/" + term}/>
        {form}
      </body>
    </html>
  }
   
 
  getAsync("/images/:term")  { implicit ctx =>
      val term = params("term")
      val json = http.json("http://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + term)
      val imageUrl = json.string("responseData.results[0].url").get
      logger.info("Received imageUrl: {}", imageUrl)

      logger.info("Requesting image")
      val (imageBytes, _) = http.bytes(imageUrl)
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

