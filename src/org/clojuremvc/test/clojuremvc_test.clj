(ns org.clojuremvc.test.clojuremvc-test
  (:gen-class)
  (:use clojure.contrib.test-is
    org.clojuremvc
    org.clojuremvc.test.params-test
    org.clojuremvc.test.collect-map-test
    org.clojuremvc.test.list-util-test
    org.clojuremvc.test.string-util-test
    org.clojuremvc.test.param-util-test)
   (:import
    [org.springframework.web.servlet ModelAndView]
    [org.springframework.web.servlet.mvc Controller]
    [org.springframework.mock.web MockHttpServletRequest MockHttpServletResponse]))

(defcontroller org.clojuremvc.test.TestController
  ("/index.html" []
    [:index {:message "Welcome to Clojure MVC."}])
  ("/sideeffects.html" []
    (println "side effect")
    [:index {:remark "This controller had a side effect."}])
  ("/params.html" [:request userinput]
    [:paramview {:model userinput}]))


(let [testController (org.clojuremvc.test.TestController.)]

  (deftest defcontroller-test
    (let [action (.get testController "/index.html")
          mav (.handleRequest action
                (MockHttpServletRequest. "POST" "/index.html")
                (MockHttpServletResponse.))]
    (is (instance? ModelAndView mav))
    (is (= "index" (.getViewName mav)))
    (is (= {"message" "Welcome to Clojure MVC."} (.getModel mav)))))

  (deftest side-effect-test
    (let [action (.get testController "/sideeffects.html")
          mav (.handleRequest action
                (MockHttpServletRequest. "GET" "/sideeffects.html")
                (MockHttpServletResponse.))]
      (is (instance? ModelAndView mav))
      (is (= "index" (.getViewName mav)))
      (is (= {"remark" "This controller had a side effect."} (.getModel mav)))))

  (deftest param-test
    (let [action (.get testController "/params.html")
          reqst (MockHttpServletRequest. "GET" "/params.html")]
      (.addParameter reqst "userinput" "hello there")
      (let [mav (.handleRequest action reqst (MockHttpServletResponse.))]
        (is (instance? ModelAndView mav))
        (is (= "paramview" (.getViewName mav)))
        (is (= {"model" "hello there"} (.getModel mav)))))))


(defn -main []
  (run-all-tests #"org.clojuremvc.test.*"))