(ns events.async
  (:require [cljs.core.async :refer [chan close! put! go <!]]
            [ajax.core :refer [GET]]))

(defn <<< [f & args]
  (let [c (chan)]
    (apply f (concat args [(fn [x]
                             (if (nil? x)
                               (close! c)
                               (put! c x)))]))
    c))

(goog-define TWITCH_AUTHORIZATION_TOKEN "")


(defn handler
  [response]
  (.log js/console (str response)))

(defn error-handler
  [error]
  (.log js/console (str error)))

(defn get-twitch-login []
  (GET "https://api.twitch.tv/helix/users?login=twitchdev" {:handler handler
                                                            :error-handler error-handler
                                                            :headers {:Authorization TWITCH_AUTHORIZATION_TOKEN
                                                                      :Client-Id "f29jj1jpfk8dc56xsxk7ask4nnlc57"}}))

(defn run-something
  []
  (go
    (<! (<<< get-twitch-login))))