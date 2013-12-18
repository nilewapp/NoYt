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
package se.weln.noyt.services

import scala.io.Source

import spray.http.MediaType
import spray.http.MediaTypes._
import spray.routing.Directives._

import se.weln.noyt.config.ServerConfig

/**
 * Defies routes to handle Assets such as stylesheets and javascript.
 */
trait AssetService {

  /**
   * Returns the contents of a resource from a path.
   */
  private def resource(path: String) = {
    val url = getClass.getResource(path)
    try {
      Source.fromURL(url).mkString
    } catch {
      case e: Exception => ""
    }
  }

  /**
   * Returns a route to some asset.
   */
  private def asset(mediaType: MediaType, dir: String, file: String) = {
    respondWithMediaType(mediaType) {
      complete {
        resource(dir + file)
      }
    }
  }

  /**
   * Returns a route to a css file.
   */
  def stylesheet(file: String) =
    asset(`text/css`, ServerConfig.config.stylesheetDir, file)

  /**
   * Returns a route to a javascript file.
   */
  def javascript(file: String) =
    asset(`application/javascript`, ServerConfig.config.javascriptDir, file)

}
