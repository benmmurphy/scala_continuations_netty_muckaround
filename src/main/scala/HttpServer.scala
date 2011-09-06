import org.jboss.netty.bootstrap._
import org.jboss.netty.channel.socket.nio._
import java.net._
import java.util.concurrent._

object HttpServer {
  def main(args:Array[String]) = {
    val bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(
      Executors.newCachedThreadPool(),
      Executors.newCachedThreadPool()))
    bootstrap.setPipelineFactory(new HttpServerPipelineFactory(new NounsHandler()))
    bootstrap.bind(new InetSocketAddress(8080))
    Unit
  }
}