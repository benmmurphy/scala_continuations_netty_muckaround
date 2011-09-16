import javax.imageio._
import java.awt.image._
import java.io._
import java.awt._

class RichBufferedImage(val image:BufferedImage) {
  def toBytes(format:String) = {
    val stream = new ByteArrayOutputStream()
    ImageIO.write(image, format, stream)
    stream.toByteArray()
  }

  def withGraphics(block : (Graphics2D => Unit)) = {
    var graphics = image.createGraphics()
    try {
      block(graphics)
    } finally {
      graphics.dispose()
    }
  }
}

object RichBufferedImage {
  implicit def toRichBufferedImage(image:BufferedImage) : RichBufferedImage  = new RichBufferedImage(image)
}

