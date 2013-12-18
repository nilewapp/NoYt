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
package se.weln.noyt.net

import java.io.IOException
import scala.io.Source

class Downloader extends Gatherer {

  /**
   * Downloads the contents of a URL.
   */
  def apply(url: String, tries: Int): Option[String] = {
    try {
      Some(Source.fromURL(url)(scala.io.Codec("UTF-8")).mkString)
    } catch {
      case e: IOException => println("Download Error " + url); println(e);
        if (tries <= 1) None
        else apply(url, tries - 1)
    }
  }

  def apply(url: String) = apply(url, 3)
}
