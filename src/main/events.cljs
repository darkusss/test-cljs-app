(ns events
  (:require [state :refer [app-state]]))

(defn input-change
  [event]
  (swap! app-state assoc :twitch-link (-> event
                                          .-target
                                          .-value)))

(defn increment
  [event]
  (.preventDefault event)
  (swap! app-state update-in [:count] inc))

(defn decrement
  [event]
  (.preventDefault event)
  (swap! app-state update-in [:count] dec))