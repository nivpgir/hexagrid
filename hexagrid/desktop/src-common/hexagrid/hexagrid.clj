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
