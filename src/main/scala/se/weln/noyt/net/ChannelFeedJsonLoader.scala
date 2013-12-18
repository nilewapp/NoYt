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

import scala.concurrent._
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Defines a method to download a feed using the Youtube API.
 */
class ChannelFeedJsonLoader(val gatherer: Gatherer) {

  /**
   * Downloads the feed of a channel using the Youtube API.
   */
  def apply(channel: String, maxResults: Int): Future[Option[String]] =
    future(gatherer(YoutubeAPI.jsonUploadsFeedURL(channel, maxResults)))
}

object ChannelFeedJsonLoader {

  /**
   * Returns a new ChannelFeedJsonLoader with a standard Downloader.
   */
  def downloadFeed =
    new ChannelFeedJsonLoader(new Downloader)
}
