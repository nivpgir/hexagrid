(ns hexagrid.core
  (:require [play-clj.core :refer :all]
            [play-clj.ui :refer :all]
            [play-clj.g2d :refer :all]
            [hexagrid.hexagrid :as h]))


(declare hexagrid-game blank-screen)
(set-screen-wrapper! (fn [screen screen-fn]
                       (try (screen-fn)
                            (catch Exception e
                              (.printStackTrace e)
                              (set-screen! hexagrid-game blank-screen)))))



(defn println-wrapper [more]
  (println more)
  more
  )


(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    ;; (shape :filled
    ;;        :set-color (color :green)
    ;;        :rect 0 0 10 30)
    (let [rect-size 50
          header (label "Hello Universe!" (color :white))

          rect (shape :filled
                      :set-color (color :green)
                      :rect 0 0 rect-size rect-size
                      :set-color (color :red)
                      :rect 0 0 (- rect-size) (- rect-size)
                      :set-color (color :blue)
                      :rect 0 0  (- rect-size) rect-size
                      :set-color (color :yellow)
                      :rect 0 0 rect-size (- rect-size))

          hex (shape :line
                     :set-color (color :white)
                     :polygon (float-array (h/hexa-corners 0 0 100 :angle-offset 45)))
          ]
      (list (assoc header :x 600 :y 800)
            (assoc rect :x 600 :y 600 :xvel 1 :yvel 1 :size rect-size)
            (assoc hex :x 600 :y 200 :xvel 0 :yvel 0 :size 10)
            )))


  :on-render
  (fn [screen entities]
    (clear!)
    ;; (println entities)
    (->> entities
         ;; (map (
         ;;       fn [ent]
         ;;       (if (not (shape? ent))
         ;;         ent
         ;;         (assoc ent
         ;;                :x (+ (x ent) (:xvel ent))
         ;;                :y (+ (y ent) (:yvel ent))))) )
         ;; (println-wrapper)
         (render! screen))
    ))


(defscreen blank-screen
  :on-render
  (fn [screen entities]
    (clear!)))



(defgame hexagrid-game
  :on-create
  (fn [this]
    (set-screen! this main-screen)))
