
(ns org.clojuremvc.example
  (:use org.clojuremvc))

(defcontroller org.clojuremvc.example.Controller
  "/index.html"
  [:index {:message "Welcome to Clojure MVC."}])

(defmapping org.clojuremvc.example.Mapping
  org.clojuremvc.example.Controller)