(ns skinner-web.app
  (:require [reagent.core :as reagent]
            [skinner-web.main :as skinner]
            [cljs.reader :as reader]))
(enable-console-print!)
(def !app-state (reagent/atom {:input ""
                               :memory 0}))

(defn top-componend
  []
  [:div
    [:h1 "TIS-100"]])

(defn acc-value []
  [:div (:memory @!app-state)])

(defn info-component[]
  [:div.sm-col.sm-col-2 [acc-value]])

(defn eval! [inputs]
  (let [_ (js/console.log "evaluating: " (-> inputs .-target .-value))
        output (skinner/read-commands [[:ADD, 5]])
        _ (prn @skinner/memory)]
       (swap! !app-state assoc-in [:memory] @skinner/memory))
  false)
  

(defn run-btn-component []
  [:input.btn.btn-outline {:type "submit"}])

(defn command-input-component []
  [:div.sm-col.sm-col-6
   [top-componend]
   [:form {:on-submit eval!}
    [:textarea {:id "code"
                :placeholder "ADD 3\nSUB 1\nSAV"
                :rows "15"
                :cols "19"}]
    [run-btn-component]]])

(defn calling-component []
  [:div.clearfix
   [command-input-component]
   [info-component]])


(defn init []
  (reagent/render-component [calling-component]
                            (.getElementById js/document "container")))
