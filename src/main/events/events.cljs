(ns events.events
  (:require [state :refer [app-state clear-twitch-link! show-input-error!]]
            [events.async :refer [add-twitch-clip send-clips]]
            [clojure.string :as string]))


(defn link-in-list
  [clip-link]
  (some #(= clip-link (:id %)) (:twitch-clips @app-state)))

(defn is-empty-str
  [str]
  (string/blank? str))

(defn validate-link-input
  [link]
  (let [clip-id-pos (.indexOf link "/clip/")]
    (if (not= clip-id-pos -1)
      (subs link (+ clip-id-pos 6))
      false)))

(defn not-empty-str
  [input-clip]
  (not (is-empty-str input-clip)))

(defn not-link-in-list
  [link]
  (not (link-in-list link)))

(defn add-link
  [clip-link]
  (let [clipId (validate-link-input clip-link)]
    (if (and clipId (not-empty-str clipId) (not-link-in-list clipId))
      (add-twitch-clip clipId)
      (show-input-error!))
    (clear-twitch-link!)))

(defn send-clips-to
  [clips]
  (send-clips clips))

(defn add-link-on-enter
  [event clip-link]
  (when (= (.-code event) "Enter")
    (add-link clip-link)))