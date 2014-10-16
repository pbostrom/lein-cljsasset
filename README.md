# lein-cljsasset

Manage JavaScript/CSS dependencies for ClojureScript projects.

* _**Library authors**_ can declare dependencies on JavaScript and CSS assets in the project.clj file.
* _**Library users**_ can resolve and concatenate JavaScript and CSS dependencies into single file(s) to include in their application.

## Usage

#### Library authors: Declare dependencies on JavaScript and CSS assets in the project.clj file.

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
             "META-INF/resources/webjars/codemirror/4.6/mode/clojure/clojure.js"]
            :css
            ["META-INF/resources/webjars/codemirror/4.6/lib/codemirror.css"]}
```

Now your users can use the lein-cljsasset plugin to fetch the dependencies and concatenate them into a single file for use in their application. It might be a good idea to include (or link) the section below in your library's README.

#### Library users: Resolve and concatenate JavaScript and CSS dependencies into single file(s).

Add the ClojureScript libraries to your `:dependencies` section in `project.clj`:
```clj
:dependencies [[om-codemirror "0.2.0-SNAPSHOT"]
               [om "0.7.3"]]
```

Add this plugin to the `:plugins` section in your `project.clj`:
```clj
:plugins [[lein-cljsasset "0.1.0"]]
```

Then just run the lein plugin:

    $ lein cljsasset

By default this will concatenate all JavaScript dependencies to `resources/public/js/assets.js` and CSS dependencies to `resources/public/css/assets.css`.

If you wish to change the default path of the output files, then add a `:cljsasset` section in your project.clj file that looks something like this:

```clj
:cljsasset {:js-output {:dir "resources/public/my-js"
                          :file "my-assets.js"}
            :css-output {:dir "resources/public/my-css"
                           :file "my-assets.css"}}
```

## Examples
[om-codemirror](https://github.com/pbostrom/om-codemirror) is a library that provides an Om wrapper around the CodeMirror text editor. It declares dependencies on several JavaScript and CSS assets provided by CodeMirror.

[om-editor-app](https://github.com/pbostrom/om-editor-app) is an app that depends on `om-codemirror` and uses `lein-cljsasset` to fetch the JavaScript and CSS assets.

Also see my fork of Om, which is just a single [commit](https://github.com/pbostrom/om/commit/dbcc4a2b567c851bb00763d98130558599e8ec5e) that adds a `:cljsasset` section. The `om-editor-app` listed above uses this version to avoid having to manage the `react.js` dependency directly.

## TODO
* Nested dependencies: Currently the plugin does not walk the entire dependency tree; it will only find assets declared by its direct dependencies.
* File system dependencies: The plugin can only fetch assets on the classpath. It should allow fetching an asset from the filesystem.

## License

Copyright © 2014 Paul Bostrom

Distributed under the Eclipse Public License, the same as Clojure.
