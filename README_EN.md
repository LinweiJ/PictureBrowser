

## PictureBrowser

Implementation of DialogFragment for Android that supports show a group picture 

Feature

- A useful picture browser,it's easy to use just like dialog
- Implementation of DialogFragment , so  auto rebuild when the device switches between portrait and landscape orientation
- use  ViewPager+FragmentPagerAdapter , so slipping smoothness 
- use PhotoView, a zooming Android ImageView.
- customizable picture loader,you can use Glide,Picasso,Fresco,UIL 
- other customizable properties



[![](https://jitpack.io/v/LinweiJ/PictureBrowser.svg)](https://jitpack.io/#LinweiJ/PictureBrowser)

![PictureBrowser.gif](https://github.com/LinweiJ/PictureBrowser/blob/master/screen_shot/PictureBrowser.gif)

## Document

- [English](https://github.com/LinweiJ/PictureBrowser/blob/master/README_EN.md)
- [中文](https://github.com/LinweiJ/PictureBrowser/blob/master/README.md)



## usage

### Gradle

#### 1.Add it in your root build.gradle at the end of repositories:

```xml
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```

#### 2.Add the dependency in your module build.gradle :

```xml
dependencies {
	        compile 'com.github.LinweiJ:PictureBrowser:0.0.1'
	}
```

### java

#### 1.simple example

```java
PictureLoader pictureLoader = new PictureLoader() {
            @Override
            public void showPicture(Fragment fragment, PhotoView pictureView, String pictureUrl) {
                //yon can use other image loading library    
                Glide.with(fragment)
                        .load(pictureUrl)
                        .placeholder(new ColorDrawable(Color.LTGRAY))
                        .into(pictureView);
            }
        };

PictureBrowser.Builder builder = new PictureBrowser.Builder();
builder.setFragmentManager(getSupportFragmentManager())
        .setUrlList(pictureUrl)
        .setStartIndex(position)
        .initPictureLoader(pictureLoader)
        .setShowDeleteIcon(true)
        .setShowIndexHint(true)
        .setCancelOutside(true)
        .create()
        .show();
```



#### 2.class/method

- PictureBrowser.Builder

|       method       |               description                | remark(whether must) |
| :----------------: | :--------------------------------------: | :------------------: |
| setFragmentManager |  android.support.v4.app.FragmentManager  |         must         |
|     setUrlList     | ArrayList<String> ,a group picture's url |         must         |
| initPictureLoader  | init PictureLoader,set success only once |         must         |
|  setPictureLoader  |           reset PictureLoader            |                      |
|   setStartIndex    |   set first show picture,default true    |                      |
| setShowDeleteIcon  | set deleteIcon show or not,default true  |                      |
|  setShowIndexHint  |  set IndexHint show or not,default true  |                      |
|  setCancelOutside  | set  pictureBrowser cancelable when click screen,default true |                      |
|       create       | after all setup,get a PictureBrowser's instance |         must         |

- PictureLoader

|   method    |               description                | remark |
| :---------: | :--------------------------------------: | :----: |
| showPicture | parameter:Fragment fragment, PhotoView pictureView, String pictureUrl,choose image loading library that you like |        |

- PictureBrowser

| method |     description     | remark |
| :----: | :-----------------: | :----: |
|  show  | show PictureBrowser |  must  |



### more usage

For a working implementation of this project see the app/ folder.



## Thanks

- [PhotoView](https://github.com/bm-x/PhotoView)

## License

```
Copyright 2017 LinWeiJia

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```

## More

Why not give a star ? (>_@)

