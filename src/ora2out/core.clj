(ns ora2out.core
  ;(:require [clojure.java.jdbc :as jdbc]) 
  ;(use clojure.java.jdbc)
  (:gen-class))

(require '[clojure.java.jdbc :as j] '[clojure.data.csv :as csv] '[clojure.java.io :as io])

(def db {:classname "oracle.jdbc.OracleDriver"
           :subprotocol "oracle"
           :subname "thin:@tcp-science1.quantumretail.com:1521:quantum" 
           :user "quantum"
           :password "Q2010"})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")

  ;(println (j/query db ["select * from qr_system_config"] :row-fn :current_run_cycle))
  ;(j/query db ["select * from qr_calendar_454 where week_begin_dt = '02-AUG-15'"] :row-fn #'println)
  ;(j/query db ["select * from qr_calendar_454 where week_begin_dt = '02-AUG-15'"] :row-fn println)


  ;(def rs (j/query db ["select * from qr_calendar_454 where week_begin_dt = '02-AUG-15'"]))
  (def rs (j/query db ["select week_begin_dt, year_nbr from qr_calendar_454 where week_begin_dt = '02-AUG-15'"]))

  ; http://stackoverflow.com/questions/18572117/convert-collection-of-hash-maps-to-a-csv-file
  ;(defn write-csv [path row-data]
    ;(let [columns [:week_begin_dt :year_nbr]
          ;headers (map name columns)
          ;rows (mapv #(mapv % columns) row-data)]
      ;(with-open [file (io/writer path)]
        ;(csv/write-csv file (cons headers rows)))))

  (defn write-csv [path row-data]
    (let [columns (keys (first row-data))
          headers (map name columns)
          rows (mapv #(mapv % columns) row-data)]
      (with-open [file (io/writer path)]
        (csv/write-csv file (cons headers rows)))))
  (write-csv "/tmp/results.csv" rs)

  (println "End!"))

