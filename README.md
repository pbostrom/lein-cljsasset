# lein-cljsasset

Manage JavaScript/CSS dependencies for ClojureScript projects.

_**Library authors**_ can declare dependencies on JavaScript and CSS assets in the project.clj file.
_**Library consumers**_ can resolve and concatenate JavaScript and CSS dependencies into single file(s) to include in their application.

## Usage

### Library authors: Declare dependencies on JavaScript and CSS assets in the project.clj file.

Add any JavaScript library available in a Maven repository to your `:dependencies` section in `project.clj`:

```clj
:dependencies [[org.webjars/codemirror "4.6"]  ; available on WebJars
               [com.facebook/react "0.11.1"]]  ; available on Clojars
```

Add a `:cljsasset` section in `project.clj`:
```clj
:cljsasset {:js
           ["react/react.js"    ; from com.facebook/react
            "META-INF/resources/webjars/codemirror/4.6/lib/codemirror.js" ; from org.webjars/codemirror
            "META-INF/resources/webjars/codemirror/4.6/mode/clojure/clojure.js"
            "META-INF/resources/webjars/codemirror/4.6/addon/edit/closebrackets.js"
            "META-INF/resources/webjars/codemirror/4.6/addon/edit/matchbrackets.js"]
           :css
           ["META-INF/resources/webjars/codemirror/4.6/lib/codemirror.css"]}
```

### Library consumers: Resolve and concatenate JavaScript and CSS dependencies (which are declared by your ClojureScript dependencies) into single file(s).

Add the desired ClojureScript library to your `:dependencies` section in `project.clj`:
```clj
:dependencies [[om-codemirror "0.2.0-SNAPSHOT"]]
```

Add `[lein-cljsasset "0.1.0"]` to `:plugins` in your `project.clj` or `profiles.clj`. Then add a `:cljsasset` section in your project.clj file that looks something like this:

```clj
:cljsasset {:js-output {:dir "resources/js"
                          :file "assets.js"}
              :css-output {:dir "resources/css"
                           :file "assets.css"}}
```
Now you can run the lein plugin:

    $ lein cljsasset
    
And your concatenated assets will be available for use by your application.
    
### Declare JavaScript and CSS dependencies for your ClojureScript library

```clj
(defproject om-codemirror-lib "0.2.0-SNAPSHOT"
  ...
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2322"]
                 [org.webjars/codemirror "4.3"]
                 [om "0.7.3"]]

  :cljsasset {:js
              ["META-INF/resources/webjars/codemirror/4.3/lib/codemirror.js"
               "META-INF/resources/webjars/codemirror/4.3/mode/clojure/clojure.js"
               "META-INF/resources/webjars/codemirror/4.3/addon/edit/closebrackets.js"
               "META-INF/resources/webjars/codemirror/4.3/addon/edit/matchbrackets.js"]
              :css
              ["META-INF/resources/webjars/codemirror/4.3/lib/codemirror.css"]})

```

## License

Copyright © 2014 Paul Bostrom

Distributed under the Eclipse Public License, the same as Clojure.
