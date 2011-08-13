(ns lunch-roller.views.common
  (:use noir.core
        hiccup.core
        hiccup.page-helpers))

(defpartial layout [& content]
            (html5
              [:head
               [:title "lunch-roller"]
               (include-css "/css/reset.css")
               (include-js "/js/lib/underscore.js")
               (include-js "/js/lib/backbone.js")
               (include-js "/js/lib/jquery.js")
               (include-js "/js/lib/handlebars.js")
               (include-js "/js/lib/local-jquery.js")
               (include-js "/js/lib/local-handlebars.js")
               (include-js "/js/lib/jquery.validate.js")]
              [:body
               [:div#wrapper
                content]]))
