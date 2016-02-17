(ns skinner-web.app
  (:require [reagent.core :as reagent]
            [skinner-web.main :as skinner]))

(def app-state
  (reagent/atom
    {:input ""}))

(defn top-componend
  []
  [:div
    [:h1 "TIS-100"]])

(defn acc-value []
  [:div @skinner/memory])

(defn info-component[]
  [:div.sm-col.sm-col-2 [acc-value]])

(defn run-btn-component []
  [:button.btn.btn-outline {:on-click #(.log js/console (:input @app-state))} "RUN"])

(defn command-input-component []
  [:div.sm-col.sm-col-6
    [top-componend]
    [:textarea {:placeholder "ADD 3\nSUB 1\nSAV"
                :rows "15"
                :cols "19"
                :on-change #(let [element (.-target %)]
                              (reset! app-state {:input (.-value element)}))}]

    [:div
      [run-btn-component]]])

(defn calling-component []
  [:div.clearfix
   [command-input-component]
   [info-component]])


(defn init []
  (reagent/render-component [calling-component]
                            (.getElementById js/document "container")))
