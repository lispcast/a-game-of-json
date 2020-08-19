(ns a-game-of-json.core
  (:require [cheshire.core :as json]))

(defonce books-raw (slurp "https://raw.githubusercontent.com/joakimskoog/AnApiOfIceAndFire/master/data/books.json"))

(defonce characters-raw (slurp "https://raw.githubusercontent.com/joakimskoog/AnApiOfIceAndFire/master/data/characters.json"))

(defonce houses-raw (slurp "https://raw.githubusercontent.com/joakimskoog/AnApiOfIceAndFire/master/data/houses.json"))

(defonce books-json (json/parse-string books-raw))

(defonce chars-json (json/parse-string characters-raw))

(defonce houses-json (json/parse-string houses-raw))

(defonce books-by-id (into {} (for [book books-json]
                                [(get book "Id") book])))

(defonce chars-by-id (into {} (for [char chars-json]
                                [(get char "Id") char])))

(defonce houses-by-id (into {} (for [houses houses-json]
                                 [(get houses "Id") houses])))

(defn children [char]
  (map #(get chars-by-id %) (get char "Children")))

(defn grandchildren [char]
  (mapcat children (children char)))

(defn print-children []
  (doseq [char chars-json]
    (let [children (children char)]
      (when (seq children)
        (println (get char "Name"))
        (doseq [child children]
          (println "  " (get child "Name")))
        (println "----")))))

(defn print-grandchildren []
  (doseq [char chars-json]
    (let [grandchildren (grandchildren char)]
      (when (seq grandchildren)
        (println (get char "Name"))
        (doseq [gchild grandchildren]
          (println "  " (get gchild "Name")))
        (println "----")))))
