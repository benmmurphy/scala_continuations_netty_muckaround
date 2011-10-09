import org.jboss.netty.bootstrap._
import org.jboss.netty.channel.socket.nio._
import java.net._
import java.util.concurrent._
import util.Properties

trait HttpServer {
  def handler:Handler

  def main(args:Array[String]) : Unit = {
    val port = Properties.envOrElse("PORT", "8080").toInt
    val bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
      Executors.newCachedThreadPool(),
      Executors.newCachedThreadPool()))
    bootstrap.setPipelineFactory(new HttpServerPipelineFactory(handler))
    bootstrap.bind(new InetSocketAddress(port))
  }
}
