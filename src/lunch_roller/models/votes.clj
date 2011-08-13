(ns lunch-roller.models.votes
  (require [clj-time.core :as time]
           [clj-time.coerce :as time-coerce]))

(def data (atom []))

(defn- match-fn [person_id place_id]
  (fn [record] (and (= person_id (:person_id record))
                    (= place_id (:place_id record)))))

(defn add [person_id place_id & [vote]]
  (when-let [is-new (not (some (match-fn person_id place_id) @data))]
    (swap! data conj {:person_id person_id :place_id place_id :vote (or vote 1) :time (time-coerce/to-long (time/now))})))

(defn del [person_id place_id]
  (swap! data #(remove (match-fn person_id place_id) %)))

(defn get-all []
  @data)

(defn same-day [t1 t2]
  (every? identity (map #(= (% t1) (% t2)) [time/year time/month time/day])))

(defn get-today []
  (filter (partial same-day (time/now)) @data))

(defn add-user-votes [person_id places]
  (let [votes (into {} (map (juxt :place_id :vote) (filter #(= (:person_id %) person_id) @data)))]
    (for [p places]
      (assoc p :vote (get votes (:id p))))))

(defn select []
  "Selects randomly, assigning probability based on number of votes." {})