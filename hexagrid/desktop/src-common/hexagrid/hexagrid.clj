(ns hexagrid.hexagrid)



(defn hexa-corners [x y size & {:keys [angle-offset] :or {angle-offset 0}}]
  (loop [i 0 coords []]
    ;; (println "coords:" coords "i:" i)
    (if (= i 6) coords
        (recur (inc i) (into [] (concat coords (->> i
                                                    (* 60)
                                                    (+ angle-offset)
                                                    (Math/toRadians)
                                                    ((fn  [ang]
                                                       ((juxt (fn [rad] (Math/cos rad))
                                                              (fn [rad] (Math/sin rad))) ang)))
                                                    (map (fn [dir] (* size dir))))
                                        ))))))



(defn make-hexgrid []
  "will make you a hexagonal grid, parameters will be decided later"
  {})




(def cube-direction {:es [1 (- 1) 0]
                     :ne [1 0 (- 1)]
                     :nw [0 1 (- 1)]
                     :we [(- 1) 1 0]
                     :sw [(- 1) 0 1]
                     :se [0 (- 1) 1]})

;;(def axial-direction (reduce-kv (fn [m k v] (assoc m k (cube-to-axial v))) {} cube-direction))



(def axial-direction {:es [1 0]
                      :ne [1 (- 1)]
                      :nw [0 (- 1)]
                      :we [(- 1) 0]
                      :sw [(- 1) 1]
                      :se [0 1]})

;; TODO: maybe add diagonal directions?

(defn add-coords [& extra]
  (apply map + extra))

(defn cube-to-axial [& coords] (apply map (fn [x] x) coords))

(defn axial-to-cube [x z] [x (- (+ x z)) x])

(defn get-neighbors-cube [x y z] '())


;; (defn coord-to-hex)
