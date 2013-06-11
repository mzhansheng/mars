(ns com.seekon.system.account.client.internal.user-profile
  (:use [seesaw core mig]
        [seesaw.options :only [apply-options]]
        [seesaw.widget-options :only [widget-option-provider]])
  (:import [com.seekon.system.account.client.internal ServiceFacade]
           [com.seekon.system.auth.model User]
           )
  )

(defn- get-screen-center-point [obj-w obj-h]
  (let [kit (java.awt.Toolkit/getDefaultToolkit)
        sw (.. kit (getScreenSize) (getWidth))
        sh (.. kit (getScreenSize) (getHeight))
        ]
    [(/ (- sw obj-w) 2) (/ (- sh obj-h) 2)]
    ))
(defn- get-user [user-id]
   (.getUser (ServiceFacade/getUserService) user-id)
  ;(User.)
  )

(widget-option-provider org.jdesktop.swingx.JXDatePicker text-options)

(defn- date-picker [& args]
  (apply-options (construct org.jdesktop.swingx.JXDatePicker) args)
  )

(defn- update-user [user-map]
  (let [user (User.)]
    (doto user (.setUserName (user-map :user-name)) (.setMobile (user-map :mobile))
      (.setId (user-map :user-id)) (.setPhone (user-map :phone))
      (.setEmail (user-map :email)) (.setIdCard (user-map :id-card))
      (.setUserCode (user-map :user-code)))
    (try (.updateUserBasicInfo (ServiceFacade/getUserService) user)
    (catch Exception e
      (.printStackTrace e)
      (alert (str "保存失败，原因：" e) ))
    )
    ))
(defn- check-new-pwd [ae]
 (let [v-new-pwd-re (value (select (to-root ae) [:#new-pwd-re]))
       v-new-pwd (value (select (to-root ae) [:#new-pwd]))
       pwd-tip (select (to-root ae) [:#pwd-tip])
       not-equals (not= v-new-pwd-re v-new-pwd)
      ]                  
      (if not-equals
        (do (.setText pwd-tip "新密码设置的不一致.")
          (.setEnabled (select (to-root ae) [:#b-update-button]) false)
        ) 
        (do (.setText pwd-tip "")
          (.setEnabled (select (to-root ae) [:#b-update-button]) true) 
        )
      )
    ) 
  )

(defn- update-pwd [user-map]
  (let [user-id (user-map :user-id)
        pwd (user-map :new-pwd)]
  (try (.updatePassword (ServiceFacade/getUserService) user-id pwd)
    (catch Exception e
      (.printStackTrace e)
      (alert (str "保存失败，原因：" e) ))
    )
  )
  )

(defn get-user-profile [user-id]
  (let [dialog-w 400
        dialog-h 315
        [center-x center-y] (get-screen-center-point dialog-w dialog-h)
        sex-bg (button-group)
        user (get-user user-id)
        ]
  (-> 
    (custom-dialog
      :title "个人档案信息"
      :content
      (tabbed-panel
        :tabs [
         {
            :title "基本信息",
            :content
            (border-panel
              :center
              (
                mig-panel :constraints ["align center", "[right]"]
                 :items [                     
                         [ "账号" ""]
                         [ (text :enabled? false :id :user-code :text (.getUserCode user)) "span, w 3cm:5cm:, wrap"]
                         [ "名称" ""]
                         [ (text :id :user-name :text (.getUserName user)) "span, growx, wrap"]
                         [ "性别"  ""]
                         [ (radio :id :sex-m :text "男" :selected? true :group sex-bg) ""]
                         [ (radio :id :sex-f :text "女" :group sex-bg) "growx,wrap"]
                         [ "生日"  ""]
                         [ (date-picker :id :birthday)  "growx, span, wrap"]
                         [ "手机"  ""]
                         [ (text :id :mobile :text (.getMobile user)) "growx,span,wrap"]
                         [ "电话"   ""]
                         [ (text :id :phone :text (.getPhone user)) "growx,span,wrap"]
                         [ "邮箱"   ""]
                         [ (text :id :email :text (.getEmail user)) "growx,span,wrap"]
                         [ "身份证号" ""]
                         [ (text :id :id-card :text (.getIdCard user)) "span,growx"]
                         ]
                )
              :south 
              (flow-panel
                :align :right
                :items [
                  (button
                    :text "保存"
                    :listen [
                             :action (fn [ae] (update-user (assoc (value (to-root (to-widget ae))) :user-id (.getId user)) 
                                                ))
                             ]
                    )
                  (button 
                    :text "取消"
                    :listen [
                             :action (fn [ae] (dispose! (to-root (to-widget ae)) ))
                             ]
                    )
                  ]
                )
              )            
            }
           {
            :title "修改密码"
            :content 
            (border-panel
              :center 
              (mig-panel
                :constraints ["align center top", "[right]"]
                :items [
                    ["当前密码"] [(password  :id :old-pwd
                                         :listen [
                                                  :focus (fn [ae] (let [pwd-w (to-widget ae)
                                                                        pwd (.getPassword user)
                                                                        pwd-tip (select (to-root ae) [:#pwd-tip])
                                                                        c-pwd (value pwd-w)
                                                                        is-quals (or (and (not pwd) (= c-pwd ""))(= c-pwd pwd))]
                                                                    (if (== (.getID ae) java.awt.event.FocusEvent/FOCUS_LOST)
                                                                      (if (not is-quals)  
                                                                      (do (.setText pwd-tip "当前密码输入不对.")
                                                                        (.setEnabled (select (to-root ae) [:#b-update-button]) false)
                                                                        (.setEditable (select (to-root ae) [:#new-pwd]) false)
                                                                        (.setEditable (select (to-root ae) [:#new-pwd-re]) false)
                                                                        ) 
                                                                      (do (.setText pwd-tip "")
                                                                        (.setEnabled (select (to-root ae) [:#b-update-button]) true)
                                                                        (.setEditable (select (to-root ae) [:#new-pwd]) true)
                                                                        (.setEditable (select (to-root ae) [:#new-pwd-re]) true)
                                                                        ) 
                                                                      )
                                                                      ;(do (.setEditable (select (to-root ae) [:#new-pwd]) true)
                                                                      ;  (.setEditable (select (to-root ae) [:#new-pwd-re]) true)
                                                                       ; )
                                                                      )
                                                                    ) 
                                                           )
                                                  ]) 
                              "span, w 3cm:5cm:, growx, wrap"]
                    ["设置新密码"] [(password  :id :new-pwd :editable? true
                                          :listen [
                                                   :key (fn [ae] (check-new-pwd ae))
                                                   ]) "span, growx, wrap"]
                    ["重复新密码"] [(password  :id :new-pwd-re :editable? true
                                          :listen [
                                                   :key (fn [ae] (check-new-pwd ae))
                                                   ]) "span, growx, wrap"]
                    [(label :id :pwd-tip :foreground  :red) "span 2"] 
                        ]
                )
              :south
              (flow-panel
                :align :right
                :items [
                    (button :text "确定"
                            :id :b-update-button
                            :enabled? false
                            :listen [
                                     :action (fn [ae] (update-pwd (assoc (value (to-root (to-widget ae))) :user-id (.getId user))))
                                     ])
                        ]
                )
              )
            }
           ]
        )
      :resizable? false
      :size [dialog-w :by dialog-h]
      )
      (move-to! center-x center-y) 
      show!
    )
   )
  )