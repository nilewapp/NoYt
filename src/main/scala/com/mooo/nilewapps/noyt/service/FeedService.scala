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

import scala.concurrent._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.language.postfixOps

import spray.routing.Directives._

import com.mooo.nilewapps.noyt.data.Video
import com.mooo.nilewapps.noyt.datagathering.ChannelFeedJsonLoader
import com.mooo.nilewapps.noyt.parsing.JsonVideoParser

trait FeedService {

  /**
   * Generates a subscription feed.
   */
  def feed(channels: Seq[String]): Seq[Video] = {
    (channels.map(ChannelFeedJsonLoader(_)).map(_.map(JsonVideoParser(_))) flatMap {
      Await.result(_, 20 seconds)
    } toList).sortBy(_.publishTime).reverse
  }
}
