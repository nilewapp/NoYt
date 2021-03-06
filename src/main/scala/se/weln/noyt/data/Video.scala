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
package se.weln.noyt.data

/**
 * Defines the information content of a Video.
 */
case class Video(
  title: Option[String] = None,
  duration: Option[String] = None,
  id: Option[String] = None,
  channel: Option[String] = None,
  author: Option[String] = None,
  views: Option[Int] = None,
  publishTime: Option[java.util.Date] = None)
