(ns state
  (:require [reagent.core :refer [atom]]))

(defonce app-state (atom {:twitch-link "Twitch link here"
                          :twitch-clips '[]}))