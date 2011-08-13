(ns lunch-roller.models.places)

(defn get-all []
  [{:name "Chipotle"    :address "2nd St." :yelp {:rating 4 :url "http://yelp.com"}},
   {:name "Specialty's" :address "1st st." :yelp {:rating 3 :url "http://yelp.com"}}])