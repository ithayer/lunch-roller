(ns lunch-roller.models.places
  (:use clj-yelp.v2))


(def data (atom {}))
(def api-account {:consumer-key "W1M5MaPhBsPy3yztRyA_8Q"
                  :consumer-secret "LtVwl1PDVFqmY10cHNOZhOdYe6M"
                  :token "umPBe3HtXzzn6pjOkS_WejzEWkI9V8eW"
                  :token-secret "FhGLZO6wCAUk-9IP2ZuEvQekBoY"})

(defn get-from-yelp [& [page]]
  (let [offset (if page
                 (* 20 page)
                 0)]
    (with-oauth api-account
                (:businesses
                  (search-businesses {:term "restaurants"
                                      :location "71 stevenson st, san francisco, ca"
                                      :radius 800
                                      :results-offset offset
                                      :limit 20
                                      :sort 2})))))

(defn get-restaurants [cnt]
  (reduce concat 
          (pmap get-from-yelp 
                (range (int (/ cnt 20))))))

(defn init []
  (reset! data (get-restaurants 100)))

(defn get-all []
  @data)
