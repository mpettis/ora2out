(ns ora2out.core
  (require [clojure.java.jdbc :as j] [clojure.data.csv :as csv] [clojure.java.io :as io])
  (:gen-class))
;(require '[clojure.java.jdbc :as j] '[clojure.data.csv :as csv] '[clojure.java.io :as io])

; https://github.com/clojure/java.jdbc
; http://clojure-doc.org/articles/ecosystem/java_jdbc/home.html
; https://github.com/clojure/data.csv
; http://clojure-doc.org/articles/ecosystem/java_jdbc/using_sql.html


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  ; http://stackoverflow.com/questions/14322839/clojure-string-format-s-a-fails-with-classcastexception
  (println  (format "%s" "a word"))

  ; Database connection definition
  (def db {:classname "oracle.jdbc.OracleDriver"
             :subprotocol "oracle"
             :subname "thin:@tcp-science1.quantumretail.com:1521:quantum" 
             :user "quantum"
             :password "Q2010"})

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
  (def q ["select * from db_config_param where name = 'SELECT.RC_POUTL'"])
  ;(def rs (j/query db q))
  (def rs (j/query db q :as-arrays? true))
  (println rs)


  ;(with-open [file (io/writer "/tmp/results.csv")]
      ;(csv/write-csv file rs))
    (let [header (map name (first rs))]
      (with-open [file (io/writer "/tmp/results.csv")]
          (csv/write-csv file (cons header (rest rs)))))

    (println "End!"))

