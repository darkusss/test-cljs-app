(ns events.events
  (:require [state :refer [app-state clear-twitch-link! show-input-error!]]
            [events.async :refer [add-twitch-clip]]
            [clojure.string :as string]))


(defn link-added
  [clip-link]
  (some #(= clip-link %) (:twitch-clips @app-state)))

(defn is-empty-link
  [clip-link]
  (string/blank? clip-link))

(defn validate-link-input
  [link]
  (let [clip-id-pos (.indexOf link "/clip/")]
    (if (not= clip-id-pos -1)
      (subs link (+ clip-id-pos 6))
      false)))

(defn add-link
  [clip-link]
  (let [clipId (validate-link-input clip-link)
        not-empty (not (is-empty-link clip-link))
        not-exist (not (link-added clip-link))]
    (if (and clipId not-exist not-empty)
      (add-twitch-clip clipId)
      (show-input-error!)) 
    (clear-twitch-link!)))

(defn add-link-on-enter
  [event clip-link]
  (when (= (.-code event) "Enter")
    (add-link clip-link)))