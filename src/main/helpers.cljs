(ns helpers
  (:require [state :refer [app-state]]
            [clojure.string :as string]))

(defn link-already-added
  [clip-link]
  (some #(= clip-link %) (:twitch-clips @app-state)))

(defn is-empty-link
  [clip-link]
  (string/blank? clip-link))

(defn validate-link-input
  [link]
  (let [clip-id-pos (.indexOf link "/clip/")]
    (if (not= clip-id-pos -1)
      (subs link (+ clip-id-pos 6)) ;; 5 = length("/clip/")
      "")))

(defn is-valid-link
  [link]
  (if (not (string/blank? (validate-link-input link)))
    true
    false))