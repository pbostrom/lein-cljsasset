(ns leiningen.cljsasset
  (:require [clojure.pprint :as pprint]
            [leiningen.core.eval :refer [eval-in-project]]
            [leiningen.cljsasset.core :refer [lein-cljsasset-version]]
            [me.raynes.fs :as fs]
            [clojure.java.io :as io]))

(defn write-output [assets {:keys [dir file]}]
  (fs/mkdirs dir)
  (let [content (reduce (fn [acc res] (str acc (slurp (io/resource res)))) "" assets)]
    (spit (str dir "/" file) content)))

(defn write-assets [{:keys [js css]} project]
  (write-output js (get-in project [:cljsasset :js-output]))
  (write-output css (get-in project [:cljsasset :css-output])))

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
