package mzajac.scalajstut.server.api

import akka.http.scaladsl.server.{Directives, Route}
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.ExecutionContext

/**
  * Created by michal on 27.05.16.
  */
trait Routes extends Directives with LazyLogging {

  implicit def executionContext: ExecutionContext


  val routes: Route = {

    pathEndOrSingleSlash {
      get {
        getFromResource("index.html")
      }
    } ~ path("static" / Segments) { segments =>
      logger.info(s"Request for $segments")
      val path = segments.mkString("/")
      getFromResource(path)
    }


  }


  def loadFromResources(path: String) = {
    getClass.getResourceAsStream(path)
  }
}
