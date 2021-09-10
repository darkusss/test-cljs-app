(ns state
  (:require [reagent.core :refer [atom]]))

(defonce app-state (atom {:twitch-link ""
                          :twitch-clips []}))

(defn clear-twitch-link
  []
  (swap! app-state assoc :twitch-link ""))

(defn clear-twitch-clips
  []
  (swap! app-state assoc :twitch-clips []))