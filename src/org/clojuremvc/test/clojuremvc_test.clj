(ns org.clojuremvc.test.clojuremvc-test
  (:gen-class)
  (:use clojure.contrib.test-is
    org.clojuremvc
    org.clojuremvc.test.string-util-test)
   (:import
    [org.springframework.web.servlet ModelAndView]
    [org.springframework.web.servlet.mvc Controller]
    [org.springframework.mock.web MockHttpServletRequest MockHttpServletResponse]))

(defcontroller org.clojuremvc.test.TestController
  "/index.html"
  [:index {:message "Welcome to Clojure MVC."}])

(deftest defcontroller-test
  (let [testController (org.clojuremvc.test.TestController.)]
    (let [action (.get testController "/index.html")]
      (let [mav (.handleRequest action 
                  (MockHttpServletRequest. "POST" "/index.html")
                  (MockHttpServletResponse.))]
        (is (instance? ModelAndView mav))
        (is (= "index" (.getViewName mav)))
        (is (= {"message" "Welcome to Clojure MVC."} (.getModel mav)))))))

(defn -main []
  (run-all-tests #"org.clojuremvc.test.*"))