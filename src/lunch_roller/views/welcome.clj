(ns lunch-roller.views.welcome
  (:require [lunch-roller.views.common :as common]
            [lunch-roller.models.people :as people]
            [lunch-roller.models.places :as places]
            [lunch-roller.models.votes :as votes]
            [noir.content.pages :as pages]
            [noir.response :as resp])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers))

(defpage "/" []
         (common/layout
           [:p "Welcome to lunch-roller"]))

(defmacro defjson [route destruct & body]
  `(defpage ~route ~destruct
     (resp/json ~@body)))

;; Returns the list of restaurants.
(defjson "/api/places" {person_id :person_id}
  (votes/add-user-votes person_id (places/get-all)))

;; Returns the list of votes.
(defjson "/api/votes" {}
  @votes/data)

;; Submit a vote, pass person id and place id.
;; Returns
;;   0 - success, added.
;;   1 - exists
;;   2 - other error
;;
(defjson [:post "/api/vote/add"] {person_id :person_id
                                  place_id :place_id}
  (if (or (nil? person_id)
          (nil? place_id))
    2
    (let [result (votes/add person_id place_id)]
      (if (nil? result) 1 0))))

;; Get votes for today.
(defjson "/api/votes/today" {}
  (votes/get-today))

;; Remove a vote.
;; Returns
;;   0 - success, deleted.
;;   2 - other error.
;;   
(defjson [:post "/api/vote/del"] {person_id :person_id
                                  place_id :place_id}
  (if (or (nil? person_id)
          (nil? place_id))
    2
    (do (votes/del person_id place_id) 0)))

;; Make a random selection.
(defjson "/api/select" {}
  (let [place_id (votes/select)
        result (places/get-by-id place_id)]
    (println "GOT " result " FOR ID " place_id " AND PLACES: " (first @places/data))
    result))