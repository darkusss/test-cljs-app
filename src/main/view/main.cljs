(ns view.main
  (:require [state :refer [app-state delete-clip! show-clip!]]
            [events.events :refer [input-change add-link-on-enter]]
            [view.header :refer [header]]
            [view.footer :refer [footer]]))

(defn description
  [desc]
  [:div.description__container
   [:p.description desc]])

(defn twitch-clip-link-input
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
            :width "500px"
            :allowFullScreen true}])

(defn clip-info-buttons
  [clip-id]
  [:div.clip-info-buttons
   [button "➜" (fn [] (show-clip! clip-id))]
   [button "×" (fn [] (delete-clip! clip-id))]])

(defn add-clip-info
  [{id :id title :title show-clip :show-clip?}]
  [:li.list-clip {:key id} [:div
                            [:div.clip-info
                             [:h4 title]
                             (clip-info-buttons id)]
                            (when show-clip (iframe-clip id))]])

(defn list-clips
  []
  [:ul.clips-list (if-let [clips (seq (map add-clip-info (:twitch-clips @app-state)))]
                    clips
                    [:li.no-clip [:strong "No clips added yet"]])])

(defn main
  []
  [:main.main__container
   [description "Add your clip link right below"]
   [twitch-clip-link-input (:twitch-link @app-state) "Twitch clip link"]
   [list-clips]
   [button "Generate video"]])

(defn app
  []
  [:div.app__container
   [header]
   [main]
   [footer]])