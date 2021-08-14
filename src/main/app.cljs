(ns app
  (:require
   [reagent.dom :as rdom]
   [view.main :refer [app]]))

(defn ^:dev/after-load start []
  (rdom/render [app]
               (js/document.getElementById "root")))
