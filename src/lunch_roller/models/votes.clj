(ns lunch-roller.models.votes
  (require [clj-time.core :as time]))

(def data (atom []))

(defn add-vote [{person :person_id
                 place  :place_id}]
  (swap! data conj {:person_id person :place_id place :time (time/now)}))

(defn get-all []
  @data)

(defn same-day [t1 t2]
  (every? identity (map #(= (% t1) (% t2)) [time/year time/month time/day])))

(defn get-today []
  (filter (partial same-day (time/now))))