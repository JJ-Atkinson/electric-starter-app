(ns app.source-parser
  #?(:clj
     (:require
      [clojure.java.io :as io]
      [clojure.string :as str]
      [contrib.electric-codemirror :as ecm])))

(def project-meta #?(:clj (ecm/read-edn (slurp "file-information.edn"))))

(defn get-definition
  [{:keys [row end-row filename] :as kondo-analysis}]
  #?(:clj
     (with-open [reader (io/reader filename)]
       (-> reader
         (line-seq)
         (vec)
         (subvec (dec row) end-row)
         (->> (str/join "\n"))))))

(def vars-by-name
  #?(:clj
     (->> project-meta
       :analysis
       :var-definitions
       (group-by (fn [{:keys [ns name]}] (str ns \/ name)))
       (map (fn [[k [v]]] [k v]))
       (into {}))))

(defn search-vars
  [str]
  #?(:clj
     (take 10
       (let [names (keys vars-by-name)]
         (if (str/blank? str)
           names
           (filter #(str/includes? % str) names))))))

(comment
  (println
    (get-definition
      (get vars-by-name "app.todo-list/project-meta"))))
