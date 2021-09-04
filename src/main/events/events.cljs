(ns events.events
  (:require [state :refer [app-state clear-twitch-link]]
            [helpers :refer [is-empty-link link-exists]]))

(defn input-change
  [event]
  (swap! app-state assoc :twitch-link (-> event
                                          .-target
                                          .-value)))

(defn add-link-click
  [clip-link]
  (when (and (not (is-empty-link clip-link)) (not (link-exists clip-link)))
    (swap! app-state update :twitch-clips conj clip-link)
    (clear-twitch-link)))

(defn add-link-on-enter
  [event clip-link]
  (when (= (.-code event) "Enter")
    (add-link-click clip-link)))