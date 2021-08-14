(ns view.main
  (:require [state :refer [app-state]]
            [events :refer [input-change]]))

(defn header
  []
  [:header
   [:h2 "I am a header"]])

(defn footer
  []
  [:footer
   [:h2 "I am a footer"]])


(defn twitch-link-input
  [value placeholder]
  [:input {:on-change #(input-change %)
           :placeholder placeholder
           :value value}])

(defn main
  []
  [:main
   [twitch-link-input (:twitch-link @app-state) "Twitch clip link"]
   [:h1 (:twitch-link @app-state)]])

(defn app
  []
  [:div
   [header]
   [main]
   [footer]])