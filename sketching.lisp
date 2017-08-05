;; (ql:quickload :swank)
(ql:quickload :sketch)
(defpackage :tutorial (:use :cl :sketch))
(in-package :tutorial)

(defun translate-to-segment (crd src-start src-len target-start target-len)
  ;; (format t "~%~% ~A ~A ~A ~A ~A" crd src-start src-len target-start target-len)
  (+ target-start (* target-len (/ (- crd src-start) src-len))))


(defun calc-fun-graph (f points)
  (mapcar (lambda (x) (list x (funcall f x))) points))


(defun plot-fun (f seg-start seg-end npoints)
  (calc-fun-graph f (loop for i from seg-start to seg-end
                                  by (/ (- seg-end seg-start) npoints)
                                  collect i)))

(defun gen-poly (&rest rest)
  (lambda (x) (apply #'+  (loop for a in rest
                                for i = 0 then (1+ i)
                                collect (* a (expt x i))))))
(defsketch integral ((width 800) (height 600)
                     (start (- 3.18))
                     (end 3.18)
                     (len (- end start))
                     (nsteps width)
                     (nrects 50)
                     (rect-w (floor width nrects))
                     (f (gen-poly 0 (- 36) 0 49 0 (- 14) 0 1))
                     (points (plot-fun f start end width)))

  (loop for pl = points then (rest pl)
        for maxy = (apply #'max (mapcar #'second points))
        for miny = (apply #'min (mapcar #'second points))

        for x1 = (translate-to-segment (first (first pl)) start len 0 width)
        for y1 = (- height (translate-to-segment (second (first pl))
                                                 miny (- maxy miny)
                                                 0 height))

        for x2 = (translate-to-segment (first (second pl)) start len 0 width)
        for y2 = (- height (translate-to-segment (second (second pl))
                                                 miny (- maxy miny)
                                                 0 height))
        while (rest (rest pl)) do
          (when (= (rem x1 rect-w) 0)
            (rect (- x1 (/ rect-w 2)) y1 rect-w (- height y1)))
          (line x1 y1 x2 y2)))

(make-instance 'integral)
