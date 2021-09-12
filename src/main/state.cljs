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

(defn update-show-clip [coll id]
  (map (fn [x]
         (when (= (:id x) id)
           (update-in x [:show-clip?] #(if (true? %) false true)))) coll))

(defn show-clip!
  [clip-id]
  (swap! app-state update :twitch-clips update-show-clip clip-id))

(defn add-clip!
  [clip]
  (swap! app-state update :twitch-clips conj (assoc clip :show-clip? false)))

(defn delete-clip!
  [clip-id]
  (swap! app-state update :twitch-clips remove-by-id clip-id))