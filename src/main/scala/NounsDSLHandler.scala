class NounsDSLHandler extends DSLHandler {
 
  get("/nouns/:term")  { implicit ctx =>
    println(params)
  } 
}

