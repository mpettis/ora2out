(ns ora2out.core
  (require [clojure.java.jdbc :as j]
           [clojure.data.csv :as csv]
           [clojure.java.io :as io]
           [clojure.pprint :as pp]
           [clojure.tools.cli :refer [parse-opts] :as c])
  (:gen-class))
;(require '[clojure.java.jdbc :as j] '[clojure.data.csv :as csv] '[clojure.java.io :as io])

; https://github.com/clojure/java.jdbc
; http://clojure-doc.org/articles/ecosystem/java_jdbc/home.html
; https://github.com/clojure/data.csv
; http://clojure-doc.org/articles/ecosystem/java_jdbc/using_sql.html


(defn -main
  "Take a query and database connection string info and return query as CSV output"
  [& args]
  ;(println "Hello, World!")
  ; http://stackoverflow.com/questions/14322839/clojure-string-format-s-a-fails-with-classcastexception
  ;(println  (format "%s" "a word"))

  ; parse command line options
  ; http://blog.cymen.org/2012/03/09/clojure-using-clojuretools-cli-to-parse-command-line-arguments/
  ; https://github.com/clojure-cookbook/clojure-cookbook/blob/master/03_general-computing/3-07_parse-command-line-arguments.asciidoc
  (let [[options args banner]
          (c/cli args
            ["-h" "--help" "Print this help" :default false :flag true]
            ["-qtext" "Query, as single string" :default "select * from db_config_param"]
            ["-host" "Database host" :default "tcp-science1.quantumretail.com"]
            ["-sid" "Database SID" :default "quantum"]
            ["-port" "Database port to listen on" :default "1521"]
            ["-user" "Database user" :default "quantum"]
            ["-password" "Database password" :default "Q2010"])]
      (def argopts options)
      (when (:help argopts) (println banner) (System/exit 0)))

    ;(println "port:" (:port argopts))

  ; Database connection definition
  (def db {:classname "oracle.jdbc.OracleDriver"
             :subprotocol "oracle"
             ;:subname "thin:@tcp-science1.quantumretail.com:1521:quantum" 
             :subname (format "thin:@%s:%s:%s" (:host argopts) (:port argopts) (:sid argopts)) 
             :user (:user argopts)
             :password (:password argopts)})
  ;(pp/pprint db)

  ;(println (j/query db ["select * from qr_system_config"] :row-fn :current_run_cycle))
  ;(j/query db ["select * from qr_calendar_454 where week_begin_dt = '02-AUG-15'"] :row-fn #'println)
  ;(j/query db ["select * from qr_calendar_454 where week_begin_dt = '02-AUG-15'"] :row-fn println)

  ;(def rs (j/query db ["select * from qr_calendar_454 where week_begin_dt = '02-AUG-15'"]))
  ;(def rs (j/query db ["select week_begin_dt, year_nbr from qr_calendar_454 where week_begin_dt = '02-AUG-15'"]))

  ; http://stackoverflow.com/questions/18572117/convert-collection-of-hash-maps-to-a-csv-file
  ;(defn write-csv [path row-data]
    ;(let [columns [:week_begin_dt :year_nbr]
          ;headers (map name columns)
          ;rows (mapv #(mapv % columns) row-data)]
      ;(with-open [file (io/writer path)]
        ;(csv/write-csv file (cons headers rows)))))

  ;(defn write-csv [path row-data]
    ;(let [columns (keys (first row-data))
          ;headers (map name columns)
          ;rows (mapv #(mapv % columns) row-data)]
      ;(with-open [file (io/writer path)]
        ;(csv/write-csv file (cons headers rows)))))
  ;(write-csv "/tmp/results.csv" rs)


  ;(def q ["select week_begin_dt, year_nbr from qr_calendar_454 where week_begin_dt = '02-AUG-15'"])

  ;(def q ["select * from db_config_param where name = 'SELECT.RC_POUTL'"])
  ;;(def rs (j/query db q))
  ;(def rs (j/query db q :as-arrays? true))
  ;;(println rs)


  ;(with-open [file (io/writer "/tmp/results.csv")]
      ;(csv/write-csv file rs))

      ; write to file
  ;(def q ["select * from db_config_param where name = 'SELECT.RC_POUTL'"])
  ;(def rs (j/query db q :as-arrays? true))
  ;;(println rs)
  ;(let [header (map name (first rs))]
    ;(with-open [file (io/writer "/tmp/results.csv")]
        ;(csv/write-csv file (cons header (rest rs)))))

    ;; Write to stdout
  ;(def q ["select * from db_config_param where name = 'SELECT.RC_POUTL'"])
  ;(def q ["select * from qr_graph"])
  ;(def q ["select * from db_config_param"])
  (def q [(:qtext argopts)])
  (def rs (j/query db q :as-arrays? true))
  (let [header (map name (first rs))]
    (csv/write-csv *out* (cons header (rest rs))))
  (flush)

    ;(println "End!")
    )

