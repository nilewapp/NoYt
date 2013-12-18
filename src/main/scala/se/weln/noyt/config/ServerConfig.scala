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
package se.weln.noyt.config

import java.io.File

import com.typesafe.config._

case class ServerConfig(
  domain: String = "https://localhost:8443/",
  interface: String = "localhost",
  port: Int = 8443,
  truststorePath: String = "/keystore.jks",
  truststorePassword: String = "soulkey",
  stylesheetDir: String = "/assets/stylesheets/",
  javascriptDir: String = "/assets/javascript/",
  proxyEnable: Boolean = false,
  proxyHost: String = "localhost",
  proxyPort: String = "8118")

object ServerConfig {

  private var _config = ServerConfig()

  def config = _config

  /**
   * Loads a configuration file. If no file is specified the default
   * configuration in "application.conf" is loaded.
   */
  def load(file: File = null) {

    val c =
      if (file == null) {
        ConfigFactory.load()
      } else {
        val f = ConfigFactory.parseFile(file).resolve()
        f.checkValid(f)
        f
      }

    _config = ServerConfig(
      domain = c.getString("http-server.domain"),
      interface = c.getString("http-server.interface"),
      port = c.getInt("http-server.port"),
      truststorePath = c.getString("truststore.path"),
      truststorePassword = c.getString("truststore.pass"),
      stylesheetDir = c.getString("assets.stylesheets"),
      javascriptDir = c.getString("assets.javascript"),
      proxyEnable = c.getBoolean("http-proxy.use-proxy"),
      proxyHost = c.getString("http-proxy.host"),
      proxyPort = c.getString("http-proxy.port"))
  }
}
