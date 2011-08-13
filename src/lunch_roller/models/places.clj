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

(defn get-by-id [id]
  (some (comp (partial = id) :id) @data))

;;    "businesses": [
;;    {
;;      "categories": [
;;        [
;;          "Local Flavor",
;;          "localflavor"
;;        ],
;;        [
;;          "Mass Media",
;;          "massmedia"
;;        ]
;;      ],
;;      "display_phone": "+1-415-908-3801",
;;      "id": "yelp-san-francisco",
;;      "image_url": "http://s3-media2.px.yelpcdn.com/bphoto/7DIHu8a0AHhw-BffrDIxPA/ms.jpg",
;;      "location": {
;;        "address": [
;;          "706 Mission St"
;;        ],
;;        "city": "San Francisco",
;;        "coordinate": {
;;          "latitude": 37.786138600000001,
;;          "longitude": -122.40262130000001
;;        },
;;        "country_code": "US",
;;        "cross_streets": "3rd St & Opera Aly",
;;        "display_address": [
;;          "706 Mission St",
;;          "(b/t 3rd St & Opera Aly)",
;;          "SOMA",
;;          "San Francisco, CA 94103"
;;        ],
;;        "geo_accuracy": 8,
;;        "neighborhoods": [
;;          "SOMA"
;;        ],
;;        "postal_code": "94103",
;;        "state_code": "CA"
;;      },
;;      "mobile_url": "http://lite.yelp.com/biz/4kMBvIEWPxWkWKFN__8SxQ",
;;      "name": "Yelp",
;;      "phone": "4159083801",
;;      "rating_img_url": "http://media1.px.yelpcdn.com/static/201012161694360749/i/ico/stars/stars_3.png",
;;      "rating_img_url_large": "http://media3.px.yelpcdn.com/static/201012161053250406/i/ico/stars/stars_large_3.png",
;;      "rating_img_url_small": "http://media1.px.yelpcdn.com/static/201012162337205794/i/ico/stars/stars_small_3.png",
;;      "review_count": 3347,
;;      "snippet_image_url": "http://s3-media2.px.yelpcdn.com/photo/LjzacUeK_71tm2zPALcj1Q/ms.jpg",
;;      "snippet_text": "Sometimes we ask questions without reading an email thoroughly as many of us did for the last event.  In honor of Yelp, the many questions they kindly...",
;;      "url": "http://www.yelp.com/biz/yelp-san-francisco"
;;    }
;; ]
