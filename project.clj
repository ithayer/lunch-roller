(defproject lunch-roller "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.2.1"]
                           [noir "1.1.1-SNAPSHOT"]
			   [clj-yelp "0.1.0-SNAPSHOT"]
                           [log4j "1.2.15" :exclusions [javax.mail/mail
                                                        javax.jms/jms
                                                        com.sun.jdmk/jmxtools
                                                        com.sun.jmx/jmxri]]
                           [clj-time "0.3.0"]
                           [clj-http "0.1.3"]
                           [congomongo "0.1.7-SNAPSHOT"]]
            :main lunch-roller.server)
