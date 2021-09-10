(ns view.main
  (:require [state :refer [app-state]]
            [events.events :refer [input-change add-link-on-enter]]
            [view.header :refer [header]]
            [view.footer :refer [footer]]
            [events.async :refer [run-something]]))

(defn description
  [desc]
  [:div.description__container
   [:p.description desc]])

(defn twitch-link-input
  [value placeholder]
  [:input.addLink__container {:on-change #(input-change %)
                              :on-key-up #(run-something)
                              :on-key-press #(add-link-on-enter % (:twitch-link @app-state))
                              :placeholder placeholder
                              :value value}])

(defn button
  [btn-name handle-click]
  [:button.btn {:on-click handle-click} btn-name])

(defn add-link
  [twitch-link]
  [:li.list-link {:key twitch-link} twitch-link])

(defn list-links
  []
  [:ul.links-list (if-let [links (seq (map add-link (:twitch-clips @app-state)))]
                    links
                    [:li "No links added yet"])])

(defn main
  []
  [:main.main__container
   [description "Add your clip link right below"]
   [twitch-link-input (:twitch-link @app-state) "Twitch clip link"]
   [list-links]
   [button "Generate video"]])

(defn app
  []
  [:div.app__container
   [header]
   [main]
   [footer]])