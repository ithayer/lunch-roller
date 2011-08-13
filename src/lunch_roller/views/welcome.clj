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

(defmacro defjson [route destruct & body]
  `(defpage ~route ~destruct
     (resp/json ~@body)))

(defpage "/" []
         (common/layout
           [:p "Welcome to lunch-roller"]))

;; Returns the list of restaurants.
(defjson "/api/places" {}
  (places/get-all))

;; Submit a vote, pass person _id and place _id.
(defjson [:post "/api/vote/add"] {person_id :person_id
                                  place_id :place_id}
  (votes/add person_id place_id))

;; Get votes for today.
(defjson "/api/votes/today" {}
  (votes/get-today))

;; Remove a vote.
(defjson [:post "/api/vote/del"] {person_id :person_id
                                  place_id :place_id}
  (votes/del person_id place_id))

;; Make a random selection.
