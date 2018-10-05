(ns sublimerepl.core)

(defn pprint-middleware 
  "pprint in Clojure SublimeREPL.
  
  In your project.clj add it to :repl-option:
  
  (defproject sample \"0.1.0\"
    :dependencies [[org.clojure/clojure \"1.4.0\"]
                   [sublimerepl \"X.X.X\"]]
    :repl-options {:nrepl-middleware [sublimerepl/pprint-middleware]})"
  [handler]
  (fn [{:keys [code op] :as msg}]
    (if (and (= op "eval") (not (empty? code)))
      (->> #(str "(clojure.pprint/pprint " % ")")
           (update-in msg [:code])
           handler)
      (handler msg))))