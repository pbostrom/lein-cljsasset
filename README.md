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

Add the ClojureScript libraries you want to use to your `:dependencies` section in `project.clj`. This example uses [om-codemirror](https://github.com/pbostrom/om-codemirror):
```clj
:dependencies [[om-codemirror "0.2.1"]]
```

Add this plugin to the `:plugins` section in your `project.clj`:
```clj
:plugins [[lein-cljsasset "0.2.0"]]
```

If the library is packaged with support for `lein-cljsasset`, then all you have to do is run the lein plugin:

    $ lein cljsasset

By default this will concatenate all JavaScript dependencies to `resources/public/js/assets.js` and CSS dependencies to `resources/public/css/assets.css`.

If you wish to change the default path of the output files, then add a `:cljsasset` section in your project.clj file that looks something like this:

```clj
:cljsasset {:js-output {:dir "resources/public/my-js"
                        :file "my-assets.js"}
            :css-output {:dir "resources/public/my-css"
                         :file "my-assets.css"}}
```

###### Declaring JavaScript/CSS dependencies in your application's `project.clj`
If the ClojureScript library you want to use has a dependency on a JavaScript library, but is not packaged with support for `lein-cljsasset`, then you can add a `:cljsasset` section yourself to fetch the library. For example, [Om](https://github.com/swannodette/om) has a dependency on `com.facebook/react`, which is deployed on Clojars. By inspecting the Maven artifact, you can find the path of the JavaScript asset you want to include. In this case it is `"react/react.js"` (or `"react/react.min.js"` for the minified version). To have `lein-cljsasset` include the JavaScript asset in your output file, you would add this to your `project.clj`:
```clj
:cljsasset {:js
            ["react/react.js"]}
```
You can also declare dependencies on any of the JavaScript libraries available on [WebJars](http://www.webjars.org/). Using jQuery as an example, you would first include the WebJar in your dependencies section:
`:dependencies [[org.webjars/jquery "2.1.1"]]` then you can click on the "Files" link on right column on the WebJars site to see the listing of files it provides. We will choose `META-INF/resources/webjars/jquery/2.1.1/jquery.min.js`, and add it to the `:cljsasset` section:
```clj
:cljsasset {:js
            ["META-INF/resources/webjars/jquery/2.1.1/jquery.min.js"]}
```

## Examples
[om-codemirror](https://github.com/pbostrom/om-codemirror) is a library that provides an Om wrapper around the CodeMirror text editor. It declares dependencies on several JavaScript and CSS assets provided by CodeMirror.

[om-editor-app](https://github.com/pbostrom/om-editor-app) is an app that depends on `om-codemirror` and uses `lein-cljsasset` to fetch the JavaScript and CSS assets. It also fetches the `"react/react.js"` asset provided by Om.

## TODO
* Nested dependencies: Currently the plugin does not walk the entire dependency tree; it will only find the `:cljsasset` sections declared by its direct dependencies.
* File system dependencies: The plugin can only fetch assets available on the classpath. It should allow fetching an asset from the filesystem.

## License

Copyright © 2014 Paul Bostrom

Distributed under the Eclipse Public License, the same as Clojure.
