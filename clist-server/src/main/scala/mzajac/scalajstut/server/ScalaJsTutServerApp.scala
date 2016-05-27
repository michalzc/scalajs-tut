package mzajac.scalajstut.server

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import mzajac.scalajstut.server.api.Routes

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Created by michal on 27.05.16.
  */
object ScalaJsTutServerApp extends App with Routes {

  implicit val system = ActorSystem("sclajstut-server-system")
  implicit val materializer = ActorMaterializer()

  implicit val executionContext = system.dispatcher


  val bindingFuture = Http().bindAndHandle(routes, "localhost", 8000)

  sys.addShutdownHook{
    import scala.concurrent.duration._
    val whenTerminated = system.terminate()
    Await.result(whenTerminated, 5.seconds)
  }
}
