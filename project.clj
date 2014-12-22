(defproject clj-slack-client "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [cheshire "5.4.0"]
                 [aleph "0.4.0-alpha9"]
                 [byte-streams "0.2.0-alpha3"]]
  :main ^:skip-aot clj-slack-client.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
