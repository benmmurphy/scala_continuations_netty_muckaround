import com.ning.http.client._
import scala.util.continuations._
import org.apache.commons.io._
import org.codehaus.jackson.map._
import org.slf4j._
import com.jayway.jsonpath._

class HttpClient {
  val client:AsyncHttpClient = new AsyncHttpClient()
  val logger:Logger = LoggerFactory.getLogger(classOf[HttpClient]);
  def json(url:String) = {
    val response = get(url)
    val json = new ObjectMapper().readValue(response.getResponseBody(), classOf[Object])
    Some(new JSONResource(json))
  }



  def bytes(url:String)  = {
    val response = get(url)
    Some((IOUtils.toByteArray(response.getResponseBodyAsStream()), response.getContentType()))
  }

  def get(url:String) : Response @suspendable = {
    logger.info("getting url {}", url)
    var response:Response = null
    var throwable:Throwable = null
    shift { k: (Unit => Unit) =>
      val handler = new AsyncCompletionHandler[Unit] {
        override def onCompleted(e:Response) = {
          logger.info("response completed")
          response = e
          k()
          logger.info("returning client worker to the pool")
        }
        override def onThrowable(t:Throwable) = {
          logger.info("error occured {}", t)
          throwable = t
          k()
          logger.info("returning client worker to the pool")
        }
      }
      val l = client.prepareGet(url).execute(handler)
    }
    if (response != null) {
      logger.info("response returned {}", response.getStatusCode())
    }
    if (throwable != null) {
      throw throwable
    }

    response
  }
}

class JSONResource (val json:Object) {
  def string(path:String) : Option[String] = {
    val result:String = JsonPath.read(json, path)
    if (result == null)  None else Some(result)
  }
}
