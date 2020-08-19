# mvvm
搭建mvvm框架
build.gradle(Module:app)
中需要添加的代码：
``
    implementation 'com.trello.rxlifecycle2:rxlifecycle:2.2.2'
    implementation 'com.trello.rxlifecycle2:rxlifecycle-android:2.2.2'
    implementation 'com.trello.rxlifecycle2:rxlifecycle-components:2.2.2'
    implementation 'org.jetbrains:annotations:15.0'
    implementation project(path: ':mvvm')

``
