(ns spice-coop-lids.core
  (:require [scad-clj.scad :as scad]
            [scad-clj.model :as m])
  (:gen-class))

(defn includes []
  [(m/include "Thread_Library.scad")
   (m/use "write.scad")])

(scad/write-scad [:cylinder {:fn 3}])

(def cylinder-radius (/ 45.5 2))
(def cylinder-height 13)
(def cap-pitch 4.25)
(def lid-side 3)
(def top-thickness 3.6)

(defn trapezoidThreadNegativeSpace [length pitch pitchRadius threadHeightToPitch profileRatio threadAngle threadQuality]
  (m/call-module :trapezoidThreadNegativeSpace 
    {:length length 
     :pitch pitch 
     :pitchRadius pitchRadius 
     :threadHeightToPitch threadHeightToPitch
     :profileRatio profileRatio
     :threadAngle threadAngle
     :RH true
     :countersunk 0
     :clearance 0.1
     :backlash 0.1
     :stepsPerTurn threadQuality}))

(defn cylinder-text [text translation radius height text-height text-depth]
  (m/call-module :writecylinder text translation radius height {:h text-height :t text-depth}))

(def threads (trapezoidThreadNegativeSpace cylinder-height cap-pitch cylinder-radius (/ 1.524 6.35) 1.66 30 90))

(defn wrapped-text-with-shift [text shift]
  (m/rotate (map m/deg->rad [0, 180, shift])
    (cylinder-text (str \" text \") [0 0 -13] (+ cylinder-radius lid-side) cylinder-height (- cylinder-height 4) 1.5)))

(defn wrapped-text [text]
  (m/union
    (wrapped-text-with-shift text 0)
    (wrapped-text-with-shift text 180)))

(defn lid []
  (m/union
    (m/difference
      (m/cylinder (+ cylinder-radius lid-side) cylinder-height)
      threads
      (wrapped-text "SPICECOOP"))
    (m/difference
      (m/cylinder (+ cylinder-radius lid-side) top-thickness)
      (wrapped-text "SPICECOOP"))))


(defn build []
  (spit
    "lid.scad"
    (scad/write-scad
      (m/with-fn 120
        (m/with-center false
          [(includes) (lid)])))))


(defn -main
  "Builds spice-coop lids"
  [& args]
  (build))


