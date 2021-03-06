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

object YoutubeAPI {

  /**
   * Base URL of the Youtube API.
   */
  val BaseURL =
    "https://gdata.youtube.com/feeds/api/users/%s/uploads?alt=json&max-results=%d"

  /**
   * Returns the URL for the video feed of a channel.
   */
  def jsonUploadsFeedURL(channel: String, maxResults: Int) =
    BaseURL.format(channel, maxResults)
}
