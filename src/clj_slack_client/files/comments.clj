(ns clj-slack-client.files.comments
  (:require [clj-slack-client.web :as web])
  (:refer-clojure :exclude [comment]))


(defn add
  "Add a comment to an existing file. Callable by bots, workspaces,
  and user tokens."
  [token comment file]
  (web/call-and-get-response "files.comments.add"
                             {:token token :comment text :file file}))


(defn delete
  "Deletes an existing comment on a file. Callable by all token types."
  [token file file-comment-id]
  (web/call-and-get-response "files.comments.delete"
                             {:token token :file file :id file-comment-id}))


(defn edit
  "Edit an existing file comment. Accessible by all token types."
  [token comment file file-comment-id]
  (web/call-and-get-response "files.comments.edit"
                             {:token token :comment comment
                              :file file :id file-comment-id}))

