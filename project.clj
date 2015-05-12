(defproject ora2out "0.1.0-SNAPSHOT"
  :description "Stand-alone jar that allows you to issue queries against an Oracle database and dump results to stdout as CSV, XML, JSON, possibly more."
  :url "https://github.com/mpettis"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/data.csv "0.1.2"]
                 [org.clojure/java.jdbc "0.3.6"]
                 [com.oracle/ojdbc6 "11.2.0"]
                 [org.apache.commons/commons-lang3 "3.0"]]
  ;;:main ^:skip-aot ora2out.core
  :main ora2out.core
  :aot [ora2out.core]
  :target-path "target/%s"
  :plugins [[cider/cider-nrepl "0.8.2"]]
  :profiles {:uberjar {:aot :all}})

