(ns lunch-roller.models.places)

(def data (atom [{:_id 1 :name "Chipotle"    :address "2nd St." :yelp {:rating 4 :url "http://yelp.com"}},
                 {:_id 2 :name "Specialty's" :address "1st st." :yelp {:rating 3 :url "http://yelp.com"}}]))

(defn get-all []
  @data)