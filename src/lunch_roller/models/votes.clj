(ns lunch-roller.models.votes
  (require [clj-time.core :as time]))

(def data (atom []))

(defn add [person_id place_id]
  (swap! data conj {:person_id person_id :place_id place_id :time (time/now)}))

(defn del [person_id place_id]
  (swap! data #(remove (fn [record] (and (= person_id (:person_id record))
                                         (= place_id (:place_id record))))
                       %)))

(defn get-all []
  @data)

(defn same-day [t1 t2]
  (every? identity (map #(= (% t1) (% t2)) [time/year time/month time/day])))

(defn get-today []
  (filter (partial same-day (time/now)) @data))

