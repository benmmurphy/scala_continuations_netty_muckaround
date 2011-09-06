import org.jboss.netty.channel._
import org.jboss.netty.channel.Channels._
import org.jboss.netty.handler.codec.http._

class HttpServerPipelineFactory(val handler: Handler)  extends ChannelPipelineFactory {
  def getPipeline() : ChannelPipeline = {
    val pipe = pipeline()
    pipe.addLast("decoder", new HttpRequestDecoder())
    pipe.addLast("encoder", new HttpResponseEncoder())
    pipe.addLast("deflater", new HttpContentCompressor())
    pipe.addLast("handler", new HttpRequestHandler(handler))
    pipe
  }
}

