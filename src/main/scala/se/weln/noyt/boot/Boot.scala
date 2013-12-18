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
package se.weln.noyt.boot

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http

import se.weln.noyt.config.ServerConfig
import se.weln.noyt.ServiceActor

/**
 * Starts the server.
 */
object Boot extends App with SslConfig {

  implicit val system = ActorSystem("NoYt")

  /* Create and start the service actor */
  val handler = system.actorOf(Props[ServiceActor], "handler")

  ServerConfig.load()

  val config = ServerConfig.config

  /* Apply proxy settings */
  if (config.proxyEnable) {
    System.setProperty("proxyHost", config.proxyHost)
    System.setProperty("proxyPort", config.proxyPort)
  }

  /* Create and bind the http server */
  IO(Http) ! Http.Bind(handler,
    config.interface,
    port = config.port)
}
