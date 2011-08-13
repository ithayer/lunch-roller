(ns lunch-roller.models.people)

(def data (atom [{:id 1 :name "Chris"}
                 {:id 2 :name "Josh"}
                 {:id 3 :name "Ignacio"}]))

(defn get-all []
  @data)

(defn get-by-id [id]
  (some (= id :id) @data))

