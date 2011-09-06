import javax.imageio._
import java.awt.image._
import java.io._

class RichBufferedImage(val image:BufferedImage) {
  def toBytes(format:String) = {
    val stream = new ByteArrayOutputStream()
    ImageIO.write(image, format, stream)
    stream.toByteArray()
  }
}

object RichBufferedImage {
  implicit def toRichBufferedImage(image:BufferedImage) : RichBufferedImage  = new RichBufferedImage(image)
}

