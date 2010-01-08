(ns org.clojuremvc
  (:use org.clojuremvc.string-util)
  (:import
    [org.springframework.web.servlet ModelAndView]
    [org.springframework.web.servlet.mvc AbstractController]))

(defmacro defcontroller [classname & body]
    (let [this (gensym "this")
        shortname (strip (str classname) #(inc (.lastIndexOf % ".")))]
    `(do
       (defn ~(symbol (str "-" shortname "-postinit")) [~this]
         ~@(for [[url handler] (apply hash-map body)]
             `(.put ~this ~url
                 (proxy [AbstractController] []
                   (handleRequestInternal [request# response#]
                     (let [[view# model#] ~handler]
                       (ModelAndView. (tostr view#) (keys-to-strings model#))))))))
       (gen-class
         :name ~classname
         :extends java.util.HashMap
         :post-init ~(str shortname "-postinit")
         ))))

(defmacro defmapper [classname & body]
  (let [this (gensym "this")
        shortname (strip (str classname) #(inc (.lastIndexOf % ".")))]
    `(do
       (defn ~(symbol (str "-" shortname "-postinit")) [~this]
         ~@(for [controller body]
             `(doseq [[url# handler#] (.entrySet (new ~controller))]
                (.registerHandler ~this url# handler#))))
       (gen-class
         :name ~classname
         :extends
         org.springframework.web.servlet.handler.AbstractUrlHandlerMapping
         :post-init ~(str shortname "-postinit")
         ))))