(ns events.async
  (:require [ajax.core :refer [GET json-response-format]]
            [state :refer [app-state]]))

(goog-define TWITCH_AUTHORIZATION_TOKEN "")


(defn handler
  [response]
  (swap! app-state update :twitch-clips conj (first (:data response))))

(defn error-handler
  [error]
  (.log js/console (str error)))

(defn get-twitch-clip [clipId]
  (GET (str "https://api.twitch.tv/helix/clips?id=" clipId) {:handler handler
                                                             :error-handler error-handler
                                                             :response-format (json-response-format {:keywords? true})
                                                             :headers {:Authorization TWITCH_AUTHORIZATION_TOKEN
                                                                       :Client-Id "f29jj1jpfk8dc56xsxk7ask4nnlc57"}}))