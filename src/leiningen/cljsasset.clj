(ns leiningen.cljsasset
  (:require [clojure.pprint :as pprint]
            [leiningen.core.eval :refer [eval-in-project]]
            [leiningen.cljsasset.core :refer [lein-cljsasset-version conj-assets]]
            [me.raynes.fs :as fs]
            [clojure.java.io :as io]))

(defn write-output [assets {:keys [dir file]}]
  (fs/mkdirs dir)
  (let [content (reduce (fn [acc res] (str acc (slurp (io/resource res)))) "" assets)]
    (spit (str dir "/" file) content)))

(def default-js {:dir "resources/public/js"
                 :file "assets.js"})
(def default-css {:dir "resources/public/css"
                  :file "assets.css"})

(defn write-assets [assets project]
  (let [{:keys [js css]} (conj-assets assets (:cljsasset project))]
    (when (seq js)
      (write-output js (get-in project [:cljsasset :js-output] default-js)))
    (when (seq css)
      (write-output css (get-in project [:cljsasset :css-output] default-css)))))

(defn cljsasset
  "Process assets and write to output files."
  [project & keys]
  (let [deps (map (comp str first) (:dependencies project))
        assets (eval-in-project
                (-> (update-in project [:dependencies] conj
                               ['lein-cljsasset lein-cljsasset-version])
                    (assoc :eval-in :leiningen))
                `(leiningen.cljsasset.core/get-assets (list ~@deps))
                '(require 'leiningen.cljsasset.core))]
    (write-assets assets project)))
