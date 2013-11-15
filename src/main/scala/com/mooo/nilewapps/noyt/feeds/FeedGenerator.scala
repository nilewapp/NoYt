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
package com.mooo.nilewapps.noyt.feeds

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import com.mooo.nilewapps.noyt.data.Video
import com.mooo.nilewapps.noyt.net.ChannelFeedJsonLoader._
import com.mooo.nilewapps.noyt.parsing.JsonVideoParser._

class FeedGenerator {

  /**
   * Generates a subscription feed.
   */
  def feeds(
      channels: Seq[String],
      maxResults: Int): Future[Seq[Video]] = {
    Future.sequence(channels.map(downloadFeed(_, maxResults))).map {
      _.flatMap(parseFeed(_))
        .sortWith(_.publishTime.get after _.publishTime.get)
        .take(maxResults)
    }
  }
}

object FeedGenerator {

  def feeds = new FeedGenerator().feeds _
}
