(ns koans.15-destructuring
  (:require [koan-engine.core :refer :all]))

(def test-address
  {:street-address "123 Test Lane"
   :city "Testerville"
   :state "TX"})

(meditations
  "Destructuring is an arbiter: it breaks up arguments"
  (= ":bar:foo" ((fn [[a b]] (str b a))
         [:foo :bar]))

  "Whether in function definitions"
  (= (str "An Oxford comma list of apples, "
          "oranges, "
          "and pears.")
    ((fn [[a b c]] (let [y (str "and " c)
                         x (apply str (interpose ", " [a b y]))]
                     (str "An Oxford comma list of " x ".")))
      ["apples" "oranges" "pears"]))

  "Or in let expressions"
  (= "Rich Hickey aka The Clojurer aka Go Time aka Lambda Guru"
    (let [[first-name last-name & aliases]
          (list "Rich" "Hickey" "The Clojurer" "Go Time" "Lambda Guru")]
      (let [sep " aka "]
        (str first-name " " last-name sep (apply str (interpose sep aliases))))))

  "You can regain the full argument if you like arguing"
  (= {:original-parts ["Stephen" "Hawking"] :named-parts {:first "Stephen" :last "Hawking"}}
     (let [[first-name last-name :as full-name] ["Stephen" "Hawking"]]
       (assoc {} :original-parts full-name
                 :named-parts (assoc {} :first first-name :last last-name))))

  "Break up maps by key"
  (= "123 Test Lane, Testerville, TX"
     (let [{street-address :street-address, city :city, state :state} test-address]
       (apply str (interpose ", " [street-address city state]))))

  "Or more succinctly"
  (= "123 Test Lane, Testerville, TX"
     (let [{:keys [street-address city state]} test-address]
       (apply str (interpose ", " [street-address city state]))))

  "All together now!"
  (= "Test Testerson, 123 Test Lane, Testerville, TX"
     ((fn [[test testerson] address]
         (let [name (str test " " testerson)
               {:keys [street-address city state]} address]
           (apply str (interpose ", " [name street-address city state]))))
       ["Test" "Testerson"] test-address)))
