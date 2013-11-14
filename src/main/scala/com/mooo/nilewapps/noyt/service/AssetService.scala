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
package com.mooo.nilewapps.noyt.service

import scala.io.Source

import com.typesafe.config._
import spray.http.MediaType
import spray.http.MediaTypes._
import spray.routing.Directives._

trait AssetService {

  private val config = ConfigFactory.load().getConfig("assets")

  private def resource(path: String) = {
    val url = getClass.getResource(path)
    Source.fromURL(url).mkString
  }

  private def asset(mediaType: MediaType, dir: String, file: String) = {
    respondWithMediaType(mediaType) {
      complete {
        resource(config.getString(dir) + file)
      }
    }
  }

  def stylesheet(file: String) =
    asset(`text/css`, "stylesheets", file)

  def javascript(file: String) =
    asset(`application/javascript`, "javascript", file)

}
