(ns leiningen.cljsasset.core
  (:require [clojure.java.io :as io]))

(def lein-cljsasset-version "0.2.0")

(defn read-project
  "Returns a vector of forms read from project.clj"
  [resource]
  (binding [*read-eval* false]
    (loop [pbr (java.io.PushbackReader. (io/reader resource))
           nxt (read pbr false :EOF)
           forms []]
      (if (= nxt :EOF)
        forms
        (recur pbr (read pbr false :EOF) (conj forms nxt))))))

(defn get-project [group-artifact]
  (when-let [resource (io/resource (str "META-INF/leiningen/" group-artifact "/project.clj"))]
    (apply hash-map
           (-> (filter #(= (first %) 'defproject) (read-project resource))
               first
               rest
               rest
               rest))))

(defn conj-assets [acc asset]
  (-> acc
      (update-in [:js] into (:js asset))
      (update-in [:css] into (:css asset))))

(defn get-assets [deps]
  (reduce conj-assets {:js [] :css []} (map (comp :cljsasset get-project) deps)))
