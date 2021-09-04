(ns helpers
  (:require [state :refer [app-state]]
            [clojure.string :as string]))

(defn link-exists
  [clip-link]
  (some #(= clip-link %) (:twitch-clips @app-state)))

(defn is-empty-link
  [clip-link]
  (string/blank? clip-link))