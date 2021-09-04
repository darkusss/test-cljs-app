(ns view.main
  (:require [state :refer [app-state]]
            [events.events :refer [input-change add-link-click add-link-on-enter]]
            [view.header :refer [header]]
            [view.footer :refer [footer]]))

(defn description
  [desc]
  [:div.description__container
   [:p.description desc]])

(defn twitch-link-input
  [value placeholder]
  [:input.addLink__container {:on-change #(input-change %)
                              :on-key-press #(add-link-on-enter % (:twitch-link @app-state))
                              :placeholder placeholder
                              :value value}])

(defn add-link-button
  [btn-name]
  [:button.btn {:on-click #(add-link-click (:twitch-link @app-state))} btn-name])

(defn add-link
  [twitch-link]
  [:li {:key twitch-link} twitch-link])

(defn list-links
  []
  [:ul (if-let [links (map add-link (:twitch-clips @app-state))]
         links
         [:li "No links added yet"])])

(defn main
  []
  [:main
   [description "Add your clip link right below"]
   [twitch-link-input (:twitch-link @app-state) "Twitch clip link"]
   [add-link-button "Add link"]
   [list-links]])

(defn app
  []
  [:div.app__container
   [header]
   [main]
   [footer]])