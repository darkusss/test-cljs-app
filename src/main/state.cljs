(ns state
  (:require [reagent.core :refer [atom]]))

(defonce app-state (atom {:twitch-link ""
                          :twitch-clips []}))

(defn clear-twitch-link!
  []
  (swap! app-state assoc :twitch-link ""))

(defn clear-twitch-clips!
  []
  (swap! app-state assoc :twitch-clips []))

(defn remove-by-id [coll id]
  (remove #(= id (:id %)) coll))

(defn delete-clip!
  [clip-id]
  (swap! app-state update :twitch-clips remove-by-id clip-id))