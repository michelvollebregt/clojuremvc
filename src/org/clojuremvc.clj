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
         ~@(for [[url params & handler] body]
             `(.put ~this ~url
                 (proxy [AbstractController] []
                   (handleRequestInternal [request# response#]
                     (let [[view# model#] (do ~@handler)]
                       (ModelAndView. (tostr view#) (keys-to-strings model#))))))))
       (gen-class
         :name ~classname
         :extends java.util.HashMap
         :post-init ~(str shortname "-postinit")
         ))))

;;; kunnen we ook defcontroller een methode laten definieren die we annoteren
;;; met metadata, zodat defmapping deze kan terugvinden?
;;; vraag: kunnen we ALLE namespaceas doorzoeken? (hoe gaat dit in test-is?)

(defmacro defmapping [classname & body]
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