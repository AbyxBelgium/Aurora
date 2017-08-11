# Aurora
The Aurora library allows for manipulating bitmaps and images in a programmatically way. It was originally built for generating different types of backgrounds, but provides much more features today. Aurora is superfast and uses GPU acceleration where possible. A quickstart guide can be found below!

**DISCLAIMER: Please note that Aurora is currently still in alpha-status and that some bugs might still be present. All features described in this document are working and tested, but it's possible for some bugs to have been overlooked. Please report any issues in the issue tracker.**

## Features
###  Find closest color in set
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
![Magic crop](https://raw.githubusercontent.com/AbyxBelgium/Aurora/master/documentation/readme/render_shape.png)
## Quickstart
Coming soon...
## Speed
