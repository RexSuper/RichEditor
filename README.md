# android富文本编辑器

# html实现的富文本编辑器
该项目采用的方法是，放静态网页到项目中，通过webview js和html交互的方式，调用html自带的各种富文本编辑的功能，并加上了一些细节性需求优化，提供一定的拓展性，尝试用过用原生实现，安卓自带的Html类里面自定义标签，但是非常实现和再现，再和其他端打通非常困难，最后发现直接用html编辑是最好的选择，可直接生成html源代码，也可以直接设置显示，这个方法需要你知道一定的js和webview前端知识

最后特别感谢“wasabeef/richeditor-android”，因为里面的RichEditor提供了给我全新的思路，来实现这些需求，让我越过了的原生实现的种种困难，
另外感谢HTML"Mogul" @ZX 让我知道html这些细节坑点 如何实现 ，可以视为它的RichEditor升级补充版

# RichHtmlEditorForAndroid

This project adopts the method of putting static web pages into the project, calling various rich text editing functions of HTML through the way of interaction between WebView JS and html, and adding some detail requirements optimization to provide certain extensibility, trying to use native implementation, Android's own Html class to customize labels, but very achieve and reproduce, and then communicate with other terminals. It's very difficult. Finally, we find that editing directly with html is the best choice. It can generate HTML source code directly or set up display directly. This method requires you to know some knowledge of JS and WebView front-end.

Finally, 

I would like to thank "wasabeef/richeditor-android" in particular, because Rich Editor provides me with new ideas to achieve this requirement, which can be regarded as an upgraded supplement to it.

Thank you also for HTML Mogul @ZX aha


