# 基本框架组

<!-- <p align="center"><img src="https://github.com/Libgdx-Processing-Plus/Libgdx-Processing-Plus/tree/public/doc/image/logo.png"></p> -->

![logo](doc/image/logo.png)

[![jitpack-badge](https://jitpack.io/v/Java-Game-Engine-Merger/Libgdx-Processing.svg)](https://jitpack.io/#Java-Game-Engine-Merger/Libgdx-Processing)

（只是一些Libgdx游戏框架）

[中文](#基本框架组) | [English Version](#fundamental-framework-group)

## 使用Protobuf

应当先从<https://github.com/protocolbuffers/protobuf/releases/latest>下载protoc并加入path环境变量，否则gradle运行build和generateProto的任务时会报错

## 如何上手

### 创建新的草图

1. 打开对应项目（例如game0001）的`pama1234.gdx.launcher.MainApp`
2. 创建新的`Screen0xxx`
3. 将你的`Screen0xxx`继承`ScreenCore2D`或`ScreenCore3D`

## 配置`settings.gradle`

众所周知，gradle很慢，这个托管内有至少40个子项目，这会产生9000多个甚至更多的tasks，因此，我们开发时会将一部分用不上的项目在`settings.gradle`文件内注释掉，如果需要使用，取消注释即可

## 创建新项目

如果各位开发者想要创建新的项目，则需要在项目的根目录下的`build.gradle`的（目前）第35行的`configure(subprojects.findAll {`位置添加

```gradle
|| it.name == '项目名-android'
```

用于排除这个以及其他不能使用gradle的java plugin的子项目

## 教程和文档

（未完善，整理中）<https://github.com/Java-Game-Engine-Merger/libgdx-processing-website>

## 如何使用我们的的框架

我们的框架通过jit进行打包

<https://jitpack.io/#pama1234/just-some-other-libgdx-game/>

注意，菱形依赖的问题并没有解决，因此在其他项目内使用framework0001和其他内容时可能会需要配置一大堆exclude规则，尤其是在打包安卓的时候，推荐只使用以下两个基础框架

### Gradle

```gradle
implementation "com.github.pama1234.just-some-other-libgdx-game:server-framework:$pama1234Version"
implementation "com.github.pama1234.just-some-other-libgdx-game:framework:$pama1234Version"
```

或直接标明版本

```gradle
implementation 'com.github.pama1234.just-some-other-libgdx-game:server-framework:bf0a359313'
implementation 'com.github.pama1234.just-some-other-libgdx-game:framework:bf0a359313'
```

### Maven

```maven
 <dependency>
     <groupId>com.github.pama1234.just-some-other-libgdx-game</groupId>
     <artifactId>framework</artifactId>
     <version>bf0a359313</version>
 </dependency>
```

## 空想世界框架

QQ群：589219461

配置环境：

0. 确保自己使用的电脑可以直接访问需要访问的以下网站，推荐[此方案](https://github.com/getlantern/lantern)
1. 下载并安装`jdk-17`，配置javahome和path环境变量，推荐[此版本(graalvm-22.1.0)](https://github.com/graalvm/graalvm-ce-builds/releases/tag/vm-22.1.0)
2. 下载并安装`gradle-8`，配置gradle和gradle-javahome环境变量，[链接](https://gradle.org/releases)
3. 下载并安装`Android studio Canary build`，配置AndroidSDK环境变量（也就是内测版，在页面的右侧，总之哪个新就安装哪个好啦）[链接](https://developer.android.com/studio/preview)

使用的库：libgdx，kryo，vecmath

主要类位置：pama1234.gdx.launcher.MainApp

项目代码帮助文档：`doc\codeHelp.txt`

代码格式化请使用`doc\eclipse.formatter.xml`，如果不愿意使用此格式化方案，请在提交时运行`gradlew spotlessApply`

局部变量的命名规则：

|类型|总维度数|维度数|英文字符|
|---|---|---|---|
|位置|3|1|x|
|位置|3|2|y|
|位置|3|3|z|
|体积|3|1|w|
|体积|3|2|h|
|体积|3|3|l|
|整数|?|1|i|
|浮点数|?|1|f|
|布尔值|?|1|b|
|父实例|?|1|p|
|翻译包|?|1|bd,ld|

简写示例:

|类型|示例|
|---|---|
|位置|tx, tx1, tx_1, tx2, tx_2|
|父实例|p, pc, pw, pg|

（tx是temp-x的缩写）

待办事项请看doc文件的todo.txt,若完成了某一项，请将其剪切粘贴到solved.txt，编译安卓项目请用自己的签名文件, 可以使用中英文或其他语言作为待办项，但不能修改已有待办项的语言类型

安卓版改native依赖项的时候记得手动删除`android\libs\`内的文件和文件夹

## Fundamental Framework Group

![logo](doc/image/logo.png)

[![jitpack-badge](https://jitpack.io/v/Java-Game-Engine-Merger/Libgdx-Processing.svg)](https://jitpack.io/#Java-Game-Engine-Merger/Libgdx-Processing)

(Just Some Other Libgdx game framework)

[中文](#基本框架组) | [English Version](#fundamental-framework-group)

## Use Protobuf

You should first use <https://github.com/protocolbuffers/protobuf/releases/Latest> Download Protoc and add Path environment variables, otherwise Gradle will report an error when running BUILD and GenerateProto.

## How to get started

### Create a new sketch

1. Open the corresponding items (such as Game0001) `pama1234.gdx.launcher.mainapp`
2. Create a new `screen0xxx`
3. Inherit your `Screen0xxx`` ScreenCore2D` or `ScreenCore3D`

## configuration `settings.gradle`

As we all know, Gradle is very slow. There are at least 40 sub -items in this hosting, which will produce more than 9,000 or more Tasks. Therefore, we will make some unused items in the `settings.gradle` file when we develop.If you need to use it, cancel the comment

## Create a new project

If developers want to create a new project, they need to be in the root directory of the project's root directory (current).

`` `Gradle
|| IT.Name == 'Project Name -android'
`` `

Used to exclude this and other sub -items that and other Java Plugin that cannot be used in Gradle

## tutorial and documentation

(Non-perfect, sorting) <<https://github.com/java-> Game-engine-Merger/libgdx-process-website>

## How to use our framework

Our framework is packaged through JIT

<<https://jitpack.io/#Pama1234/just-some-some-libgdx-> game/>

Note that the problem of diamond -shaped dependence is not resolved, so when using Framework0001 and other contents in other items, a lot of EXCLUDE rules may be required. Especially when packaging Android, it is recommended to use only the following two basic frameworks.

### Gradle-EN

```gradle
implementation "com.github.pama1234.just-some-other-libgdx-game:server-framework:$pama1234Version"
implementation "com.github.pama1234.just-some-other-libgdx-game:framework:$pama1234Version"
```

Or directly indicate the version.

```gradle
implementation 'com.github.pama1234.just-some-other-libgdx-game:server-framework:bf0a359313'
implementation 'com.github.pama1234.just-some-other-libgdx-game:framework:bf0a359313'
```

### Maven-EN

```maven
 <dependency>
     <groupId>com.github.pama1234.just-some-other-libgdx-game</groupId>
     <artifactId>framework</artifactId>
     <version>bf0a359313</version>
 </dependency>
```

QQ group: 589219461

Configuration Environment:

0. Make sure that the computer you use can directly access the following websites that need to be accessed, and recommend [this solution](<https://github.com/getlantern/lantern>)
1. Download and install the `jdk-17`, configure Javahome and PATH environment variables, and recommend [Graalvm-22.1.0)](<https://github.com/graalvm/graalvm-> lineases/tags/tag/vm-22.1.0)
2. Download and install `Gradle-8`, configure Gradle and Gradle-Javahome environment variables, [link](<https://gradle.org/releases>)
3. Download and install `Android Studio Canary Build`, configure the Androidsdk environment variable (that is, internal test, on the right side of the page, any new one is installed and which one is installed) [link](<https://developper.android.COM/Studio/Preview>)

Library used: libgdx, kryo, vecmath

Main class Location: pama1234.gdx.launcher.mainapp

Project code help document: `doc\codehelp.txt`

Code formatting, please use the `doc\eclipse.formatter.xml`. If you do not want to use this format scheme, run 'gradlew spotlessApply' when committing your change.

Naming rules for local variables:

| Type | Total dimension | dimension number | English character |
| --- | --- | --- | --- | |
| Location | 3 | 1 | X |
| Location | 3 | 2 | Y |
| Location | 3 | 3 | Z |
| Volume | 3 | 1 | W |
| Volume | 3 | 2 | H |
| Volume | 3 | 3 | L |
| Integer | ? | 1 | i |
| Floating point number | ? | 1 | f |
| Boolean value | ? | 1 | b |
| Father Example | ? | 1 | P |
| Translation package | ? | 1 | BD, LD |

Abbreviation example:

| Type | Example |
| --- | --- | |
Location | TX, TX1, TX_1, TX2, TX_2 |
| Father Example | P, PC, PW, PG |

(TX is the abbreviation of TEMP-X)

Please see the TODO.TXT of the doc file to do it. If you complete a certain item, please cut it and paste it to Solved.txt. Please use your own signature file to compile the Android project.Items, but cannot be modified

When the Android version changes the Native dependency item, remember to manually delete the files and folders in the files in the man manually
