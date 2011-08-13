(ns lunch-roller.views.welcome
  (:require [lunch-roller.views.common :as common]
            [lunch-roller.models.places :as places]
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

