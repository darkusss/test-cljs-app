(ns events.async
  (:require [ajax.core :refer [GET POST json-response-format json-request-format]]
            [state :refer [add-clip!]]))

(goog-define TWITCH_AUTHORIZATION_TOKEN "")
(goog-define TWITCH_CLIENT_ID "")

(defn handler
  [response]
  (add-clip! (first (:data response))))

(defn clips-handler
  [response]
  (.log js/console response))

(defn error-handler
  [error]
  (.log js/console (str error)))

(defn send-clips [clips]
  (POST "http://localhost:7789/api/clips" {:handler clips-handler
                                       :error-handler error-handler
                                       :body clips
                                       :format json-request-format
                                       :response-format (json-response-format {:keywords? true})}))

(defn add-twitch-clip [clipId]
  (GET (str "https://api.twitch.tv/helix/clips?id=" clipId) {:handler handler
                                                             :error-handler error-handler
                                                             :response-format (json-response-format {:keywords? true})
                                                             :headers {:Authorization TWITCH_AUTHORIZATION_TOKEN
                                                                       :Client-Id TWITCH_CLIENT_ID}}))