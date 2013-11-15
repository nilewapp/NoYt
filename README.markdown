## NoYt

Aims to create a completely stateless Youtube subscription management web
application.

### Getting started

1. Git-clone this repository.

        $ git clone git://github.com/nilewapp/NoYt

2. cd into the project directory

        $ cd NoYt


3. Copy [application.conf.example](/src/main/resources/application.conf.example)
   to `/src/main/resources/application.conf`

        $ cp ./src/main/resources/application.conf.example ./src/main/resources/application.conf

4. Generate the trust store

        $ ./tools/gentruststores "<your password>"

5. Copy the generated file `keystore.jks` to `/src/main/resources/` or the
   location of your choice

6. Update `application.conf` to contain correct path to `keystore.jks` and the
   password that you choose for it (Note: if you copied the keystore into
   `/src/main/resources/` the default path is correct)

        ...
        truststore {
          path = <path to keystore.jks>
          pass = <your password>
        }
        ...

7. Launch SBT:

        $ sbt

8. Start the application:

        > re-start

9. Access the application at `https://localhost:8443/feed/<name of your favourite
   youtube channel>` in any web browser.


### Copyright and license

Copyright 2013 Robert Welin

NoYt is licensed under the [Apache License 2.0](https://www.apache.org/licenses/LICENSE-2.0).
