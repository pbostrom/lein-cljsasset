# lein-pprint

Manage dependencies for ClojureScript projects.

## Usage

### Resolve and concatenate JavaScript and CSS dependencies into a single file

Add `[lein-cljsasset "0.1.0-SNAPSHOT"]` to `:plugins`.

    $ lein cljsasset
    
### Declare JavaScript and CSS dependencies for your ClojureScript library

```clj
{:compile-path "/home/phil/src/leiningen/lein-pprint/classes",
 :group "lein-pprint",
 :source-path ("/home/phil/src/leiningen/lein-pprint/src"),
 :dependencies nil,
 :target-path "/home/phil/src/leiningen/lein-pprint/target",
 :name "lein-pprint",
 :root "/home/phil/src/leiningen/lein-pprint",
 :version "1.0.0",
 :jar-exclusions [#"^\."],
 :test-path ("/home/phil/src/leiningen/lein-pprint/test"),
 :repositories
 (["central" {:url "http://repo1.maven.org/maven2"}]
  ["clojars" {:url "http://clojars.org/repo/"}]),
 :uberjar-exclusions [#"^META-INF/DUMMY.SF"],
 :eval-in :leiningen,
 :plugins [[lein-swank "1.4.0-SNAPSHOT"]],
 :resources-path
 ("/home/phil/src/leiningen/lein-pprint/dev-resources"
  "/home/phil/src/leiningen/lein-pprint/resources"),
 :native-path "/home/phil/src/leiningen/lein-pprint/native",
 :description "Pretty-print a representation of the project map."}
```

## License

Copyright © 2014 Paul Bostrom

Distributed under the Eclipse Public License, the same as Clojure.
