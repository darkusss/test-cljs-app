(ns view.main
  (:require [state :refer [app-state delete-clip! show-clip! input-change! clear-input-error!]]
            [events.events :refer [add-link-on-enter send-clips-to]]
            [view.header :refer [header]]
            [view.footer :refer [footer]]))

(defn description
  [desc]
  [:div.description__container
   [:p.description desc]])

(defn input-on-change
  [event]
  (input-change! event)
  (clear-input-error!)
  )

(defn twitch-clip-link-input
  [value placeholder]
  [:div [:<> [:input.addLink__container {:on-change #(input-on-change %)
                                         :on-key-press #(add-link-on-enter % (:twitch-link @app-state))
                                         :placeholder placeholder
                                         :value value}]
         [:span.input-error (when (:input-error? @app-state) "The input link was incorrect or already added in the list below")]]])

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
  [clip-id show-clip]
  [:div.clip-info-buttons
   [button (if show-clip "Show less" "Show more") (fn [] (show-clip! clip-id))]
   [button "Remove" (fn [] (delete-clip! clip-id))]])

(defn clip-info
  [{id :id title :title show-clip :show-clip?}]
  [:li.list-clip {:key id} [:div
                            [:div.clip-info
                             [:h4 title]
                             (clip-info-buttons id show-clip)]
                            (when show-clip (iframe-clip id))]])

(defn list-of-clips
  [clips]
  [:ul.clips-list
   (if (empty? clips)
     [:li.no-clip [:strong "Oh no... I am still empty"]]
     (for [clip clips]
       (clip-info clip)))])

(defn main
  []
  [:main.main__container
   [description "Add your twitch clip right below"]
   [twitch-clip-link-input (:twitch-link @app-state) "Twitch clip link"]
   [list-of-clips (:twitch-clips @app-state)]
   [button "Generate & download video" (fn [] (send-clips-to (:twitch-clips @app-state))) ]])

(defn app
  []
  [:div.app__container
   [header]
   [main]
   [footer]])