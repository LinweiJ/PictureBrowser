## PictureBrowser

一个基于support.v4.app.DialogFragment的图片浏览器

特点:

- 图片浏览器,跟Dialog一样简单使用
- 基于DialogFragment , 支持横竖屏切换不消失
- 基于ViewPager+FragmentPagerAdapter,滑动流畅
- 基于PhotoView,支持图片缩放
- 可定制图片加载器
- 提供一些定制接口





[![](https://jitpack.io/v/LinweiJ/PictureBrowser.svg)](https://jitpack.io/#LinweiJ/PictureBrowser)

![PictureBrowser.gif](https://github.com/LinweiJ/PictureBrowser/blob/master/screen_shot/PictureBrowser.gif)

## 文档

- [English](https://github.com/LinweiJ/PictureBrowser/blob/master/README_EN.md)
- [中文](https://github.com/LinweiJ/PictureBrowser/blob/master/README.md)

## 如何使用它？

### gradle

#### 1.先在 project的build.gradle  添加:

```
allprojects {
		repositories {
			...
			maven { url "https://jitpack.io" }
		}
	}
```
#### 2.然后在module的build.gradle 添加:

```
dependencies {
	        compile 'com.github.LinweiJ:PictureBrowser:0.0.1'
	}
```
### java

#### 1.简单使用例子

```java
PictureLoader pictureLoader = new PictureLoader() {
            @Override
            public void showPicture(PictureFragment fragment, PhotoView photoView, String pictureUrl) {
                //使用Glide加载图片,可自行根据需求选用其他图片加载库   
                Glide.with(fragment)
                        .load(pictureUrl)
                        .placeholder(new ColorDrawable(Color.LTGRAY))
                        .into(photoView);
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

#### 2.类/方法

- PictureBrowser.Builder:

|        方法名         |                    描述                    | 备注(是否设置) |
| :----------------: | :--------------------------------------: | :------: |
| setFragmentManager | 请传入android.support.v4.app.FragmentManager |    必须    |
|     setUrlList     | 请传入ArrayList<String> ,暂时只支持String类型,一般表示为图片url |    必须    |
| initPictureLoader  |      初始化图片加载器PictureLoader,只能设置成功一次      |    必须    |
|  setPictureLoader  |          可重置图片加载器PictureLoader           |    可选    |
|   setStartIndex    |              首次显示图片页码,默认为0               |    可选    |
| setShowDeleteIcon  |           是否显示左上角关闭按钮,默认为true            |    可选    |
|  setShowIndexHint  |            是否显示底部页码提示,默认为true            |    可选    |
|  setCancelOutside  |             是否开启点击关闭,默认为true             |    可选    |
|       create       |      以上设置完成后调用得到PictureBrowser的一个实例      |    必须    |

- PictureLoader


|     方法名     |                    描述                    |  备注  |
| :---------: | :--------------------------------------: | :--: |
| showPicture | 可用参数Fragment fragment, PhotoView pictureView, String pictureUrl,可自行根据需求选用合适图片加载库 |      |

- PictureBrowser


| 方法名  |           描述           |  备注  |
| :--: | :--------------------: | :--: |
| show | 调用该方法,展示PictureBrowser |  必须  |

### 更多细节

*可以参考 app/ 示例*

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
