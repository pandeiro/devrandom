(set-env!
 :source-paths   #{"src"}
 :resource-paths #{"src"}
 :dependencies   '[[org.clojure/clojure "1.6.0"]
                   [pandect "0.6.1"]])

(deftask package []
  (comp
   (aot :namespace '#{devrandom.core})
   (pom :project 'devrandom
        :version "1.0.0")
   (uber)
   (jar :main 'devrandom.core)
   (sift :include #{#"project.jar"})
   (target)))
