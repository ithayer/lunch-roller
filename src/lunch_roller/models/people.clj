(ns lunch-roller.models.people)

(def data (atom [{:_id 1 :name "Chris"}
                 {:_id 2 :name "Josh"}
                 {:_id 3 :name "Ignacio"}]))

(defn get-all []
  @data)

(defn get-by-id [id]
  (some (= id :_id) @data))

