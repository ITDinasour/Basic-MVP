# Basic-MVP
 结合RxJava实现MVP架构架构基础
 
 Step 1. Add the JitPack repository to your build file
  Add it in your root build.gradle at the end of repositories:
```kotlin 
 	allprojects {
 		repositories {
 			...
 			maven { url 'https://jitpack.io' }
 		}
 	}
``` 
 Step 2. Add the dependency
 ```kotlin 
 	dependencies {
 	        implementation 'com.github.ITDinasour:Basic-MVP:0.3.2'
         // implementation 'com.github.ITDinasour:Basic-WithViewBinding:0.1.2'//结合基础构建服务
 	}
```  
