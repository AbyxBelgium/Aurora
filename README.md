# Aurora
The Aurora library allows for manipulating bitmaps and images in a programmatically way. It was originally built for generating different types of backgrounds, but provides much more features today. Aurora is superfast and uses GPU acceleration where possible. A quickstart guide can be found below!

**DISCLAIMER: Please note that Aurora is currently still in alpha-status and that some bugs might still be present. All features described in this document are working and tested, but it's possible for some bugs to have been overlooked. Please report any issues in the issue tracker.**

## Requirements:
Aurora only supports API 20 or higher. This is due to the fact that we use RenderScript with some features that are only available on Lollipop or higher.

## Installation:
Aurora is available from jCenter. Just add the following line to your build.gradle:

```
compile 'be.abyx:aurora:0.10'
```

## Features
###  Find closest color in a set
Aurora can, given a specific color, determine the color that's visually the most similar and is part of some predefined set of colors (such as the set of material design colors). This allows you to create backgrounds that fit perfectly.
![Closest color](https://raw.githubusercontent.com/AbyxBelgium/Aurora/master/documentation/readme/color_matcher.png)
### Generate blurry background based upon image
Aurora can also generate a blurry background that perfectly fits the color scheme of a given image. This gives a very nice effect when using the image itself as a foreground illustration and placing the generated Bitmap behind it!
![Generate blurry background](https://raw.githubusercontent.com/AbyxBelgium/Aurora/master/documentation/readme/blurry_background_generation.png)
### Automatically crop images with magic crop
Use magic crop to automatically remove white backgrounds from logo's, illustrations, cliparts or other images. No need to manually specify bounds, Aurora will automatically detect what's part of the background of an image and what's not.
![Magic crop](https://raw.githubusercontent.com/AbyxBelgium/Aurora/master/documentation/readme/magic_crop.png)
### Automatically rectangular crop
Crop images to only include the interesting part (without a big border of whitespace around them). This crop is also fully automatic and will intelligently detect the edges of an image.
![Magic crop](https://raw.githubusercontent.com/AbyxBelgium/Aurora/master/documentation/readme/crop_rectangular.png)
### Shape background
Draw a shape (with a specific padding) around a bitmap. Aurora supports both circles as rectangles at the moment.
![Shapes](https://raw.githubusercontent.com/AbyxBelgium/Aurora/master/documentation/readme/render_shape.png)
###
True masive parallelism! Aurora's processes for manipulating images and rendering backgrounds scale to use 100's of cores (GPU!) and are being processed very fast!
![Parallelism](https://raw.githubusercontent.com/AbyxBelgium/Aurora/master/documentation/readme/parallelism.png)
## Quickstart
### Find closest color in a set
Given a specific color, packed as an Android color, find the color that resembles it the most (visually) from a specific set of colors. Aurora comes with a set of the most occurring colors and a set containing a selection of Google's material design colors.

The ColourPaletteFactory class is used for constructing a color palette. This is an object that contains all colors from which the visually most appealing will be chosen. Right now only a default color set and a material design color set is supported, but you can always add your own by using the ![ColourParser](https://github.com/AbyxBelgium/Aurora/wiki/ColourParser).

This code snippet is used for determining the material design color that's visually most resembles green:
```java
ColourPaletteFactory factory = new ColourPaletteFactory(getApplicationContext());
ColourPalette materialPalette = factory.getMaterialColourPalette();
int closestColor = materialPalette.matchToClosestColour(Color.GREEN);
```

## Speed
Aurora is fully built with speed in mind and uses RenderScript. This allows us to speed up all computations by using the device's built-in GPU. Following are some speed tests that were performed on a OnePlus 3T and a Nexus 5.

Coming soon...
