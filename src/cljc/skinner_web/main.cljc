(ns skinner-web.main)

; ACC
(def memory (atom 0))
; BAK
(def temp-storage (atom 0))

; make sure that my atoms aren't nil
(defn myswap!
  [myatom fn]
  (if (= @myatom nil)
    (reset! myatom 0)
    (swap! myatom fn)))

                                        ; new PURE multimethod

(defmulti super-parse (fn [state action]
                        (first action)))
                                        ; destracturing the action parameter to only use the second val

(defmethod super-parse :ADD [state [_ n]]
  (update state :mem #(+ % n)))

(defmethod super-parse :SUB [state [_ n]]
  (update state :mem #(- % n)))

(defn super-read-commands
  [state cmds]
                                        ; for every cmd in cmds do:
                                        ; (let state (super-parse state cmd))

  (reduce #(super-parse state) cmds)
)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; Language Parser
(defmulti parse (fn [my-var]
                 (first my-var)))

; adds a value to the primary memory
(defmethod parse :ADD [x]
  (myswap! memory (fn [memory] (+ memory (last x)))))

; subtracts a value from the primary memory
(defmethod parse :SUB [x]
  (myswap! memory (fn [memory] (- memory (last x)))))

; dublicates the value in memory (ACC) to the temporary storage (BAK)
(defmethod parse :SAV [x]
  (reset! temp-storage @memory))

; swap ACC and BAK with eachother
(defmethod parse :SWP [x]
  (let [prev-memory @memory
        prev-temp @temp-storage]
    (reset! memory prev-temp)
    (reset! temp-storage prev-memory)))


(defn read-commands
  [xs]
  (if (not= (first xs) nil)
    (do (parse (first xs))
      (read-commands (rest xs)))))
