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
package se.weln.noyt

import akka.actor.Actor
import spray.http.MediaTypes._
import spray.http.StatusCodes
import spray.routing.HttpService

import se.weln.noyt.services.{AssetService, FeedService}

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

  val routes = {
    get {
      path("") { redirect("/feed", StatusCodes.PermanentRedirect) } ~
      path("stylesheets" / Rest) { stylesheet(_) } ~
      path("javascript" / Rest) { javascript(_) } ~
      path("feed" / Rest) { feed(_) }
    }
  }
}
