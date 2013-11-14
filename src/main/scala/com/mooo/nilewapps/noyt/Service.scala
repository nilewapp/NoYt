/**
 *  Copyright 2013 Robert Welin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mooo.nilewapps.noyt

import java.util.concurrent.TimeoutException
import javax.net.ssl.SSLException
import scala.concurrent.duration._
import scala.language.postfixOps

import akka.actor.Actor
import com.typesafe.config._
import spray.http.MediaTypes._
import spray.http.StatusCodes._
import spray.httpx.SprayJsonSupport._
import spray.routing.{ExceptionHandler, HttpService}
import spray.util.LoggingContext

import com.mooo.nilewapps.noyt.data.Video
  import com.mooo.nilewapps.noyt.service.{AssetService, FeedService}

/**
 * Actor that runs the service.
 */
class ServiceActor extends Actor with Service {
  def actorRefFactory = context
  def receive = runRoute(routes)
}

/**
 * Provides all functionality of the server.
 */
trait Service
  extends HttpService
  with AssetService
  with FeedService {

  implicit def timeoutExceptionHandler(implicit log: LoggingContext) =
    ExceptionHandler {
      case e: TimeoutException => ctx =>
        log.warning("Request {} could not be handled normally", ctx.request)
        ctx.complete(InternalServerError, "Service is taking too long to complete your request.")
    }

  val routes = {
    get {
      path("") {
        respondWithMediaType(`text/html`) {
          complete {
            html.index.render().body
          }
        }
      } ~
      path("stylesheets" / Rest) { resource =>
        stylesheet(resource)
      } ~
      path("javascript" / Rest) { resource =>
        javascript(resource)
      } ~
      path("feed" / Rest) { channels =>
        respondWithMediaType(`text/html`) {
          complete {
            html.feed.render(feed(channels.split('+')).slice(0, 25)).body
          }
        }
      }
    }
  }
}
