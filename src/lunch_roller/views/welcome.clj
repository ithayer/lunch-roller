(ns lunch-roller.views.welcome
  (:require [lunch-roller.views.common :as common]
            [noir.content.pages :as pages])
  (:use noir.core
        hiccup.core
        hiccup.page-helpers))

(defpage "/" []
         (common/layout
           [:p "Welcome to lunch-roller"]))
