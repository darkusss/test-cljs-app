(ns view.main
  (:require [state :refer [app-state]]
            [events.events :refer [input-change add-link-on-enter]]
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

(defn button
  [btn-name handle-click]
  [:button.btn {:on-click handle-click} btn-name])

(defn iframe-clip
  [clipId]
  [:iframe {:src (str "https://clips.twitch.tv/embed?clip=" clipId "&parent=localhost")
            :height "350px"
            :width "400px"
            :allowFullScreen true}]
  )

(defn add-clip-info
  [{id :id title :title}]
  [:li.list-clip {:key id} title (iframe-clip id)])

(defn list-clips
  []
  [:ul.clips-list (if-let [clips (seq (map add-clip-info (:twitch-clips @app-state)))]
                    clips
                    [:li "No links added yet"])])

(defn main
  []
  [:main.main__container
   [description "Add your clip link right below"]
   [twitch-link-input (:twitch-link @app-state) "Twitch clip link"]
   [list-clips]
   [button "Generate video"]])

(defn app
  []
  [:div.app__container
   [header]
   [main]
   [footer]])