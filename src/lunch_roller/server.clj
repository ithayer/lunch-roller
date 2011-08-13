(ns lunch-roller.server
  (:require [noir.server :as server]
            [lunch-roller.models.places :as places]))

(server/load-views "src/lunch_roller/views/")

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (places/init)
    (server/start port {:mode mode
                        :ns 'lunch-roller})))

