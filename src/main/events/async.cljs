(ns events.async
  (:require [ajax.core :refer [GET json-response-format]]
            [state :refer [add-clip!]]))

(goog-define TWITCH_AUTHORIZATION_TOKEN "")
(goog-define TWITCH_CLIENT_ID "")

(defn handler
  [response]
  (add-clip! (first (:data response))))

(defn error-handler
  [error]
  (.log js/console (str error)))

(defn get-twitch-clip [clipId]
  (GET (str "https://api.twitch.tv/helix/clips?id=" clipId) {:handler handler
                                                             :error-handler error-handler
                                                             :response-format (json-response-format {:keywords? true})
                                                             :headers {:Authorization TWITCH_AUTHORIZATION_TOKEN
                                                                       :Client-Id TWITCH_CLIENT_ID}}))