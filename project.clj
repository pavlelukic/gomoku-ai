(defproject gomoku-ai "0.1.0-SNAPSHOT"
  :description "A console-based Gomoku game in Clojure."
  :url "https://github.com/pavlelukic/gomoku-ai"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :main ^:skip-aot gomoku-ai.core
  :plugins [[lein-midje "3.2.2"]]
  :target-path "target/%s"
  :profiles {:dev     {:dependencies [[midje "1.10.9"]]}
             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})