
(ns org.clojuremvc.param-util
  (:use org.clojuremvc.list-util
    org.clojuremvc.collect-map))

(defn param-retrieval
  "Returns the phrase for retrieving the value of a controller parameter by
  name. keyword specifies the type of parameter (:request or :session).
  requestvar defines the name of the variable containing the request object. If
  the type of parameter does not exist, this method throws an exception."
  [keyword name requestvar]
  (condp = keyword
    :request `(first (.. ~requestvar (getParameterMap) (get ~(str name))))
    :session `(.. ~requestvar (getSession) (getAttribute ~(str name)))
    (throw (Exception.))))

(defmacro with-controller-params
  "Binds controller parameters (request parameters, session attributes) to the
  specified variable names, and runs the body.
  For example:
  (with-controller-params req [:request a b :session c d] (println a b c d))
  prints the request parameters called \"a\" and \"b\" and the session
  attributes called \"c\" and \"d\". The parameters are read from the
  HttpServletRequest object represented by req."
  [request params & body]
  `(let
     ~(flatten-1                                                             ; this changes ([[a b][1 2]][[c d][3 4]]) into [[a b] [1 2] [c d] [3 4]]
        (for [[keyword & params-of-type] (partition-with keyword? params)]   ; loop over subsequences like [:request a b c]
         [(vec params-of-type)                                               ; vec of the variables to be assigned to
          (vec (map #(param-retrieval keyword % request) params-of-type))])) ; values of the variables to be assigned to
     ~@body))
