(ns hexagrid.core
  (:require [play-clj.core :refer :all]
            [play-clj.ui :refer :all]
            [play-clj.g2d :refer :all]))

(defscreen main-screen
  :on-show
  (fn [screen entities]
    (update! screen :renderer (stage))
    ;; (shape :filled
    ;;        :set-color (color :green)
    ;;        :rect 0 0 10 30)
    (let [rect-size 50
          header (label "Hello Universe!" (color :white))
          shape (shape :filled
                       :set-color (color :green)
                       :rect 0 0 rect-size rect-size
                       :set-color (color :red)
                       :rect 0 0 (- rect-size) (- rect-size)
                       :set-color (color :blue)
                       :rect 0 0  (- rect-size) rect-size
                       :set-color (color :yellow)
                       :rect 0 0 rect-size (- rect-size))]
      (list (assoc header :x 600 :y 800)
            (assoc shape :x 600 :y 400 :xvel 1 :yvel 1 :size rect-size))))


  :on-render
  (fn [screen entities]
    (clear!)
    ;; (println entities)
    (->> entities
         (map (fn [ent] (if (shape? ent)
                         ;; (when (= (+ (x ent) (:size ent)) (size)))
                         (assoc ent
                                :x (+ (x ent) (:xvel ent))
                                :y (+ (y ent) (:yvel ent))))
                ent)))
    (render! screen))
  )


(defscreen blank-screen
  :on-render
  (fn [screen entities]
    (clear!)))


(defgame hexagrid-game
  :on-create
  (fn [this]
    (set-screen! this main-screen)))

(set-screen-wrapper! (fn [screen screen-fn]
                       (try (screen-fn)
                            (catch Exception e
                              (.printStackTrace e)
                              (set-screen! hexagrid-game blank-screen)))))



(defn println-wrapper [more]
  (println more)
  more
  )

(defn get-dirs [angle]
  (println "direction" angle)
  ((juxt (fn [rad] (Math/cos rad)) (fn [rad] (Math/sin rad)) )  angle))


(defn create-hexagon [x y size & {:keys [angle-offset flat?] :or {angle-offset 0}}]
  (if flat? (def angle-offset 0))
  (loop [i 0 coords []]
    (println "coords:" coords "i:" i)
    (if (= i 6) coords
        (recur (inc i) (concat coords (->> i
                                           (* 60)
                                           (+ angle-offset)
                                           (Math/toRadians)
                                           ((fn  [ang]
                                              ((juxt (fn [rad] (Math/cos rad))
                                                     (fn [rad] (Math/sin rad))) ang)))
                                           (map (fn [dir] (* size dir)))))))))
;; (let [cur-angle (* angle-offset i)
;;       x-offset (* (Math/cos (Math/toRadians cur-angle)) size)
;;       y-offset (* (Math/sin (Math/toRadians cur-angle)) size)
;;       new-coord (list (+ x x-offset) (+ y y-offset))]
;;   (println coords)

