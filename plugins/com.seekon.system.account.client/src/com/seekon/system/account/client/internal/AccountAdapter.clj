(ns com.seekon.system.account.client.internal.AccountAdapter
  (:use [com.seekon.system.account.client.internal.user-profile]
        )
  (:gen-class
   :methods [#^{:static true} [getUserProfile [String] javax.swing.JDialog]])
  )

(defn -getUserProfile [user-code]
  (get-user-profile user-code) 
  )
