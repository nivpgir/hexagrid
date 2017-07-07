(ns hexagrid.core.desktop-launcher
  (:require [hexagrid.core :refer :all])
  (:import [com.badlogic.gdx.backends.lwjgl LwjglApplication]
           [org.lwjgl.input Keyboard])
  (:gen-class))

(defn -main
  []
  (LwjglApplication. hexagrid-game "hexagrid" 800 600)
  (Keyboard/enableRepeatEvents true))
