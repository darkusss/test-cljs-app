(ns events.events
  (:require [state :refer [app-state clear-twitch-link]]
            [helpers :refer [is-empty-link link-already-added validate-link-input]]
            [events.async :refer [get-twitch-clip]]))

(defn input-change
  [event]
  (swap! app-state assoc :twitch-link (-> event
                                          .-target
                                          .-value)))

(defn add-link-click
  [clip-link]
  (let [clipId (validate-link-input clip-link)
        not-empty (not (is-empty-link clip-link))
        not-exists (not (link-already-added clip-link))]
    (when (and clipId not-exists not-empty)
      (get-twitch-clip clipId))
    (clear-twitch-link)))

(defn add-link-on-enter
  [event clip-link]
  (when (= (.-code event) "Enter")
    (add-link-click clip-link)))