# mvvm
搭建mvvm框架
## 1.build.gradle(Module:app)
中需要添加的代码：
```
    implementation 'com.trello.rxlifecycle2:rxlifecycle:2.2.2'
    implementation 'com.trello.rxlifecycle2:rxlifecycle-android:2.2.2'
    implementation 'com.trello.rxlifecycle2:rxlifecycle-components:2.2.2'
    implementation 'org.jetbrains:annotations:15.0'
    implementation 'com.github.sweven-tears:mvvm:1.0'

```
## 2.build.gradle(Project:xxx)
```
...
allprojects {
    repositories {
        ...
        maven { url 'https://jitpsck.io' }
    }
}
...
```
