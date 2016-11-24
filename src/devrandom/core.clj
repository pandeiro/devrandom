(ns devrandom.core
  (:gen-class)
  (:require
   [clojure.java.shell   :as shell]
   [clojure.string       :as string]
   [pandect.algo.sha1 :refer [sha1]]))


;;
;; Util
;;
(defn- processes []
  (:out (shell/sh "ps" "aux")))

(defn- hash->hexvals [hash]
  (map string/join (partition 2 hash)))


;;
;; State
;;
(defonce pool
  (atom []))


;;
;; Main
;;
(defn -main
  "Output random chars using the process list and timestamp as seed"
  [& args]
  (do
    (future
      (while true
        (let [hash (sha1 (str (processes) (System/currentTimeMillis)))
              vals (hash->hexvals hash)]
          (doseq [val vals]
            (swap! pool conj (Integer/parseInt val 16))))
        (Thread/sleep 1000)))
    (while true
      (if (seq @pool)
        (do
          (print (char (first @pool)))
          (flush)
          (swap! pool subvec 1))
        (Thread/sleep 1000)))))
