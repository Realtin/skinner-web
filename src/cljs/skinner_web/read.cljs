(ns skinner-web.read
  (:require [clojure.string :as s]))
  
(defn read-command [str]
  (let [elems (s/split str #" ")
        head (keyword (first elems))
        tail (map js/parseInt (rest elems))
        ast (vec (cons head tail))]
    ast))
  
(defn read-fn
   "takes a string and turns it into AST"
   [str]
   (let [lines (s/split-lines str)
         commands (mapv read-command lines)]
     commands))