#Running

1. install simple built tool https://github.com/harrah/xsbt/wiki
2. sbt run

#DSL Weirdness

    get("/nouns")  { implicit ctx =>
        println(params)
    }


the "implicit ctx =>" syntax is a little weird also we are not giving the user access to thread locals to allow them to access request/session/params/etc easily outside of the current method. 

there is a trade off. thread locals means that it is harder to integrate other peoples libraries that use @suspendable because there is no way to ensure they clean up our thread locals when they 'shift' and restore our thread locals when they callback into us. 

but no thread locals can be pain. not sure what to do at the moment.

#Example

    /* Provides main method to run the server */
    object NounsServer extends HttpServer {
      override def handler = new NounsDSLHandler()
    }

    class NounsDSLHandler extends DSLHandler {
      val http:HttpClient = new HttpClient()
      val logger:Logger = LoggerFactory.getLogger(classOf[NounsDSLHandler]);

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

      ....
    }

#See Also
http://days2011.scala-lang.org/node/138/288
